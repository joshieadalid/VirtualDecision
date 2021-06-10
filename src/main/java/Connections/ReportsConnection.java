package Connections;

import Objects.ReportObject;

import java.sql.*;
import java.util.ArrayList;

public class ReportsConnection {

    Connection connection;

    public ReportsConnection() {
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

    public ArrayList<ReportObject> getReportList() throws SQLException {
        ArrayList<ReportObject> reportList = new ArrayList<>();
        ResultSet rs = connection
                .prepareStatement("SELECT * FROM `Reports`")
                .executeQuery();
        while (rs.next()) {
            ReportObject reportObject = new ReportObject();
            reportObject.id = rs.getInt(1);
            reportObject.report = rs.getString(2);
            reportObject.description = rs.getString(3);
            reportObject.maintenanceType = rs.getInt(4);
            reportObject.supportManagerID = rs.getInt(5);
            reportObject.maintenanceEngineerID = rs.getInt(6);
            reportObject.observation = rs.getString(7);
            reportObject.state = rs.getInt(8);
            reportObject.supportManagerUsername = new UsersConnection().getUsernameById(reportObject.supportManagerID);
            reportObject.maintenanceEngineerUsername = new UsersConnection().getUsernameById(reportObject.maintenanceEngineerID);
            reportList.add(reportObject);
        }
        return reportList;
    }

    public boolean insert(String report, String description, String maintenanceType, String supportManagerID, String maintenanceEngineerID) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `Reports`(`Report`, `Description`, `MaintenanceType`, `SupportManagerID`, `MaintenanceEngineerID`) VALUES (?,?,?,?,?)");
        preparedStatement.setString(1, report);
        preparedStatement.setString(2, description);
        preparedStatement.setString(3, maintenanceType);
        preparedStatement.setString(4, supportManagerID);
        preparedStatement.setString(5, maintenanceEngineerID);
        int rs = preparedStatement.executeUpdate();
        return rs > 0;
    }

    public boolean update(String report, String description, String maintenanceType, String id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `Reports` SET `Report`=?,`Description`=?,`MaintenanceType`=? WHERE `ID`=?");
        preparedStatement.setString(1, report);
        preparedStatement.setString(2, description);
        preparedStatement.setString(3, maintenanceType);
        preparedStatement.setString(4, id);
        int rs = preparedStatement.executeUpdate();
        return rs > 0;
    }

    public boolean close(String id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `Reports` SET `State` = 2 WHERE `ID`=?");
        preparedStatement.setString(1, id);
        int rs = preparedStatement.executeUpdate();
        return rs > 0;
    }

    public boolean finish(String observation, String id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `Reports` SET `Observation` = ?, `State` = 1 WHERE `ID`=?");
        preparedStatement.setString(1, observation);
        preparedStatement.setString(2, id);
        int rs = preparedStatement.executeUpdate();
        return rs > 0;
    }

    public boolean delete(String id) throws SQLException {
        if (!new FAQsConnection().reportIDExists(id)) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `Reports` WHERE `ID` = ?");
            preparedStatement.setString(1, id);
            int rs = preparedStatement.executeUpdate();
            return rs > 0;
        }
        return false;
    }
}
