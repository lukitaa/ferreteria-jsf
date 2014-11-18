<%-- 
    Document   : details
    Created on : Oct 19, 2014, 12:54:45 PM
    Author     : Lucio Martinez <luciomartinez at openmailbox dot org>
--%>


<%@page import="entity.Products"%>
<%@page import="entity.Details"%>
<%@page import="java.util.List"%>
<%@page import="servlets.Common"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="sessionUser" class="servlets.SessionUser" scope="session"/>
<jsp:useBean id="purchaseDetails" type="java.util.List<Details>" scope="session"/>
<%
// Initializate auxiliar variables
Products p = null;
boolean itemIsOverProductStock = false;
int total = 0, stockToBuy = 0, realStock = 0;
%>   
<!DOCTYPE html>
<html lang="es" dir="ltr">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        
        <title>Ferreter&iacute;a - Ver carrito</title>
        
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
                    <li class="active">Ver Carrito</li>
                </ol>
                <!-- ENDS BREADCRUMBS -->
                <!-- BEGINS CONTENT -->
                <div class="jumbotron presentation products">
                    <h1 class="header">Ver carrito</h1>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>Producto</th>
                                <th>Precio</th>
                                <th>Unidades</th>
                                <th>Stock</th>
                                <th>Quitar</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%                             
                            for (Details d : purchaseDetails) {
                                // Recover product data
                                p = d.getProducts();
                                // Differentiate the amount of unities that
                                // the user want to buy with the real stock
                                stockToBuy = d.getAmount(); 
                                realStock  = p.getStock();
                                // Check if the user's wishes are over the reality
                                // In such case scenario, I would congratulate him :-)
                                itemIsOverProductStock = (stockToBuy <= 0 || stockToBuy > realStock);
                            %>
                            <tr class="<%= itemIsOverProductStock?"danger":"" %>">
                                <td><%= p.getProduct() %></td>
                                <td><%= d.getPrice() %></td>
                                <td><%= stockToBuy %></td>
                                <td><%= realStock %></td>
                                <td><a href="carrito/quitar?producto=<%= p.getIdProduct() %>" class="btn btn-xs btn-warning" title="Quitar del pedido"><span aria-hidden="true">&times;</span><span class="sr-only">Quitar</span></a></td>
                            </tr>
                            <%
                                total += d.getAmount() * d.getPrice();
                            } %>
                        </tbody>
                    </table>
                    <p class="lead">Total: <%= total %></p>
                    <a href="carrito/comprar" class="btn btn-info" <%= itemIsOverProductStock?"disabled":""%>>Realizar Pedido</a>
                    <h4 class="text-muted">Advertencia: no se podra realizar la compra si la cantidad de unidades de un art&iacute;culo supera el stock disponible.</h4>
                </div>
                <!-- ENDS CONTENT -->
            </div>
        </main>
        
        <script src="static/vendors/jquery/js/jquery.min.js"></script>
        <script src="static/vendors/bootstrap/js/bootstrap.min.js"></script>
        <script src="static/js/scripts.js"></script>
    </body>
</html>