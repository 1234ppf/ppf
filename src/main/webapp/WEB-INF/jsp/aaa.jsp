<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@page import="java.util.*"%>
<html>
<head>
</head>
<body>
    <p>${message}</p>
    <form action="/user/imageFile" id="uploadForm" method="post" enctype="multipart/form-data" name="uploadForm" target="uploadFrame">

            <input type="file"  name="file" id="file">


            <input type="submit">

    </form>
</body>
</html>
<script src="../../js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
    $(function () {

        console.log(1111);
        var a=$("#fupPhoto").val();
        console.log(a);
    })



</script>