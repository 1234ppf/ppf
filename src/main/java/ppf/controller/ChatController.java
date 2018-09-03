package ppf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ppf.bean.User;
import ppf.service.ConsumerService;
import ppf.service.IUserService;
import ppf.service.ProducerService;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.TextMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {
    @Resource(name = "demoQueueDestination")
    private Destination destination;

    @Autowired(required=false)
    ProducerService producerService;

    @Autowired(required=false)
    ConsumerService consumerService;

    @Resource
    IUserService userService;


    @RequestMapping(value="/chatRoom")
    public String chatRoom (HttpServletRequest request){
        User loginUser=(User)request.getSession().getAttribute("user");
        List<User> elseUser=userService.selectElse(loginUser);
        request.setAttribute("elseUser",elseUser);
        return "chat";
    }


    @RequestMapping(value="/sendMessage",method=RequestMethod.GET)
    public @ResponseBody void send(String msg) {
        msg="helloActiveMq";
        producerService.sendMessage(destination,msg);
        /*return new ModelAndView("redirect:/my/receiveMessage");*/
    }

    @RequestMapping(value="/receiveMessage",method=RequestMethod.GET)
    public @ResponseBody void receive()  {
        TextMessage x=consumerService.receive(destination);
    }



}
