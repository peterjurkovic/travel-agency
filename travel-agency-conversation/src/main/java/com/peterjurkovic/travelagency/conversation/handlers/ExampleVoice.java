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
public class ExampleVoice extends BinaryWebSocketHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExampleVoice.class);

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        LOGGER.info("Handling message...");

        WebSocketSession browserSession = BrowserHandler.sessionTable.get("1");

        if(browserSession != null) {
            ByteBuffer echoMessage = message.getPayload();
            //        LOGGER.info(echoMessage.toString());
            browserSession.sendMessage(new BinaryMessage(echoMessage));
        }
        LOGGER.info("...handled!");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        LOGGER.info("Connection {} established...", session.getId());
        BrowserHandler.sessionTable.put("2", session);

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        session.close(CloseStatus.SERVER_ERROR);
        LOGGER.info("...connection {} closed.", session.getId());
    }

}
