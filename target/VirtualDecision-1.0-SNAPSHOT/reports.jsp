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
    String result = (String) request.getAttribute("result");
    if (result != null) {
%>
<p>
    <%=result%>
</p>
<%
    }
%>
<h1>Reportes de mantenimiento</h1>
<h2>Crear nuevo reporte de mantenimiento</h2>

<form method="post" action="Report">
    <input type="hidden" name="supportManagerID" value="<%=((UserObject)request.getAttribute("userObject")).id%>">
    <label>Nombre del reporte<input type="text" name="report" placeholder="Nombre del reporte"></label>
    <br>
    <label>Descripción del problema<input type="text" name="description" placeholder="Descripción del problema"></label>
    <br>
    <fieldset>
        <legend>Tipo de mantenimiento</legend>
        <label><input type="radio" name="maintenanceType" value="1" checked>Mejora</label>
        <label><input type="radio" name="maintenanceType" value="2">Preventivo</label>
        <label><input type="radio" name="maintenanceType" value="3">Correctivo</label>
        <label><input type="radio" name="maintenanceType" value="4">Perfectivo</label>
    </fieldset>
    <br>
    <label>Ingeniero de mantenimiento
        <select name="maintenanceEngineerID" required>
            <%
                ArrayList<UserObject> maintenanceEngineerList = (ArrayList<UserObject>) request.getAttribute("maintenanceEngineerList");
                for (UserObject maintenanceEngineer : maintenanceEngineerList) {%>
            <option value="<%=maintenanceEngineer.id%>">
                <%=maintenanceEngineer.username%>
            </option>
            <%}%>
        </select></label>
    <br>
    <input type="submit" name="insert" value="Crear reporte">
</form>

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
            <th>Ingeniero de mantenimiento</th>
            <th>Observación</th>
            <th>Estado</th>
            <th>Actualizar</th>
            <th>Cerrar</th>
            <th>Eliminar</th>
        </tr>

        <%
            for (ReportObject reportObject : reportsList) {
        %>
        <tr>
            <form method="post" action="Report">
                <input type="hidden" name="id" value="<%=reportObject.id%>">
                <%if (reportObject.state == 0) {%>
                <td><input type="text" name="report" placeholder="Reporte" value="<%=reportObject.report%>"></td>
                <td><input type="text" name="description" placeholder="Descripción"
                           value="<%=reportObject.description%>"></td>
                <td>
                    <fieldset>
                        <label><input type="radio" name="maintenanceType" value="1"
                                      <%if (reportObject.maintenanceType == 1){%>checked<%}%>>Mejora</label>
                        <label><input type="radio" name="maintenanceType" value="2"
                                      <%if (reportObject.maintenanceType == 2){%>checked<%}%>>Preventivo</label>
                        <label><input type="radio" name="maintenanceType" value="3"
                                      <%if (reportObject.maintenanceType == 3){%>checked<%}%>>Correctivo</label>
                        <label><input type="radio" name="maintenanceType" value="4"
                                      <%if (reportObject.maintenanceType == 4){%>checked<%}%>>Perfectivo</label>
                    </fieldset>
                </td>
                <td><%=reportObject.supportManagerUsername%>
                </td>
                <td><%=reportObject.maintenanceEngineerUsername%>
                </td>
                <td><% if (reportObject.observation == null) {%>No hay observaciones<%}%></td>
                <td>En programación</td>
                <td><input type="submit" name="update" value="Actualizar"></td>
                <td><input type="submit" name="close" value="Cerrar"></td>
                <%
                    }
                    if (reportObject.state == 1 || reportObject.state == 2) {%>
                <td><p><%=reportObject.report%>
                </p></td>
                <td><p><%=reportObject.description%>
                </p></td>
                <td><p><% if (reportObject.maintenanceType == 1){%>Mejora<%}%><% if (reportObject.maintenanceType == 2){%>Preventivo<%}%><% if (reportObject.maintenanceType == 3){%>Correctivo<%}%><% if (reportObject.maintenanceType == 4){%>Perfectivo<%}%>
                </p></td>
                <td><p><%=reportObject.supportManagerUsername%>
                </p></td>
                <td><p><%=reportObject.maintenanceEngineerUsername%>
                </p></td>
                <td><p><%=reportObject.maintenanceType%>
                </p></td>
                <td>
                    <p><%if (reportObject.state == 1) {%>Programación finalizada<%
                        }
                        if (reportObject.state == 2) {%>
                        Reporte cerrado
                        <%}%></p></td>
                <td><p>No se puede actualizar</p></td>
                <td><p>El reporte ya está cerrado</p></td>
                <%
                    }
                %>
                <td><input type="submit" name="delete" value="Eliminar"></td>
            </form>
        </tr>
        <%
            }%></table>
</div>
<%} else {%>
<p>No hay datos</p>
<%}%>
</body>