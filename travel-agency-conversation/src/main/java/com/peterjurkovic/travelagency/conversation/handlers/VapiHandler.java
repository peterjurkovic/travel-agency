package com.peterjurkovic.travelagency.conversation.handlers;

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

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        WebSocketSession browserSession = BrowserHandler.sessionTable.get("1");
        if(browserSession != null) {
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
        BrowserHandler.sessionTable.put("2", session);

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        session.close(CloseStatus.SERVER_ERROR);
        LOGGER.info("...VapiHandler connection {} closed.", session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        WebSocketSession phoneSession = BrowserHandler.sessionTable.get("2");
        phoneSession.close(CloseStatus.NO_STATUS_CODE);
        BrowserHandler.sessionTable.remove("2");
    }
}
