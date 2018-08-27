<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<html>
<head>
    <script src="../../js/jquery-1.8.3.min.js"></script>
    <script src="../../js/jquery.validate.min.js"></script>
    <style type="text/css">
        .d1{
            border:1px solid red;
            width:30%;
            height:30%;
            text-align:center;
            line-height:40px;
            margin:0px auto;
        }
    </style>
</head>
<body>
<div class="d1">
    <form action="/sys/login" id="loginForm" method="post"
          enctype="multipart/form-data" name="loginForm" >
        <div>
            <span>*登录名：</span>
            <input name="username" id="username"/>
        </div>
        <div>
            <span>*密&nbsp&nbsp&nbsp码：</span>
            <input type="password"  name="password" id="password"/>
        </div>
            <input type="submit" value="登录"/>
    </form>
</div>
</body>
</html>
<script type="text/javascript">
    $(function () {
        var error = '${error}';
        if(error){
            alert('${message}');
        }

        $("#loginForm").validate({
            rules : {
                username : {
                    required:true,
                },
                password : {
                    required:true,
                },
            },
            messages : {
                username : "请选择有效选项",
                password : "请输入有效内容",
            },
            submitHandler : function(form) {
                form.submit();
            }

        });
    })
</script>