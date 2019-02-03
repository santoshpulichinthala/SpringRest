package co.za.eskom.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/abc")
public class HelloRestController {

    private final static Logger logger = LoggerFactory.getLogger(HelloRestController.class);

    @GetMapping("/helloworld")
    public String hello(){
        logger.info("inside hello world");
        return "Hello world";
    }

}