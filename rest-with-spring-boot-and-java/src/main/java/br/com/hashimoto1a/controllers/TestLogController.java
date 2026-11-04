package br.com.hashimoto1a.controllers;

import br.com.hashimoto1a.services.PersonServices;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestLogController {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(TestLogController.class.getName());

    @GetMapping("/test")
    public String testLog(){
        logger.debug("this is an DEBUG log");
        logger.debug("this is an DEBUG log 2");
        logger.info("this is an INFO log");
        logger.warn("this is an WARN log");
        logger.error("this is an ERROR log");
        return "Logs generated successfully";
    }
}
