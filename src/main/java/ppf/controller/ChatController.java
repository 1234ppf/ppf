package ppf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ppf.service.ConsumerService;
import ppf.service.ProducerService;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/chat")
public class ChatController {
    @Resource(name = "demoQueueDestination")
    private Destination destination;

    @Autowired(required=false)
    ProducerService producerService;

    @Autowired(required=false)
    ConsumerService consumerService;

    @RequestMapping(value="/chatRoom")
    public String chatRoom (HttpServletRequest request){
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
