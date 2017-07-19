package edu.springboot.configuration;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


@Component
public class SpringLoader implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = Logger.getLogger(SpringLoader.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
	logger.info("started successfully-------------");
       
    }
}
