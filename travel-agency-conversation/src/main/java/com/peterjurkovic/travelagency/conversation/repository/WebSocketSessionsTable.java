package com.peterjurkovic.travelagency.conversation.repository;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Nicola Giacchetta
 */
public class WebSocketSessionsTable {

    public final static ConcurrentHashMap<String, WebSocketSession> sessionsTable = new ConcurrentHashMap();

    public static void cleanSession(String sessionId) throws IOException {
        WebSocketSession browserSession = sessionsTable.get(sessionsTable);
        if(browserSession != null){
            browserSession.close(CloseStatus.NO_STATUS_CODE);
            sessionsTable.remove(sessionId);
        }
    }
}
