package com.peterjurkovic.travelagency.conversation.event;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.peterjurkovic.travelagency.common.model.Participant;
import com.peterjurkovic.travelagency.common.model.ParticipantType;

public class WebsocketSessionRepository {

    private final Map<String, SessionConversations> sessions = new ConcurrentHashMap<>();

    
    public void joinConversation(String sessionId, String conversationId, Participant participant){
        SessionConversations sessionConversation = sessions.computeIfAbsent(sessionId, s -> new SessionConversations(participant));
        sessionConversation.add(conversationId);
    }
    
    
    public Optional<String> leaveConversation(String sessionId, String conversationId){
        SessionConversations sessionConversation = sessions.get(sessionId);
        if(sessionConversation != null && sessionConversation.remove(conversationId)){
            return Optional.of(sessionConversation.getParticipantId());
        }
        return Optional.empty();
    }
    
    public Set<String> getOnlineParticipantsInConversation(String conversationId){
        return sessions.values().stream()
                    .filter( s -> s.contains(conversationId))
                    .map(SessionConversations::getParticipantId )
                    .collect(Collectors.toSet());
    }
    
    public Optional<SessionConversations> remove(String sessionId){
        return Optional.ofNullable(sessions.remove(sessionId));
    }
    
    public boolean isParticipantOnline(String participantId){
        return sessions.values().stream()
                .filter( s -> s.getParticipantId().equals(participantId))
                .findFirst()
                .isPresent();
    }
    
    public boolean isNotParticipantOnline(String participantId){
        return ! isParticipantOnline(participantId);
    }
    
    
    static class SessionConversations{
        
        private final String participantId;
        private final ParticipantType type;
        private final Set<String> conversations = ConcurrentHashMap.newKeySet();
     
        SessionConversations(Participant participant){
            this.participantId = participant.getId();
            this.type = participant.getType();
        }
        
        public SessionConversations add(String conversation){
            this.conversations.add(conversation);
            return this;
        }
        
        public boolean remove(String conversation){
            return this.conversations.remove(conversation);
        }
        
        public boolean contains(String conversation){
            return conversations.contains(conversation);
        }
        
        public String getParticipantId() {
            return participantId;
        }

        public ParticipantType getType() {
            return type;
        }
        
        public Set<String> getConversations(){
            return Collections.unmodifiableSet(conversations);
        }
        
        public int getNumberOfConversations(){
            return conversations.size();
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((participantId == null) ? 0 : participantId.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            SessionConversations other = (SessionConversations) obj;
            if (participantId == null) {
                if (other.participantId != null)
                    return false;
            } else if (!participantId.equals(other.participantId))
                return false;
            return true;
        }
       
        
    }
   
        
}
