<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@page import="java.util.*"%>
<html>
<head>
    <link rel="stylesheet" href="../../css/chat.css">


</head>
<body>
    <p>欢迎:${user.username}来到聊天室！</p>
    <div class="talk_con">
        <div class="talk_show" id="words">
            <div class="atalk"><span id="asay">A说：吃饭了吗？</span></div>
            <div class="btalk"><span id="bsay">B说：还没呢，你呢？</span></div>
        </div>
        <div class="talk_input">
            <select class="whotalk" id="who">
                <option value="0">A说：</option>
                <option value="1">B说：</option>
            </select>
            <input  class="talk_word" id="chatWords">
            <input type="button" value="发送" class="talk_sub" id="talksub">
        </div>
    </div>
</body>
</html>
<script src="../../js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
        $(function () {


            $("#talksub").click(function(){
                //定义空字符串
                
                var str = "";
                if($("#chatWords").val() == ""){
                    // 消息为空时弹窗
                    alert("消息不能为空");
                    return;
                }
                if($("#who").val() == 0){
                    //如果Who.value为0n那么是 A说
                    $("#words").append('<div class="atalk"><span>A说 :' + $("#chatWords").val() +'</span></div>');
                }
                else{
                    $("#words").append('<div class="btalk"><span>B说 :' + $("#chatWords").val() +'</span></div>');
                }
                // 将之前的内容与要发的内容拼接好 提交
                console.log(str);

            });

        })


</script>