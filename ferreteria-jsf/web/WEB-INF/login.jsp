<%-- 
    Document   : login
    Created on : Sep 4, 2014, 12:55:25 AM
    Author     : Lucio Martinez <luciomartinez at openmailbox dot org>
--%>

<%@page import="entity.Users"%>
<%@page import="controllers.LoginController"%>
<%@page import="servlets.Common"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
String username = request.getParameter("username");
%>
<!DOCTYPE html>
<html lang="es" dir="ltr">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        
        <title>Ferreter&iacute;a - Login</title>
        
        <base href="${pageContext.request.contextPath}/" >
        
        <link href="static/css/styles.css" rel="stylesheet">
        <link href="static/vendors/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="static/vendors/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet">
        
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
            <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
    </head>
    <body>
        
        <!-- BEGINS NAV -->
        <nav class="navbar navbar-default" role="navigation">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                        <span class="sr-only">Activar navegaci&oacute;n</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="inicio">Ferreter&iacute;a</a>
                </div>
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                        <li><a href="inicio">Inicio</a></li>
                        <li class="active"><a href="login.jsp">Login</a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a><%= new SimpleDateFormat("dd 'of' MMM, yyyy").format(Calendar.getInstance().getTime()) %></a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <!-- ENDS NAV -->
        
        <main role="main" class="container">
            <div class="col-md-10 col-md-offset-1">
                <!-- BEGINS BREADCRUMBS -->
                <ol class="breadcrumb">
                    <li><a href="inicio">Inicio</a></li>
                    <li class="active">Login</li>
                </ol>
                <!-- ENDS BREADCRUMBS -->
                <!-- BEGINS CONTENT -->
                <div class="jumbotron presentation login">
                    <h1>Bienvenido a Ferreter&iacute;a!</h1>
                    <p>Inicia sesi&oacute;n para entrar al sistema de compras.</p>
                    
                    <!-- Intento de login fallido -->
                    <% if(!username.isEmpty()) { %>
                    <div class="alert alert-danger alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Cerrar</span></button>
                        <strong>Error</strong> La combinaci&oacute;n usuario contraseña es incorrecta.
                    </div>
                    <% } %> 
                    
                    <form role="form" class="form form-horizontal" method="POST" action="login">
                        <div class="form-group">
                            <label for="username-login" class="col-sm-3 control-label">Nombre de Usuario</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" name="username" id="username-login" placeholder="pepe" value="<%= ((username != null)?username:"") %>" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="password-login" class="col-sm-3 control-label">Contraseña</label>
                            <div class="col-sm-7">
                                <input type="password" class="form-control" name="password" id="password-login" placeholder="C0n7r@s3ñ@" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-3 col-sm-7">
                                <button type="submit" name="submit" value="submit" class="btn btn-default">Iniciar Sesi&oacute;n</button>
                            </div>
                        </div>
                    </form>
                </div>
                <!-- ENDS CONTENT -->
            </div>
        </main>
        
        <script src="static/vendors/jquery/js/jquery.min.js"></script>
        <script src="static/vendors/bootstrap/js/bootstrap.min.js"></script>
        <script src="static/js/scripts.js"></script>
    </body>
</html>
