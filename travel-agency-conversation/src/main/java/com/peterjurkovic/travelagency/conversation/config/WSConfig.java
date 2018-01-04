//package com.peterjurkovic.travelagency.conversation.config;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.WebSocketMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//
//@Configuration
//@EnableWebSocket
//public class WSConfig implements WebSocketConfigurer{
//
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(myHandler(), "/myHandler").setAllowedOrigins("*");
//    }
//
//    @Bean
//    public WebSocketHandler myHandler() {
//        return new MyHandler();
//    }
//    
//    static class MyHandler extends TextWebSocketHandler{
//
//        @Override
//        public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
//            System.out.println("Hiii............");
//        }
//    }
//}
