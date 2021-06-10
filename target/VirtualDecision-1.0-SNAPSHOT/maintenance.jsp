<%@ page import="Objects.UserObject" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Objects.ReportObject" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>Reportes</title>
    <link rel="stylesheet" type="text/css" href="css/pasi.css">
</head>
<body>
<%
    UserObject userObject = (UserObject) request.getAttribute("userObject");
    String result = (String) request.getAttribute("result");
    if (result != null) {
%>
<p><%=result%>
</p>
<%}%>
<h1>Reportes pendientes para <%=userObject.username%>
</h1>

<%
    ArrayList<ReportObject> reportsList = (ArrayList<ReportObject>) request.getAttribute("reportsList");

    if (!reportsList.isEmpty()) {
%>
<div style="text-align: center;">
    <table>
        <tr>
            <th>Reporte</th>
            <th>Descripción</th>
            <th>Tipo de mantenimiento</th>
            <th>Gerente de soporte</th>
            <th>Observación</th>
            <th>Estado</th>
            <th>Terminar programación</th>
        </tr>

        <%
            for (ReportObject reportObject : reportsList) {

        %>
        <tr>
            <form method="post" action="Maintenance">
                <input type="hidden" name="id" value="<%=reportObject.id%>">
                <td><%=reportObject.report%>
                </td>
                <td><%=reportObject.description%>
                </td>
                <td><% if (reportObject.maintenanceType == 1){%>Mejora<%}%><% if (reportObject.maintenanceType == 2){%>Preventivo<%}%><% if (reportObject.maintenanceType == 3){%>Correctivo<%}%><% if (reportObject.maintenanceType == 4){%>Preventivo<%}%>
                </td>
                <td><%=reportObject.supportManagerUsername%>
                </td>
                <td>
                    <%if (reportObject.state == 0){%>
                    <input type="text" name="observation" placeholder="Observaciones para el Gerente"
                           value="<% if (reportObject.observation == null) {%>No hay observaciones<%}%>">
                    <%}%>
                </td>
                <td>
                    <%if (reportObject.state == 0){%>
                    En programación
                    <%}%>
                    <%if (reportObject.state == 1){%>
                    Programación finalizada
                    <%}%>
                    <%if (reportObject.state == 2){%>
                    Reporte cerrardo
                    <%}%>
                </td>
                <%if (reportObject.maintenanceEngineerID == userObject.id && reportObject.state == 0) {%>
                <td><input type="submit" name="finish" value="Finalizar programación"></td>
                <%}%>
            </form>
        </tr>
        <%
            }
        } else {%>
        <p>No existen reportes</p>
        <%}%>
    </table>
</div>
</body>