import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ScoreDAO {
    
    public boolean saveScore(int userId, int score, int speedIndex, int fieldExtent, int cellExtent) {
        String sql = """
                INSERT INTO scores (user_id, score, speed_index)
                VALUES (?, ?, ?)
                """;

        try (
                Connection connection = Database.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, userId);
            statement.setInt(2, score);
            statement.setInt(3, speedIndex);

            return statement.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public int getBestScoreForUser(int userId, int speedIndex) {
        String sql = """
                SELECT MAX(score) AS best_score
                FROM scores
                WHERE user_id = ? AND speed_index = ?
                """;

        try (
                Connection connection = Database.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, userId);
            statement.setInt(2, speedIndex);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getInt("best_score");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int[] getBestScoresForUser(int userId) {
        int[] highscores = new int[3];

        for (int i = 0; i < highscores.length; i++) {
            highscores[i] = getBestScoreForUser(userId, i);
        }

        return highscores;
    }
}
