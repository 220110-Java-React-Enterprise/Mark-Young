package bankingApp;

import java.sql.*;

public class UserRepo implements DataSourceCRUD<UserData> {
    private final Connection connection;
    public UserRepo() {
        connection = ConnectionMgr.getConnection();
    }

    @Override
    public UserData create(UserData model) {
        try {
            String sql = "INSERT INTO userData (first_name, last_name, username, " +
                    "password, email) VALUES (?,?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, model.getFirstName());
            pstmt.setString(2, model.getLastName());
            pstmt.setString(3, model.getUsername());
            pstmt.setString(4, model.getPassword());
            pstmt.setString(5, model.getEmail());

            pstmt.executeUpdate();
            System.out.println("successfully created user in database.");

            ResultSet res = pstmt.getGeneratedKeys();
            Integer newID = 0;
            if (res.next()) {
                newID = Integer.parseInt(res.getString(1));
                model.setUserID(newID);
            }

            return model;

        } catch (SQLException e) {
            System.out.println("We are having difficulties accessing the database.");
        }
        return null;
    }

    @Override
    public UserData readEmail(UserData model) {
        try {
            String sql2 = "SELECT * FROM userData WHERE email = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, model.getEmail());
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                return null;
            } else {
                model.setUserID(rs.getInt("userID"));
                model.setFirstName(rs.getString("first_name"));
                model.setLastName((rs.getString("last_name")));
                model.setUsername(rs.getString("username"));
                model.setPassword(rs.getString("password"));
                model.setEmail(rs.getString("email"));
            }

            return model;
        } catch (SQLException e) {
            System.out.println("failed. We had trouble connecting.");
        }
        return null;
    }

    @Override
    public UserData update(UserData model) {
        try {
            String sql = "UPDATE userData SET first_name = ?, last_name = ?, " +
                    "username = ?, password = ?, email = ? WHERE userID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, model.getFirstName());
            pstmt.setString(2, model.getLastName());
            pstmt.setString(3, model.getUsername());
            pstmt.setString(4, model.getPassword());
            pstmt.setString(5, model.getEmail());
            pstmt.setInt(6, model.getUserID());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("failed. We had trouble connecting.");
        }
        return model;
    }

    @Override
    public void delete(Integer id) {
        try {
            String sql = "DELETE FROM userData WHERE userID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("failed. We had trouble connecting.");
        }
    }
}
