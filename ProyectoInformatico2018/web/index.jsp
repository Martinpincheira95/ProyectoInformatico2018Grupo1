<%-- 
    Document   : index
    Created on : 26-09-2018, 21:23:21
    Author     : vanes
--%>


<!DOCTYPE html>
<html>
    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
        <style>
            body {
                font-family: "Lato", sans-serif;
            }

            .sidenav {
                height: 100%;
                width: 160px;
                position: fixed;
                z-index: 1;
                top: 0;
                left: 0;
                background-color: #111;
                overflow-x: hidden;
                padding-top: 20px;
            }

            .sidenav a {
                padding: 6px 8px 6px 16px;
                text-decoration: none;
                font-size: 25px;
                color: #818181;
                display: block;
            }

            .sidenav a:hover {
                color: #f1f1f1;
            }

            .main {
                margin-left: 160px; /* Same as the width of the sidenav */
                font-size: 10px; /* Increased text to enable scrolling */
                padding: 0px 10px;
            }

            @media screen and (max-height: 450px) {
                .sidenav {padding-top: 15px;}
                .sidenav a {font-size: 18px;}
            }
        </style>
    </head>
    <ul>
        <li><a class="active" href="registro.jsp">Registro</a></li>
        <li><a href="perfil.jsp">Ingresar</a></li>
    </ul>
    <body>
        <div class="sidenav">
            <form name="forma" method="post" action="index">
                <a href="ingreso.jsp">Ingresar</a>
                <a onclick="forma.submit()" href="#">Registrarse</a>
            </form>
        </div>
        <div class="main">
            <h1>Bienvenido a la plataforma colaborativa UDEC</h1>
        </div>  
    </body>
</html>
