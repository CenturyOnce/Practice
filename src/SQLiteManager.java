import java.sql.*;
import java.util.*;

public class SQLiteManager {
    private Connection connection;
    private int addedNew;
    private int changed;

    public SQLiteManager(String path){
        String url = "jdbc:sqlite:" + path;
        addedNew = 0;
        changed = 0;
        try{
            connection = DriverManager.getConnection(url);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        createTables();
    }

    public void addedAndChanged(){
        System.out.println("Добавлено объектов: " + addedNew + "\nИзменено объектов: " + changed);
    }

    public void createTables(){
        String mediaSql = "CREATE TABLE IF NOT EXISTS media (" +
                " id integer PRIMARY KEY," +
                " type TEXT NOT NULL," +
                " name TEXT NOT NULL," +
                " genres TEXT," +
                " pub_year INTEGER," +
                " rating REAL)";
        String bookSql = "CREATE TABLE IF NOT EXISTS books (" +
                "media_id INTEGER PRIMARY KEY," +
                "author TEXT NOT NULL," +
                "publisher TEXT," +
                "pages INTEGER," +
                "FOREIGN KEY(media_id) REFERENCES media(id))";
        String filmSql = "CREATE TABLE IF NOT EXISTS films (" +
                "media_id INTEGER PRIMARY KEY," +
                "creators TEXT NOT NULL," +
                "duration INTEGER," +
                "FOREIGN KEY(media_id) REFERENCES media(id))";
        String gamesql = "CREATE TABLE IF NOT EXISTS games (" +
                "media_id INTEGER PRIMARY KEY," +
                "developer TEXT NOT NULL," +
                "publisher TEXt NOT NULL," +
                "platforms TEXT NOT NULL," +
                "supLanguages TEXT NOT NULL," +
                "FOREIGN KEY(media_id) REFERENCES media(id))";

        try (Statement stmt = connection.createStatement()){
            stmt.execute(mediaSql);
            stmt.execute(bookSql);
            stmt.execute(filmSql);
            stmt.execute(gamesql);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private boolean mediaExistsById(int id) throws  SQLException{
        String sql = "SELECT 1 FROM media WHERE id = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1, id);
            try(ResultSet rs = pstmt.executeQuery()){
                return rs.next();
            }
        }
    }

    private boolean identicalMediaExists(Media media) throws SQLException{
        String sql = "SELECT 1 FROM media WHERE " +
                "id = ? AND " +
                "type = ? AND " +
                "name = ? AND " +
                "genres = ? AND " +
                "pub_year = ? AND " +
                "rating = ?";

        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1, media.getId());
            pstmt.setString(2, media.getType());
            pstmt.setString(3, media.getName());
            pstmt.setString(4, String.join(", ", media.getGenres()));
            pstmt.setInt(5, media.getYear());
            pstmt.setDouble(6, media.getRating());

            try(ResultSet rs = pstmt.executeQuery()){
                if(!rs.next()) return false;
            }
        }

        if(media instanceof Book) return identicalBookExists((Book) media);
        else if(media instanceof Film) return  identicalFilmExists((Film) media);
        else if(media instanceof Game) return  identicalGameExists((Game) media);
        return false;
    }
    private boolean identicalBookExists(Book book) throws SQLException {
        String sql = "SELECT 1 FROM books WHERE " +
                "media_id IN (SELECT id FROM media WHERE name = ? AND type = 'BOOK') AND " +
                "author = ? AND " +
                "publisher = ? AND " +
                "pages = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, book.getName());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getPublisher());
            pstmt.setInt(4, book.getPages());

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }
    private boolean identicalFilmExists(Film film) throws SQLException {
        String sql = "SELECT 1 FROM films WHERE " +
                "media_id IN (SELECT id FROM media WHERE name = ? AND type = 'FILM') AND " +
                "creators = ? AND " +
                "duration = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, film.getName());
            pstmt.setString(2, String.join(", " ,film.getCreators()));
            pstmt.setInt(3, film.getDuration());

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }
    private boolean identicalGameExists(Game game) throws SQLException {
        String sql = "SELECT 1 FROM films WHERE " +
                "media_id IN (SELECT id FROM media WHERE name = ? AND type = 'FILM') AND " +
                "developer = ? AND " +
                "publisher = ? AND " +
                "platforms = ? AND " +
                "supLanguages = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, game.getName());
            pstmt.setString(2, game.getDeveloper());
            pstmt.setString(3, game.getPublisher());
            pstmt.setString(4, String.join(", " ,game.getPlatforms()));
            pstmt.setString(5, String.join(", " ,game.getSupLanguages()));

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }


