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
public class VapiHandler extends BinaryWebSocketHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(VapiHandler.class);
    private String vapiSessionId;

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        WebSocketSession browserSession = WebSocketSessionsTable.sessionsTable.get("1");
        if(browserSession != null && browserSession.isOpen()) {
            ByteBuffer echoMessage = message.getPayload();
            browserSession.sendMessage(new BinaryMessage(echoMessage));
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        LOGGER.info("VapiHandler Text Message skipped {}", message.getPayload());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        LOGGER.info("VapiHandler Connection {} established...", session.getId());
        WebSocketSessionsTable.sessionsTable.put("2", session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        WebSocketSessionsTable.cleanSession("1");
        session.close(CloseStatus.SERVER_ERROR);
        LOGGER.info("...VapiHandler connection {} closed for transportError.", session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        WebSocketSessionsTable.cleanSession("1");
        session.close(CloseStatus.NORMAL);
        LOGGER.info("...VapiHandler connection {} closed.", session.getId());
    }
}
