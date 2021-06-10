package Connections;

import Objects.UserObject;

import java.sql.*;
import java.util.ArrayList;

public class UsersConnection {
    public Connection connection;

    public UsersConnection() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException ignored) {
        }

        try {
            connection = DriverManager
                    .getConnection("jdbc:mariadb://localhost:3306/VirtualDecision", "root", "");
        } catch (SQLException ignored) {
        }
    }

    public ArrayList<UserObject> getMaintenanceEngineerList() throws SQLException {
        ArrayList<UserObject> maintenanceEngineerList = new ArrayList<>();
        ResultSet rs = connection
                .prepareStatement("SELECT * FROM `Users` WHERE `Type` = 3")
                .executeQuery();
        while (rs.next()) {
            UserObject userObject = new UserObject();
            userObject.id = rs.getInt(1);
            userObject.username = rs.getString(2);
            userObject.hash = rs.getString(3);
            userObject.type = rs.getInt(4);
            maintenanceEngineerList.add(userObject);
        }
        return maintenanceEngineerList;
    }

    public UserObject login(String username, String password) throws SQLException {
        UserObject userObject = new UserObject();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `Users` WHERE `Hash` = ?");
        preparedStatement.setString(1, new Hash().generateHash(username + password));
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        userObject.id = rs.getInt(1);
        userObject.username = rs.getString(2);
        userObject.hash = rs.getString(3);
        userObject.type = rs.getInt(4);
        return userObject;
    }

    public String getUsernameById(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT `Username` FROM `Users` WHERE `ID` = ?");
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            return rs.getString(1);
        }
        return null;
    }

    public boolean register(String username1, String password1) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `Users`(`Username`,`Hash`) VALUES (?, ?)");
        preparedStatement.setString(1, username1);
        preparedStatement.setString(2, new Hash().generateHash(username1 + password1));
        int rs = preparedStatement.executeUpdate();
        return rs > 0;
    }

    public boolean update(String username1, String password1, String username2, String password2) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `Users` SET `Username`=?,`Hash`=? WHERE `Username`=? AND `Hash`=?");
        preparedStatement.setString(1, username2);
        preparedStatement.setString(2, new Hash().generateHash(username2 + password2));
        preparedStatement.setString(3, username1);
        preparedStatement.setString(4, new Hash().generateHash(username1 + password1));
        int rs = preparedStatement.executeUpdate();
        return rs > 0;
    }

    public boolean delete(String username1, String password1) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `Users` WHERE `hash` = ?");
        preparedStatement.setString(1, new Hash().generateHash(username1 + password1));
        preparedStatement.executeUpdate();
        int rs = preparedStatement.executeUpdate();
        return rs > 0;
    }
}