    public void addMedia(Media media) throws SQLException{
        if(media.getId() > 0 && mediaExistsById(media.getId())){
            String currType = getMediaType(media.getId());
            if(currType.equals(media.getType())){
                updateMedia(media);
                if(identicalMediaExists(media)) changed += 1;
                return;
            } else System.err.println("Объекты совпадают по ID, но их типы разные");
        }

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

                    if(media.getType().equals(Book.TYPE)){
                        addBook((Book) media, mediaId);
                    } else if(media.getType().equals(Film.TYPE)){
                        addFilm((Film) media, mediaId);
                    } else if(media.getType().equals(Game.TYPE)){
                        addGame((Game) media, mediaId);
                    }
                    addedNew += 1;
                }
            }
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
            pstmt.executeUpdate();
        }
    }
    private void addGame(Game game, int mediaId) throws SQLException{
        String sql = "INSERT INTO games(media_id, developer, publisher, platforms, supLanguages) VALUES(?,?,?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1, mediaId);
            pstmt.setString(2, game.getDeveloper());
            pstmt.setString(3, game.getPublisher());
            pstmt.setString(4, String.join(", ", game.getPlatforms()));
            pstmt.setString(5, String.join(", ", game.getSupLanguages()));
            pstmt.executeUpdate();
        }
    }

    private String getMediaType(int id) throws SQLException{
        String sql = "SELECT type FROM media WHERE id = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    return rs.getString("type");
                }
            }
        }
        throw new SQLException("Медиа с id " + id + " не найдено");
    }

    public void updateMedia(Media media){
        String sql = "UPDATE media SET name=?, genres=?, pub_year=?, rating=? WHERE id=?";

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
            } else if(media instanceof Game){
                updateGame((Game) media);
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
        String sql = "UPDATE films SET creators=?, duration=? WHERE media_id=?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setString(1, String.join(", ", film.getCreators()));
            pstmt.setInt(2, film.getDuration());
            pstmt.setInt(3, film.getId());
            pstmt.executeUpdate();
        }
    }
    private void updateGame(Game game) throws SQLException{
        String sql = "UPDATE films SET developer=?, publisher=?, platforms=?, supLanguages=? WHERE media_id=?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setString(1, game.getDeveloper());
            pstmt.setString(2, game.getPublisher());
            pstmt.setString(3, String.join(", ", game.getPlatforms()));
            pstmt.setString(4, String.join(", ", game.getSupLanguages()));
            pstmt.setInt(5, game.getId());
            pstmt.executeUpdate();
        }
    }

    public List<Media> getAllMedia() throws SQLException{
        List<Media> result = new ArrayList<>();
        String sql = "SELECT m.id, m.type, m.name, m.genres, m.pub_year, m.rating," +
                "b.author, b.publisher, b.pages," +
                "f.creators, f.duration, " +
                "g.developer, g.publisher, g.platforms, g.supLanguages " +
                "FROM media m " +
                "LEFT JOIN books b ON m.id=b.media_id " +
                "LEFT JOIN films f ON m.id=f.media_id " +
                "LEFT JOIN games g ON m.id=g.media_id";

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()){

            while(rs.next()){
                String type = rs.getString("type");
                Media media;

                if(Book.TYPE.equals(type)){
                    media = getBookFromRS(rs);
                } else if(Film.TYPE.equals(type)){
                    media = getFilmFromRS(rs);
                } else {
                    media = getGameFromRS(rs);
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
        if(creatorsStr!=null && !creatorsStr.isEmpty()){
            creators = new ArrayList<>(Arrays.asList(creatorsStr.split(", ")));
        }
        Film film = new Film(
                rs.getInt("id"),
                rs.getString("name"),
                genres,
                rs.getInt("pub_year"),
                creators,
                rs.getInt("duration"),
                rs.getDouble("rating")
        );
        film.setId(rs.getInt("id"));
        return film;
    }
    private Game getGameFromRS(ResultSet rs) throws SQLException{
        String genresStr = rs.getString("genres");
        Set<String> genres = new HashSet<>();
        if(genresStr!=null && !genresStr.isEmpty()){
            genres = new HashSet<>(Arrays.asList(genresStr.split(", ")));
        }
        String platformsStr = rs.getString("platforms");
        Set<String> platforms = new HashSet<>();
        if(platformsStr!=null && !platformsStr.isEmpty()){
            platforms = new HashSet<>(Arrays.asList(platformsStr.split(", ")));
        }
        String supLanguagesStr = rs.getString("genres");
        Set<String> supLanguages = new HashSet<>();
        if(supLanguagesStr!=null && !supLanguagesStr.isEmpty()){
            supLanguages = new HashSet<>(Arrays.asList(supLanguagesStr.split(", ")));
        }
        Game game = new Game(
                rs.getInt("id"),
                rs.getString("name"),
                genres,
                rs.getInt("pub_year"),
                rs.getString("developer"),
                rs.getString("publisher"),
                platforms,
                supLanguages,
                rs.getDouble("rating")
        );
        game.setId(rs.getInt("id"));
        return game;
    }

    public ArrayList<Media> searchByName(String searchTerm) throws SQLException{
        ArrayList<Media> result = new ArrayList<>();
        String sql = "SELECT m.id, m.type, m.name, m.genres, m.pub_year, m.rating," +
                "b.author, b.publisher, b.pages," +
                "f.creators, f.duration," +
                "g.developer, g.publisher, g.platforms, g.supLanguages " +
                "FROM media m " +
                "LEFT JOIN books b ON m.id=b.media_id " +
                "LEFT JOIN films f ON m.id=f.media_id " +
                "LEFT JOIN games g ON m.id=g.media_id " +
                "WHERE m.name LIKE ? OR " +
                "m.genres LIKE ? OR " +
                "b.author LIKE ? OR " +
                "f.creators LIKE ? OR " +
                "m.type LIKE ? OR " +
                "g.developer LIKE ? OR " +
                "b.publisher LIKE ? OR " +
                "g.publisher LIKE ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setString(1, searchTerm);
            pstmt.setString(2, searchTerm);
            pstmt.setString(3, searchTerm);
            pstmt.setString(4, searchTerm);
            pstmt.setString(5, searchTerm);
            pstmt.setString(6, searchTerm);
            pstmt.setString(7, searchTerm);
            pstmt.setString(8, searchTerm);

            try (ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                    String type = rs.getString("type");
                    Media media = null;

                    if(type!=null){
                        if(Book.TYPE.equals(type)){
                            media = getBookFromRS(rs);
                        } else if(Film.TYPE.equals(type)){
                            media = getFilmFromRS(rs);
                        } else if(Game.TYPE.equals(type)){
                            media = getGameFromRS(rs);
                        }
                    }

                    if(media!=null){
                        result.add(media);
                    }
                }
            }
        }
        return result;
    }

    public void deleteMedia(int id) throws SQLException{
        String sql = "SELECT type FROM media WHERE id=?";
        String type;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1, id);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    type = rs. getString("type");
                    String table;
                    if(Book.TYPE.equals(type)) table = "books";
                    else if(Film.TYPE.equals(type)) table = "films";
                    else table = "games";
                    String childSQL = "DELETE FROM " + table + " WHERE media_id=?";
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
            stmt.execute("DELETE FROM games");
            stmt.execute("DELETE FROM media");

            ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='sqlite_sequence'");
            if (rs.next()) {
                stmt.execute("DELETE FROM sqlite_sequence WHERE name IN('media','books','films')");
            }

            stmt.execute(enableFK);
        }
        System.out.println("Таблицы успешно очищены.");
    }
}
