package com.peterjurkovic.travelagency.conversation.handlers;

import com.peterjurkovic.travelagency.conversation.repository.WebSocketSessionsTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.nio.ByteBuffer;

/**
 * @author Nicola Giacchetta
 */
public class BrowserHandler extends BinaryWebSocketHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(VapiHandler.class);

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        WebSocketSession phoneSession = WebSocketSessionsTable.sessionsTable.get("2");

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
        WebSocketSessionsTable.sessionsTable.put("1", session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        session.close(CloseStatus.SERVER_ERROR);
        LOGGER.info("...BrowserHandler connection {} closed.", session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        WebSocketSessionsTable.cleanSession("1");
    }
}
