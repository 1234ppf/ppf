package ppf.service;


import org.springframework.stereotype.Service;

import javax.jms.Destination;

@Service
public interface ProducerService {

    void sendMessage(Destination destination, final String msg);

    void sendMessage(final String msg);
}
