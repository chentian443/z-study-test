package org.springevent.test.config;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AnnotationRegisterListener {

	@EventListener({MyApplicationEvent.class})
	@Async
	public void listenMyApplicationEvent(MyApplicationEvent myApplicationEvent){
		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+":ApplicationEvent-" + myApplicationEvent.getSource());
	}
}
