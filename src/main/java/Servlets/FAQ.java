package Servlets;

import Connections.FAQsConnection;
import Connections.ReportsConnection;
import Objects.FAQObject;
import Objects.ReportObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "FAQ", value = "/FAQ")
public class FAQ extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //Campos
        String question = request.getParameter("question");
        String answer = request.getParameter("answer");
        String reportId = request.getParameter("reportId");
        String id = request.getParameter("id");

        //Botones
        String insert = request.getParameter("insert");
        String delete = request.getParameter("delete");
        String update = request.getParameter("update");

        FAQsConnection faqsConnection = new FAQsConnection();
        ReportsConnection reportsConnection = new ReportsConnection();
        StringBuilder result = new StringBuilder();
        if (insert != null) {
            try {
                if (faqsConnection.insert(question, answer, reportId)) {
                    result.append("Inserción correcta; ");
                } else {
                    result.append("Inserción incorrecta; ");
                }
            } catch (SQLException throwables) {
                request.setAttribute("result", "Insert: " + throwables);
            }
        }

        if (update != null) {
            try {
                if (faqsConnection.update(question, answer, reportId, id)) {
                    result.append("Actualización correcta; ");
                } else {
                    result.append("Actualización incorrecta; ");
                }

            } catch (SQLException throwables) {
                result.append("Update: ").append(throwables).append("; ");
            }
        }

        if (delete != null) {
            try {
                if (faqsConnection.delete(id)) {
                    result.append("Eliminación correcta; ");
                } else {
                    result.append("Eliminación incorrecta; ");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                result.append("Delete: ").append(throwables).append("; ");
            }
        }


        ArrayList<FAQObject> faqsList = new ArrayList<>();
        ArrayList<ReportObject> reportsList = new ArrayList<>();
        try {
            faqsList = faqsConnection.getFAQsList();
            result.append("FAQsLists: Todo bien; ");
        } catch (SQLException throwables) {
            result.append("FAQsLists: ").append(throwables).append("; ");
        }

        try {
            reportsList = reportsConnection.getReportList();
            result.append("ReportsList: Todo bien; ");
        } catch (SQLException throwables) {
            result.append("ReportsLists: ").append(throwables).append("; ");
        }

        request.setAttribute("result", result.toString());
        request.setAttribute("faqsList", faqsList);
        request.setAttribute("reportsList", reportsList);

        getServletContext().getRequestDispatcher("/FAQs.jsp").forward(request, response);
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
