package ppf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ppf.bean.User;
import ppf.service.IUserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/sys")
public class LoginController {
    @Resource
    private IUserService userService;

    @RequestMapping(value = "/login")
    public String login(HttpServletRequest request, User user, HttpSession session){
        System.out.println(user);
        User returnUser = userService.selectUserByName(user.getUsername());
        if(returnUser==null||returnUser.equals("")){
            request.setAttribute("error","1");
            request.setAttribute("message","该用户不存在，请先注册！");
            return "login";
        }
        String password=returnUser.getPassword();
        System.out.println(returnUser.getPassword());
        if (password==null||("").equals(password)){
            request.setAttribute("error","2");
            request.setAttribute("message","密码不存在！");
            return "login";
        }else if (password==user.getPassword()||password.equals(user.getPassword())){
            request.setAttribute("success","0");
            session.setAttribute("user",returnUser);
            request.setAttribute("message","登录成功，欢迎来到首页！");
            return "index";
        }else{
            request.setAttribute("error","3");
            request.setAttribute("message","密码错误！");
            return "login";
        }
    }
}
