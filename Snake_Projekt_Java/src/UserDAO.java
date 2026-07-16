import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public boolean register(String username, String pswd) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (
                Connection connection = Database.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, username);
            statement.setString(2, pswd);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Spieler login(String username, String password) {
        String sql = "SELECT id, username FROM users WHERE username = ? AND password = ?";

        try (
                Connection connection = Database.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("username");

                return new Spieler(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
