package ppf.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.socket.server.standard.SpringConfigurator;
import ppf.bean.User;
import ppf.service.IUserService;
import ppf.service.impl.UserServiceImpl;

//websocket连接URL地址和可被调用配置

@ServerEndpoint(value="/chat/{userId}",configurator = SpringConfigurator.class)
public class WebSocketChat {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(WebSocketChat.class);
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //记录每个用户下多个终端的连接
    private static Map<String, Set<WebSocketChat>> userSocket = new HashMap<>();

    //需要session来对用户发送数据, 获取连接特征userId
    private Session session;
    private String userId;


    /**
     * @Title: onOpen
     * @Description: websocekt连接建立时的操作
     * @param @param userId 用户id
     * @param @param session websocket连接的session属性
     * @param @throws IOException
     */
    @OnOpen
    public void onOpen(@PathParam("userId") String userId,Session session) throws IOException{
        this.session = session;
        this.userId = userId;
        onlineCount++;
        //根据该用户当前是否已经在别的终端登录进行添加操作
        if (userSocket.containsKey(this.userId)) {
            System.out.println("当前用户id:{"+this.userId+"}已有其他终端登录");
            userSocket.get(this.userId).add(this); //增加该用户set中的连接实例
        }else {
            System.out.println("当前用户id:{"+this.userId+"}第一个终端登录");
            Set<WebSocketChat> addUserSet = new HashSet<>();
            addUserSet.add(this);
            userSocket.put(this.userId, addUserSet);
        }
        System.out.println(userSocket);
        System.out.println("用户{"+userId+"}登录的终端个数是为{"+userSocket.get(this.userId).size()+"}");
        System.out.println("当前在线用户数为：{"+userSocket.size()+"},所有终端个数为：{"+onlineCount+"}");
    }

    /**
     * @Title: onClose
     * @Description: 连接关闭的操作
     */
    @OnClose
    public void onClose(){
        //移除当前用户终端登录的websocket信息,如果该用户的所有终端都下线了，则删除该用户的记录
        if (userSocket.get(this.userId).size() == 0) {
            userSocket.remove(this.userId);
        }else{
            userSocket.get(this.userId).remove(this);
        }
        System.out.println("用户{"+this.userId+"}登录的终端个数是为{"+userSocket.get(this.userId).size()+"}");
        System.out.println("当前在线用户数为：{"+userSocket.size()+"},所有终端个数为：{"+onlineCount+"}");
    }

    /**
     * @Title: onMessage
     * @Description: 收到消息后的操作
     * @param @param message 收到的消息
     * @param @param session 该连接的session属性
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("收到"+this.userId+"用户的"+message);
        if(session ==null)  System.out.println("session null");
        //测试向客户端发送消息发送
        sendMessageToOneUser(this.userId, message);
    }

    /**
     * @Title: onError
     * @Description: 连接发生错误时候的操作
     * @param @param session 该连接的session
     * @param @param error 发生的错误
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("用户id为：{"+this.userId+"}的连接发送错误");
        error.printStackTrace();
    }

    public Boolean sendMessageToOneUser(String userId,String message){
        /*因为这不是springmvc，故通过反射加载获得userservice*/
        String sendUserno = message.split("[|]")[0];
        UserServiceImpl userService=(UserServiceImpl)ContextLoader.getCurrentWebApplicationContext().getBean("userService");

        User user=userService.selectUser(Integer.parseInt(userId));
        String userName=user.getUsername();
        String sendMessage = userId+"|"+userName+"|"+message.split("[|]")[1];
        System.out.println("1"+sendUserno);
        System.out.println("2"+userSocket);
        System.out.println("3"+userSocket.get(sendUserno));
        try {
            if (userSocket.get(sendUserno)!=null){
                WebSocketChat WS=userSocket.get(sendUserno).iterator().next();
                System.out.println(WS);
                WS.session.getBasicRemote().sendText(sendMessage);

                return true;
        }else {

            return false;
        }
        }catch (IOException e){
            e.printStackTrace();
            System.out.println(" 给用户id为：{"+sendUserno+"}发送消息失败");
            return false;
        }


    }
    /**
     * @Title: sendMessageToUser
     * @Description: 发送消息给用户下的所有终端
     * @param @param userId 用户id
     * @param @param message 发送的消息
     * @param @return 发送成功返回true，反则返回false
     */
    public Boolean sendMessageToUser(String userId,String message){
        if (userSocket.containsKey(userId)) {
            System.out.println(" 给用户id为：{"+userId+"}的所有终端发送消息：{"+message+"}");
            for (WebSocketChat WS : userSocket.get(userId)) {
                System.out.println("sessionId为:{"+WS.session.getId()+"}");
                try {
                    WS.session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println(" 给用户id为：{"+userId+"}发送消息失败");
                    return false;
                }
            }
            return true;
        }
        System.out.println("发送错误：当前连接不包含id为：{"+userId+"}的用户");
        return false;
    }

}
