package org.kafka.demo.controller;

import org.kafka.demo.service.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendController {

    @Autowired
    private Producer producer;

    @GetMapping(value = "/send")
    public String send() {
        producer.send();
        return "{\"code\":\"send\"}";
    }
}
