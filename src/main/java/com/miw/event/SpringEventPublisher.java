package com.miw.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringEventPublisher implements EventPublisher {

	private final ApplicationEventPublisher applicationEventPublisher;

	public SpringEventPublisher(ApplicationEventPublisher applicationEventPublisher) {

		this.applicationEventPublisher = applicationEventPublisher;
	}

	@Override
	public void publish(Event event) {

		applicationEventPublisher.publishEvent(event);
	}
}