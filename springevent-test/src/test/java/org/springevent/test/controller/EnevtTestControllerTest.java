package org.springevent.test.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springevent.test.EventApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EnevtTestControllerTest {

	@Autowired
    private TestRestTemplate restTemplate;
	
    @Test
    public void getName() {
        String name = restTemplate.getForObject("/event", String.class);
        System.out.println(name);
        Assert.assertEquals("eventNum must in (0,35)", name);
    }

}
