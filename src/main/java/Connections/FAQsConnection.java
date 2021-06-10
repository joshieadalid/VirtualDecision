package Connections;

import Objects.FAQObject;

import java.sql.*;
import java.util.ArrayList;

public class FAQsConnection {

    Connection connection;

    public FAQsConnection() {
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

    public ArrayList<FAQObject> getFAQsList() throws SQLException {
        ArrayList<FAQObject> faqList = new ArrayList<>();

        ResultSet rs = connection
                .prepareStatement("SELECT * FROM `FAQs`")
                .executeQuery();
        while (rs.next()) {
            FAQObject faqItem = new FAQObject();
            faqItem.id = rs.getInt(1);
            faqItem.question = rs.getString(2);
            faqItem.answer = rs.getString(3);
            faqItem.reportId = rs.getInt(4);
            faqList.add(faqItem);
        }
        return faqList;
    }

    public boolean insert(String question, String answer, String reportId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `FAQs`(`Question`, `Answer`, `ReportID`) VALUES (?,?,?)");
        preparedStatement.setString(1, question);
        preparedStatement.setString(2, answer);
        preparedStatement.setString(3, reportId);
        int rs = preparedStatement.executeUpdate();
        return rs > 0;
    }

    public boolean update(String question, String answer, String reportId, String id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `FAQs` SET `Question`=?,`Answer`=?,`ReportID`=? WHERE `ID`=?");
        preparedStatement.setString(1, question);
        preparedStatement.setString(2, answer);
        preparedStatement.setString(3, reportId);
        preparedStatement.setString(4, id);
        int rs = preparedStatement.executeUpdate();
        return rs > 0;

    }

    public boolean delete(String id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `FAQs` WHERE `ID` = ?");
        preparedStatement.setString(1, id);
        int rs = preparedStatement.executeUpdate();
        return rs > 0;
    }

    public boolean reportIDExists(String id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT `ReportID` FROM `FAQs` WHERE `ReportID`=?");
        preparedStatement.setString(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            return true;
        }
        return false;
    }
}
