import java.sql.*;

public class SQLiteExample {

    public static void main(String[] args){
        Connection connection = null;
        String url = "jdbc:sqlite:test.db";

        try{
            connection = DriverManager.getConnection(url);

            System.out.println("Соединено");
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        createNewDatabase(url);
        createNewTable(url);

        insert("John Doe", "john@example.com", url);
        insert("Jane Doe", "janeD@example.com", url);
        insert("Matt White", "matt@example.com", url);

        System.out.println("Все пользователи: ");
        selectALL(url);

        System.out.println("Изменение пользователя 3...");
        update(3, "Matt Dusek", "dusekkar@example.com", url);
        System.out.println("Изменённый список: ");
        selectALL(url);

        System.out.println("Удаляется пользователь 4...");
        delete(4, url);

        System.out.println("Итоговый список пользователей: ");
        selectALL(url);
    }

    public static void createNewDatabase(String url){
        try(Connection conn = DriverManager.getConnection(url)){
            if(conn != null){
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("Название драйвера: " + meta.getDriverName());
                System.out.println("Новая датабаза была создана");
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void createNewTable(String url){
        String sql = "CREATE TABLE IF NOT EXISTS users (\n" +
                " id integer PRIMARY KEY,\n" +
                " name text NOT NULL,\n" +
                " email text NOT NULL UNIQUE\n" +
                ");";

        try (Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()){
            stmt.execute(sql);
            System.out.println("Таблица создана успешно");
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void insert(String name, String email, String url){
        String sql = "INSERT INTO users(name, email) VALUES(?,?)";

        try (Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
            System.out.println("Добавлен пользователь: " + name);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void selectALL(String url){
        String sql = "SELECT id, name, email FROM users";

        try (Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            System.out.println("ID \tИмя \t\t\tEmail");
            System.out.println("-------------------------------------");
            while(rs.next()){
                System.out.println(
                        rs.getInt("id") + "\t" +
                                rs.getString("name") + "\t\t" +
                                rs.getString("email"));
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void update(int id, String name, String email, String url){
        String sql = "UPDATE users SET name = ?, email = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
            System.out.println("Пользователь обновлён: ID: " + id);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void delete(int id, String url){
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Пользователь удалён: ID: " + id);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
