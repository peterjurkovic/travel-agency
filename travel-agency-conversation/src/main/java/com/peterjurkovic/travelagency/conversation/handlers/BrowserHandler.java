package com.peterjurkovic.travelagency.conversation.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Nicola Giacchetta
 */
public class BrowserHandler extends BinaryWebSocketHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(VapiHandler.class);

    public final static ConcurrentHashMap<String, WebSocketSession> sessionTable = new ConcurrentHashMap();

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        WebSocketSession phoneSession = BrowserHandler.sessionTable.get("2");

        if(phoneSession != null) {
            ByteBuffer echoMessage = message.getPayload();
            phoneSession.sendMessage(new BinaryMessage(echoMessage));
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        LOGGER.info("BrowserHandler Text Message skipped {}", message.getPayload());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        LOGGER.info("BrowserHandler connection {} established...", session.getId());
        sessionTable.put("1", session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        session.close(CloseStatus.SERVER_ERROR);
        LOGGER.info("...BrowserHandler connection {} closed.", session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        WebSocketSession browserSession = sessionTable.get("1");
        browserSession.close(CloseStatus.NO_STATUS_CODE);
        sessionTable.remove("1");
    }
}
