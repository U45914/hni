package org.hni.events.service;

import org.hni.events.service.dao.SessionStateDAO;
import org.hni.events.service.om.Event;
import org.hni.events.service.om.EventName;
import org.hni.events.service.om.SessionState;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class EventServiceFactoryUnitTest {

    private static final String SESSION_ID = "123";
    private static final String PHONE_NUMBER = "8188461238";
    private static final String REUTRN_MESSAGE = "returnmessage";

    @InjectMocks
    private EventServiceFactory factory;

    @Mock
    private SessionStateDAO sessionStateDAO;

    @Mock
    private RegisterService registerService;

    private Event event;
    private SessionState state = null;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        factory.init();
        event = new Event(SESSION_ID, PHONE_NUMBER, "message");
        when(sessionStateDAO.insert(any(SessionState.class))).thenReturn(new SessionState());
        when(registerService.handleEvent(eq(event))).thenReturn(REUTRN_MESSAGE);
    }

    @Test
    public void testInvalidEventAndNoActiveWorkFlow() {
        when(sessionStateDAO.getByPhoneNumber(eq(PHONE_NUMBER))).thenReturn(state);
        Assert.assertEquals("Unknown keyword " + event.getTextMessage(), factory.handleEvent(event));
        verify(sessionStateDAO, times(0)).insert(any(SessionState.class));
    }

    @Test
    public void testInvalidEventWithActiveWorkFlow() {
        state = new SessionState(EventName.REGISTER, SESSION_ID, PHONE_NUMBER);
        when(sessionStateDAO.getByPhoneNumber(eq(PHONE_NUMBER))).thenReturn(state);
        Assert.assertEquals(REUTRN_MESSAGE, factory.handleEvent(event));
        verify(sessionStateDAO, times(0)).insert(any(SessionState.class));
    }

    @Test
    public void testStartRegisterWorkFlow() {
        when(sessionStateDAO.getByPhoneNumber(eq(PHONE_NUMBER))).thenReturn(state);
        event.setTextMessage("SIGNUP");
        Assert.assertEquals(REUTRN_MESSAGE, factory.handleEvent(event));
        verify(sessionStateDAO, never()).delete(eq(SESSION_ID));
        verify(sessionStateDAO, times(1)).insert(any(SessionState.class));
    }

    @Test
    public void testInterruptExistingWorkFlow() {
        state = new SessionState(EventName.MEAL, SESSION_ID, PHONE_NUMBER);
        when(sessionStateDAO.getByPhoneNumber(eq(PHONE_NUMBER))).thenReturn(state);
        event.setTextMessage("SIGNUP");
        Assert.assertEquals(REUTRN_MESSAGE, factory.handleEvent(event));
        verify(sessionStateDAO, times(1)).delete(eq(PHONE_NUMBER));
        verify(sessionStateDAO, times(1)).insert(any(SessionState.class));
    }

    @Test
    public void testContinueRegisterWorkFlow() {
        state = new SessionState(EventName.REGISTER, SESSION_ID, PHONE_NUMBER);
        when(sessionStateDAO.getByPhoneNumber(eq(PHONE_NUMBER))).thenReturn(state);
        Assert.assertEquals(REUTRN_MESSAGE, factory.handleEvent(event));
        verify(sessionStateDAO, never()).insert(any(SessionState.class));
    }
}
