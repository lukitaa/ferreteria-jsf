<%-- 
    Document   : purchase
    Created on : 15/09/2014, 20:07:35
    Author     : alumno
--%>


<%@page import="entity.Details"%>
<%@page import="servlets.Common"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="sessionUser" class="servlets.SessionUser" scope="session"/>
<jsp:useBean id="details" type="java.util.List<Details>" scope="session"/>
<%
int total = 0;
%>   
<!DOCTYPE html>
<html lang="es" dir="ltr">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        
        <title>Ferreter&iacute;a - Detalle pedido</title>
        
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
                        <li><a href="compras/historial">Historial</a></li>
                        <li class="active"><a href="productos">Productos</a></li>
                        <% if (sessionUser.isAdmin()) { %>
                        <li><a href="usuarios">Usuarios</a></li>
                        <li><a href="ordenes">Ordenes</a></li>
                        <% } %>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a>Hola, <%= sessionUser.getUsername() %>!</a></li>
                        <li><a class="btn-logout" href="logout">Salir</a></li>
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
                    <li><a href="productos">Productos</a></li>
                    <li class="active">Detalle pedido</li>
                </ol>
                <!-- ENDS BREADCRUMBS -->
                <!-- BEGINS CONTENT -->
                <div class="jumbotron presentation products">
                    <h1 class="header">Detalle pedido</h1>
                    <p>Su pedido a sido realizado satisfactoriamente! A continuaci&oacute;n se detallan los productos incluidos.</p>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>Producto</th>
                                <th>Precio</th>
                                <th>Unidades</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Details d : details) { %>
                            <tr>
                                <td><%= d.getProducts().getProduct() %></td>
                                <td class="price"><%= d.getPrice() %></td>
                                <td class="stock"><%= d.getAmount() %></td>
                            </tr>
                            <% total += d.getAmount() * d.getPrice();
                               } %>
                        </tbody>
                    </table>
                    <p class="lead">Total: <%= total %></p>
                </div>
                <!-- ENDS CONTENT -->
            </div>
        </main>
        
        <script src="static/vendors/jquery/js/jquery.min.js"></script>
        <script src="static/vendors/bootstrap/js/bootstrap.min.js"></script>
        <script src="static/js/scripts.js"></script>
    </body>
</html>