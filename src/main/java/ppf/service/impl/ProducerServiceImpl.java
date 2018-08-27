package ppf.service.impl;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import ppf.service.ProducerService;

@Service
public class ProducerServiceImpl implements ProducerService {


    //指定消息目的地的发送方法
    @Resource(name="jmsTemplate")
    private JmsTemplate jmsTemplate;
    public void sendMessage(Destination destination, final String msg){
        System.out.println(Thread.currentThread().getName()+" 向队列"+destination.toString()+"发送消息---------------------->"+msg);
        jmsTemplate.send(destination,new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(msg);
            }
        });
    }

    //默认消息目的地的方法
    public void sendMessage(final String msg){
        String destination = jmsTemplate.getDefaultDestination().toString();
        System.out.println(Thread.currentThread().getName()+" 向队列"+destination+"发送消息---------------------->"+msg);
        System.out.println(Thread.currentThread().getName()+" 向队列"+destination+"发送消息---------------------->"+msg);
        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(msg);
            }
        });
    }
}
