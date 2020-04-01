<%-- 
    Document   : index
    Created on : Mar 31, 2020, 3:28:51 PM
    Author     : Alexk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Отправка собщений</title>
        <style> 
            form {
                float: left;
                border: inset 2px green;
                text-align: center;
                padding: 15px;
            }
        </style>     
    </head>
    <body>
        <h1>Отправьте одно из сообщений</h1>
        <form action="Messanger">
            <h4>Use for string message </h4>
            <input type="text" name="message" value="" />
            <input type="hidden" name="choice" value="text" />
            <input type="submit" value="Отправить" name="send" />            
        </form>
        
        <form action="Messanger">
            <h4>Use for number message </h4>
            <input type="text" name="message" value=""/>
            <input type="hidden" name="choice" value="number"/>
            <input type="submit" value="Отправить" name="send" />            
        </form>    
    </body>
</html>
