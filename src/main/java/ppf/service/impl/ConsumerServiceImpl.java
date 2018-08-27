package ppf.service.impl;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import ppf.service.ConsumerService;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    /*//消息目的地
    @Resource(name = "demoQueueDestination")
    Destination destination1;
*/
    @Resource(name="jmsTemplate")
    JmsTemplate jmsTemplate;

    public TextMessage receive(Destination destination){
        Message message = jmsTemplate.receive(destination);
        System.out.println(message);
        TextMessage textMessage=(TextMessage) message;
        try{
            System.out.println("从队列" + destination+ "收到了消息：\t"
                    + textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return textMessage;
    }
}
