package ppf.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ppf.bean.User;
import ppf.service.IUserService;
import ppf.util.GetIpAddress;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @RequestMapping("/showUser.do")
    public void selectUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer userId = Integer.parseInt(request.getParameter("id"));
        User user = userService.selectUser(userId);
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(user));
        response.getWriter().close();
    }

    @RequestMapping("/userList")
    public String userLit(HttpServletRequest request){
        List<User> userList=userService.selectAll();
        User user = userService.selectUser(1);
        request.setAttribute("user",user);
        request.setAttribute("userList",userList);
        return "userList";
    }

    @RequestMapping(value = "/imageFile",method = RequestMethod.POST)
    public String imageFile(HttpServletRequest request){
        String a=request.getParameter("file");
        System.out.println(a);
        return "userList";
    }

    @RequestMapping(value = "/userMessage")
    public String userMessage(HttpServletRequest request){
        return "aaa";
    }

    @RequestMapping(value = "/connect",method = RequestMethod.POST)
    public String connectUs(HttpServletRequest request, @ModelAttribute("user") User user){
        user.setRegtime(new Date());
        /*返回发出请求的客户机的IP地址。
        String ip1=request.getRemoteAddr();
        返回WEB服务器的IP地址。
        String ip2=request.getLocalAddr();
        返回发出请求的客户机的完整主机名。
        String name1=request.getRemoteHost();
        返回客户机所使用的网络端口号。
        String name2=request.getLocalName();
        解决反向代理ip返回问题*/
        String clientIp = GetIpAddress.getIp(request);
        user.setRegip(clientIp);
        int result = userService.insert(user);
        if(result>0){
            request.setAttribute("message","发送成功！");
        }
        return "aaa";
    }

}