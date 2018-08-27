package ppf.service;

import org.springframework.stereotype.Service;

import javax.jms.Destination;
import javax.jms.TextMessage;

@Service
public interface ConsumerService {

    TextMessage receive(Destination destination);
}
