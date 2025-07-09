import java.sql.*;
import java.util.*;

public class SQLiteManager {
    private Connection connection;

    public SQLiteManager(String path){
        String url = "jdbc:sqlite:" + path;
        try{
            connection = DriverManager.getConnection(url);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        createTables();
    }

    public void createTables(){
        String mediaSql = "CREATE TABLE IF NOT EXISTS media (" +
                " id integer PRIMARY KEY," +
                " type text NOT NULL," +
                " name text NOT NULL," +
                " genres text," +
                " pub_year integer," +
                " rating real)";
        String bookSql = "CREATE TABLE IF NOT EXISTS books (" +
                "media_id INTEGER PRIMARY KEY," +
                "author TEXT NOT NULL," +
                "publisher text," +
                "pages integer," +
                "FOREIGN KEY(media_id) REFERENCES media(id))";
        String filmSql = "CREATE TABLE IF NOT EXISTS films (" +
                "media_id integer PRIMARY KEY," +
                "creators text NOT NULL," +
                "duration integer," +
                "FOREIGN KEY(media_id) REFERENCES media(id))";

        try (Statement stmt = connection.createStatement()){
            stmt.execute(mediaSql);
            stmt.execute(bookSql);
            stmt.execute(filmSql);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void addMedia(Media media){
        String sql = "INSERT INTO media(type, name, genres, pub_year, rating) VALUES(?,?,?,?,?)";

        try(PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            pstmt.setString(1, media.getType());
            pstmt.setString(2, media.getName());
            pstmt.setString(3, String.join(", ", media.getGenres()));
            pstmt.setInt(4, media.getYear());
            pstmt.setDouble(5, media.getRating());
            pstmt.executeUpdate();

            try(ResultSet rs = pstmt.getGeneratedKeys()){
                if(rs.next()){
                    int mediaId = rs.getInt(1);
                    media.setId(mediaId);

                    if(media instanceof Book){
                        addBook((Book) media, mediaId);
                    } else if(media instanceof Film){
                        addFilm((Film) media, mediaId);
                    }
                }
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private void addBook(Book book, int mediaId) throws SQLException{
        String sql = "INSERT INTO books(media_id, author, publisher, pages) VALUES(?,?,?,?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1, mediaId);
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getPublisher());
            pstmt.setInt(4, book.getPages());
            pstmt.executeUpdate();
        }
    }
    private void addFilm(Film film, int mediaId) throws SQLException{
        String sql = "INSERT INTO films(media_id, creators, duration) VALUES(?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1, mediaId);
            pstmt.setString(2, String.join(", ", film.getCreators()));
            pstmt.setInt(3, film.getDuration());
        }
    }

    public void updateMedia(Media media){
        String sql = "UPDATE media SET name=?, genres=?, pub_year=?, rating=?, WHERE id=?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setString(1, media.getName());
            pstmt.setString(2, String.join(", ", media.getGenres()));
            pstmt.setInt(3, media.getYear());
            pstmt.setDouble(4, media.getRating());
            pstmt.setInt(5, media.getId());
            pstmt.executeUpdate();

            if(media instanceof Book){
                updateBook((Book) media);
            } else if(media instanceof Film){
                updateFilm((Film) media);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private void updateBook(Book book) throws SQLException{
        String sql = "UPDATE books SET author=?, publisher=?, pages=? WHERE media_id=?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setString(1, book.getAuthor());
            pstmt.setString(2, book.getPublisher());
            pstmt.setInt(3, book.getPages());
            pstmt.setInt(4, book.getId());
            pstmt.executeUpdate();
        }
    }
    private void updateFilm(Film film) throws SQLException{
        String sql = "UPDATE books SET author=?, publisher=?, pages=? WHERE media_id=?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setString(1, String.join(", ", film.getCreators()));
            pstmt.setInt(2, film.getDuration());
            pstmt.setInt(3, film.getId());
            pstmt.executeUpdate();
        }
    }

    public List<Media> getAllMedia() throws SQLException{
        List<Media> result = new ArrayList<>();
        String sql = "SELECT m.id, m.type, m.name, m.genres, m.pub_year, m.rating," +
                "b.author, b.publisher, b.pages," +
                "f.creators, f.duration " +
                "FROM media m " +
                "LEFT JOIN books b ON m.id=b.media_id " +
                "LEFT JOIN films f ON m.id=f.media_id";

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()){

            while(rs.next()){
                String type = rs.getString("type");
                Media media;

                if("BOOK".equals(type)){
                    media = getBookFromRS(rs);
                } else {
                    media = getFilmFromRS(rs);
                }
                result.add(media);
            }
        }

        return result;
    }

    private Book getBookFromRS(ResultSet rs) throws SQLException{
        String genresStr = rs.getString("genres");
        Set<String> genres = new HashSet<>();
        if(genresStr!=null && !genresStr.isEmpty()){
            genres = new HashSet<>(Arrays.asList(genresStr.split(", ")));
        }
        Book book = new Book(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("author"),
                genres,
                rs.getInt("pub_year"),
                rs.getString("publisher"),
                rs.getInt("pages"),
                rs.getDouble("rating")
        );
        book.setId(rs.getInt("id"));
        return book;
    }
    private Film getFilmFromRS(ResultSet rs) throws SQLException{
        String genresStr = rs.getString("genres");
        Set<String> genres = new HashSet<>();
        if(genresStr!=null && !genresStr.isEmpty()){
            genres = new HashSet<>(Arrays.asList(genresStr.split(", ")));
        }
        String creatorsStr = rs.getString("creators");
        ArrayList<String> creators = new ArrayList<>();
        if(creatorsStr!=null && !genresStr.isEmpty()){
            creators = new ArrayList<>(Arrays.asList(creatorsStr.split(", ")));
        }
        Film film = new Film(
                rs.getInt("id"),
                rs.getString("name"),
                genres,
                rs.getInt("pub_year"),
                creators,
                rs.getInt("pages"),
                rs.getDouble("rating")
        );
        film.setId(rs.getInt("id"));
        return film;
    }

    public void deleteMedia(int id) throws SQLException{
        String sql = "SELECT type FROM media WHERE id=?";
        String type;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1, id);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    type = rs. getString("type");

                    String childSQL = "DELETE FROM " + ("BOOK".equals(type)?"books":"films") +
                            " WHERE media_id=?";
                    try (PreparedStatement childPstmt = connection.prepareStatement(childSQL)){
                        childPstmt.setInt(1, id);
                        childPstmt.executeUpdate();
                    }

                    String delSQL = "DELETE FROM media WHERE id=?";
                    try (PreparedStatement delPstmt = connection.prepareStatement(delSQL)){
                        delPstmt.setInt(1, id);
                        delPstmt.executeUpdate();
                    }
                }
            }
        }
    }

    public void clearAllTables() throws SQLException {
        String disableFK = "PRAGMA foreign_keys = OFF";
        String enableFK = "PRAGMA foreign_keys = ON";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(disableFK);

            stmt.execute("DELETE FROM books");
            stmt.execute("DELETE FROM films");
            stmt.execute("DELETE FROM media");

            ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='sqlite_sequence'");
            if (rs.next()) {
                stmt.execute("DELETE FROM sqlite_sequence WHERE name IN('media','books','films')");
            }

            stmt.execute(enableFK);
        }
    }

    public void close() throws SQLException{
        if(connection != null) connection.close();
    }
}
