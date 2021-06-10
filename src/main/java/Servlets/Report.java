package Servlets;

import Connections.ReportsConnection;
import Connections.UsersConnection;
import Objects.ReportObject;
import Objects.UserObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Report extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        //Campos en el formulario
        String supportManagerID = request.getParameter("supportManagerID");
        String report = request.getParameter("report");
        String description = request.getParameter("description");
        String maintenanceType = request.getParameter("maintenanceType");
        String maintenanceEngineerID = request.getParameter("maintenanceEngineerID");//ID del Ingeniero de mantenimiento asignado
        String id = request.getParameter("id");
        //Botones
        String insert = request.getParameter("insert");
        String update = request.getParameter("update");
        String close = request.getParameter("close");
        String delete = request.getParameter("delete");


        UsersConnection usersConnection = new UsersConnection();
        ReportsConnection reportsConnection = new ReportsConnection();

        ArrayList<UserObject> maintenanceEngineerList = new ArrayList<>();
        ArrayList<ReportObject> reportsList = new ArrayList<>();

        StringBuilder result = new StringBuilder();
        if (insert != null) {
            try {
                if (reportsConnection.insert(report, description, maintenanceType, supportManagerID, maintenanceEngineerID)) {
                    result.append("Inserción correcta; ");
                } else {
                    result.append("Inserción incorrecta; ");
                }
            } catch (SQLException throwables) {
                result.append("Insert: ").append(throwables).append("; ");
            }
        }

        if (update != null) {
            try {
                if (reportsConnection.update(report, description, maintenanceType, id)) {
                    result.append("Actualización correcta; ");
                } else {
                    result.append("Actualización incorrecta; ");
                }
            } catch (SQLException throwables) {
                result.append("update: ").append(throwables).append("; ");
            }
        }

        if (close != null) {
            try {
                if (reportsConnection.close(id)) {
                    result.append("Reporte cerrado; ");
                } else {
                    result.append("Error al cerrar el reporte; ");
                }
            } catch (SQLException throwables) {
                result.append("Close: ").append(throwables).append("; ");
            }
        }

        if (delete != null) {
            try {
                if (reportsConnection.delete(id)) {
                    result.append("Eliminación correcta; ");
                } else {
                    result.append("Ya existe un FAQ asociado a este reporte; ");
                }
            } catch (SQLException throwables) {
                result.append("Delete: ").append(throwables).append("; ");
            }
        }

        try {
            maintenanceEngineerList = usersConnection.getMaintenanceEngineerList();
            result.append("MaintenanceEngineerList: Todo bien; ");
        } catch (SQLException throwables) {
            result.append("MaintenanceEngineerList: ").append(throwables).append("; ");
        }

        try {
            reportsList = reportsConnection.getReportList();
            result.append("ReportsList: Todo bien; ");
        } catch (SQLException throwables) {
            result.append("ReportsList: ").append(throwables).append("; ");
        }

        request.setAttribute("userObject", request.getSession().getAttribute("userObject"));
        request.setAttribute("reportsList", reportsList);
        request.setAttribute("maintenanceEngineerList", maintenanceEngineerList);
        request.setAttribute("result", result.toString());
        getServletContext().getRequestDispatcher("/reports.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
