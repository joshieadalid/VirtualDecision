<%@ page contentType="text/html;charset=UTF-8" %>

<html lang="es">
<head>
    <title>Mi primer Login</title>
    <!--JQUERY-->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <!--BOOTSTRAP-->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>

    <!--css-->
    <link href="css/pasi.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<%
    String result = (String) request.getAttribute("result");
    if (result != null) {
%>
<p><%=result%>
</p>
<%
    }
%>
<div class="modal-dialog text-center">
    <div class="col-sm-8 main-section">
        <div class="modal-content">

            <div style="text-align: cenx"><h1>Sesión</h1></div>
            <form class="col-12" action="Session" method="post">
                <br>
                <div class="form-group" id="user-group">
                    <input type="text" class="form-control" placeholder="Nombre de usuario" name="username1"/>
                </div>

                <div class="form-group" id="contrasena-group">
                    <input type="password" class="form-control" placeholder="Contraseña" name="password1"/>

                </div>

                <button type="submit" class="btn btn-primary" name="login">Ingresar</button>
                <button type="submit" class="btn btn-primary" name="register">Registrar</button>
                <button type="submit" class="btn btn-primary" name="delete">Eliminar</button>
                <br>
                <h2>Editar usuario</h2>
                <div class="form-group" id="user-group">
                    <input type="text" class="form-control" placeholder="Nombre de usuario" name="username2"/>
                </div>

                <div class="form-group" id="contrasena-group">
                    <input type="password" class="form-control" placeholder="Contraseña" name="password2"/>
                </div>

                <button type="submit" class="btn btn-primary" name="update">Actualizar</button>
            </form>

        </div>
    </div>
</div>
</body>
</html>