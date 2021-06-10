<%@ page import="java.util.ArrayList" %>
<%@ page import="Objects.FAQObject" %>
<%@ page import="Objects.ReportObject" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>Preguntas frecuentes</title>
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

<h1>Preguntas frecuentes</h1>
<h2>Crear nuevo FAQ</h2>
<p>Refrescar la página repite la última acción realizada</p>
<form method="post" action="FAQ">
    <label>Nueva pregunta<input type="text" id="question" name="question" placeholder="Pregunta"></label>
    <br>
    <label>Nueva respuesta<input type="text" id="answer" name="answer" placeholder="Respuesta"></label>
    <br>
    <select name="reportId">
        <option value="-1">Sin reporte asociado</option>
        <%
            ArrayList<FAQObject> faqsList = (ArrayList<FAQObject>) request.getAttribute("faqsList");
            ArrayList<ReportObject> reportsList = (ArrayList<ReportObject>) request.getAttribute("reportsList");
        %>
        <%
            for (ReportObject reportObject : reportsList) {
                if (reportObject.state == 1) {
        %>
        <option value="<%=reportObject.id%>">
            <%=reportObject.report%>
        </option>
        <%
                }
            }
        %>

    </select>
    <input type="submit" name="insert" value="Crear FAQ">
</form>

<div>
<%
    if (!faqsList.isEmpty()) {%>
<table>
    <tr>
        <th>Pregunta</th>
        <th>Respuesta</th>
        <th>Reporte asociado</th>
        <th>Actualizar</th>
        <th>Eliminar</th>
    </tr>

    <%
        for (FAQObject faqObject : faqsList) {
    %>
    <tr>
        <form method="post" action="FAQ">
            <td><input type="text" name="question" placeholder="Pregunta" value="<%=faqObject.question%>"></td>
            <td><input type="text" name="answer" placeholder="Pregunta" value="<%=faqObject.answer%>"></td>

            <td>
                <select name="reportId">
                    <option value="-1">Sin reporte asociado</option>
                    <%
                        for (ReportObject reportObject : reportsList) {
                            if (reportObject.state == 1) {
                    %>
                    <option value="<%=reportObject.id%>"
                            <%if (reportObject.id == faqObject.reportId){%>selected="selected"<%}%>>
                        <%=reportObject.report%>
                    </option>
                    <%
                            }
                        }
                    %>
                </select>
            </td>
            <input type="hidden" name="id" value="<%=faqObject.id%>">
            <td><input type="submit" name="update" value="Actualizar"></td>
            <td><input type="submit" name="delete" value="Eliminar"></td>
        </form>
    </tr>
    <%}%>
</table>
<%} else {%>
<p>No hay datos</p>
<%}%>
</div>
</body>