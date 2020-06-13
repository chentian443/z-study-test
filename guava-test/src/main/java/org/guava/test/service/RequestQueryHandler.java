package org.guava.test.service;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.guava.test.events.Request;
import org.guava.test.events.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:
 * @Author: Dong.Wang
 * @Date: 2018-05-19 18:07
 **/
public class RequestQueryHandler {
    private final static Logger LOGGER = LoggerFactory.getLogger(RequestQueryHandler.class);

    private final EventBus eventBus;

    public RequestQueryHandler(EventBus eventBus) {
        this.eventBus = eventBus;
        this.eventBus.register(this);
    }

    @Subscribe
    public void doQuery(Request request){
        LOGGER.info("start query the orderNo [{}]", request.toString());
        Response response = new Response();
        this.eventBus.post(response);
    }
}
