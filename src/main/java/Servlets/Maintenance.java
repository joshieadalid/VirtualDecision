package Servlets;

import Connections.ReportsConnection;
import Connections.UsersConnection;
import Objects.ReportObject;
import Objects.UserObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "Maintenance", value = "/Maintenance")
public class Maintenance extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


        //Botones
        String finish = request.getParameter("finish");

        UsersConnection usersConnection = new UsersConnection();
        ReportsConnection reportsConnection = new ReportsConnection();

        StringBuilder result = new StringBuilder();
        if (finish != null) {
            try {
                String observation = request.getParameter("observation");
                String id = request.getParameter("id");
                if (new ReportsConnection().finish(observation, id)) {
                    result.append("Programación finalizada; ");
                } else {
                    result.append("Error al finalizar la programación; ");
                }
            } catch (SQLException throwables) {
                request.setAttribute("result", throwables + "; ");
            }
        }

        ArrayList<ReportObject> reportsList = new ArrayList<>();
        ArrayList<UserObject> maintenanceEngineerList = new ArrayList<>();

        try {
            reportsList = reportsConnection.getReportList();
            result.append("MaintenanceEngineerList: Todo bien; ");
        } catch (SQLException throwables) {
            result.append("MaintenanceEngineerList: ").append(throwables).append("; ");
        }

        try {
            maintenanceEngineerList = usersConnection.getMaintenanceEngineerList();
            result.append("ReportsList: Todo bien");
        } catch (SQLException throwables) {
            result.append("ReportsList: ").append(throwables).append("; ");
        }

        request.setAttribute("userObject", request.getSession().getAttribute("userObject"));
        request.setAttribute("reportsList", reportsList);
        request.setAttribute("maintenanceEngineerList", maintenanceEngineerList);
        request.setAttribute("result", result.toString());
        getServletContext().getRequestDispatcher("/maintenance.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }
}
