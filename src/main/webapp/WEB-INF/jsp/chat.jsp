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
    <input id="loginUserName" hidden value="${user.username}">
    <div class="talk_con">
        <div class="talk_show" id="words">
            <%--<div class="atalk"><span id="asay">A说：吃饭了吗？</span></div>
            <div class="btalk"><span id="bsay">${user.username}说：还没呢，你呢？</span></div>--%>
        </div>
        <div class="talk_input">
            <select class="whotalk" id="who">
                <c:forEach items="${elseUser}" var="list">
                    <option value="${list.id}">${list.username}</option>
                </c:forEach>
            </select>
            <input  class="talk_word" id="chatWords">
            <input type="button" value="发送" class="talk_sub" id="talksub">
            <input type="button" value="下线" class="talk_sub" id="close">
            <input type="button" value="重新连接" class="talk_sub" id="reconnect">
        </div>
    </div>
</body>
</html>
<script src="../../js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
            var url="ws://localhost:8081/chat/${user.id}";
            var webSocket;
            function websocket(){
                webSocket= new WebSocket(url);
                webSocket.onopen = function(event)
                {
                    alert("数据链接中...");
                };
                webSocket.onerror = function(event){
                    alert("数据链接失败...");
                };
                webSocket.onmessage = function (event)
                {
                    var received_msg = event.data.split("|");
                    if(received_msg[0]!=${user.id}){
                        var receiveId=received_msg[0];
                        var receiveName=received_msg[1];
                        var receiveMessage=received_msg[2];
                        $("#words").append('<div class="atalk"><span>'+receiveName+':' + receiveMessage +'</span></div>');
                    }
                    alert("数据已接收..."+received_msg);
                };
                webSocket.onclose = function(event)
                {
                    alert("连接已关闭...");
                };
            }
            if ("WebSocket" in window)
            {
                alert("您的浏览器支持 WebSocket!");
                websocket();
            }
            else
            {
                alert("您的浏览器不支持 WebSocket!");
            }
            $("#close").click(function(){
                webSocket.close();
                alert("连接关闭...");
            });
            $("#reconnect").click(function() {

                websocket();
                alert("重新连接...");

            });




            $("#talksub").click(function(){
                //定义空字符串
                send();
                if($("#chatWords").val() == ""){
                    alert("消息不能为空");
                    return;
                }
                var loginUserName=$("#loginUserName").val();
                var chatWords=$("#chatWords").val();
                $("#words").append('<div class="btalk"><span>'+loginUserName+':' + chatWords +'</span></div>');
            });





            function send() {
                if(webSocket != null) {
                    var userTo = $("#who").val();
                    var message = $("#chatWords").val();
                    message=userTo+"|"+message;
                    webSocket.send(message);
                } else {
                    alert('未与服务器链接.');
                }
            }





</script>