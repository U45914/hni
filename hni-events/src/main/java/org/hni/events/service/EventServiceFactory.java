package org.hni.events.service;

import org.hni.events.service.dao.SessionStateDAO;
import org.hni.events.service.om.Event;
import org.hni.events.service.om.EventName;
import org.hni.events.service.om.SessionState;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@Component
public class EventServiceFactory {

    @Inject
    private SessionStateDAO sessionStateDAO;

    @Inject
    private RegisterService registerService;

    @Inject
    private OrderingService orderingService;

    private Map<EventName, EventService> eventServiceMap;

    @PostConstruct
    void init() {
        eventServiceMap = new HashMap<>();
        eventServiceMap.put(EventName.REGISTER, registerService);
        eventServiceMap.put(EventName.MEAL, orderingService);
    }

    public String handleEvent(final Event event) {
        final String phoneNumber = event.getPhoneNumber();
        final SessionState state = sessionStateDAO.getByPhoneNumber(phoneNumber);
        EventName eventName = parseKeyWordToEventName(event.getTextMessage());
        if (eventName == null) {
            if (state == null) {
                // not a keyword, nor having a in progress workflow
                return "Unknown keyword " + event.getTextMessage();
            } else {
                eventName = state.getEventName();
            }
        } else {
            if (state != null) {
                // clear previous workflow as a new keyword is received
                sessionStateDAO.delete(phoneNumber);
            }
            sessionStateDAO.insert(new SessionState(eventName, event.getSessionId(), event.getPhoneNumber()));
        }
        // set value to current workflow's value when it is not a keyword value
        return eventServiceMap.get(eventName).handleEvent(event);
    }

    private EventName parseKeyWordToEventName(final String keyWord) {
        switch (keyWord.toUpperCase()) {
            // Suport multiple keywords for enrollment
            case "SIGNUP":
            case "ENROLL":
            case "REGISTER":
                return EventName.REGISTER;
            // Support multiple Keywords for ordering
            case "MEAL":
            case "ORDER":
                return EventName.MEAL;
            case "GIVE":
            case "DONATE":
                return EventName.DONATE;
            default: // No valid keyword entered
                return null;
        }
    }
}
