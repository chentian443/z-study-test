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
public class QueryService {
    private final static Logger LOGGER = LoggerFactory.getLogger(QueryService.class);

    private final EventBus eventBus;

    public QueryService(EventBus eventBus) {
        this.eventBus = eventBus;
        this.eventBus.register(this);
    }

    public void query(String orderNo){
        LOGGER.info("post the orderNo [{}]", orderNo);
        this.eventBus.post(new Request(orderNo));
    }

    @Subscribe
    public void handleResponse(Response response){
        LOGGER.info("{}", response);
    }
}
