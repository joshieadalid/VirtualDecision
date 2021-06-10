package Servlets;

import Connections.UsersConnection;
import Objects.UserObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class Session extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username1 = request.getParameter("username1");
        String password1 = request.getParameter("password1");
        String username2 = request.getParameter("username2");
        String password2 = request.getParameter("password2");
        String login = request.getParameter("login");
        String register = request.getParameter("register");
        String update = request.getParameter("update");
        String delete = request.getParameter("delete");

        if (login != null) {
            UserObject userObject = null;
            try {
                userObject = new UsersConnection().login(username1, password1);
                request.getSession().setAttribute("userObject", userObject);
                if (userObject.type == 1) {
                    getServletContext().getRequestDispatcher("/FAQ").forward(request, response);
                } else if (userObject.type == 2) {
                    request.getSession().setAttribute("userObject", userObject);
                    getServletContext().getRequestDispatcher("/Report").forward(request, response);
                } else if (userObject.type == 3) {
                    request.getSession().setAttribute("userObject", userObject);
                    getServletContext().getRequestDispatcher("/Maintenance").forward(request, response);
                } else {
                    request.setAttribute("result", "Error de sesión");
                }
            } catch (SQLException throwables) {
                request.setAttribute("result", throwables.toString());
            }
        }

        if (register != null) {
            try {
                if (new UsersConnection().register(username1, password1)) {
                    request.setAttribute("result", "Registro exitoso");
                } else {
                    request.setAttribute("result", "Error al registrar");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if (update != null) {
            try {
                if (new UsersConnection().update(username1, password1, username2, password2)) {
                    request.setAttribute("result", "Actualización exitosa");
                } else {
                    request.setAttribute("result", "Error al actualizar");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if (delete != null) {
            try {
                if (new UsersConnection().delete(username1, password1)) {
                    request.setAttribute("result", "Eliminación exitosa");
                } else {
                    request.setAttribute("result", "Error al eliminar");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        getServletContext().getRequestDispatcher("/session.jsp").forward(request, response);

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
}
