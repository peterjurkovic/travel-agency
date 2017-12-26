package com.peterjurkovic.travelagency.conversation;

import static codes.monkey.reactivechat.Event.Type.USER_LEFT;

import java.io.IOException;
import java.util.Optional;

import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;

import com.peterjurkovic.travelagency.common.utils.JsonUtils;

import codes.monkey.reactivechat.Event;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.UnicastProcessor;

public class ConversationWebSocketHandler implements WebSocketHandler{

    private UnicastProcessor<ConversationEvent> eventPublisher;
    private Flux<String> outputEvents;
    
    public ConversationWebSocketHandler(UnicastProcessor<ConversationEvent> eventPublisher, Flux<ConversationEvent> events) {
        this.eventPublisher = eventPublisher;
        this.outputEvents = Flux.from(events).map(JsonUtils::toJson);
    }
    
    @Override
    public Mono<Void> handle(WebSocketSession session) {
        session.receive()
            .map(WebSocketMessage::getPayloadAsText)
            .map(ConversationWebSocketHandler::toEvent)
            .subscribe(null, null);
        return null;
    }

    
    private static ConversationEvent toEvent(String json) {
        return JsonUtils.fromJson(json, ConversationEvent.class);
    }
    

    private static class WebSocketMessageSubscriber {
        private UnicastProcessor<ConversationEvent> eventPublisher;
        private Optional<ConversationEvent> lastReceivedEvent = Optional.empty();

        public WebSocketMessageSubscriber(UnicastProcessor<ConversationEvent> eventPublisher) {
            this.eventPublisher = eventPublisher;
        }

        public void onNext(ConversationEvent event) {
            lastReceivedEvent = Optional.of(event);
            eventPublisher.onNext(event);
        }

        public void onError(Throwable error) {
            //TODO log error
            error.printStackTrace();
        }

        public void onComplete() {

            lastReceivedEvent.ifPresent(event -> eventPublisher.onNext(
                    ConversationEvent.type(ConversationEvent.Type.USER_LEFT)

                            .build()));
        }

    }
}
