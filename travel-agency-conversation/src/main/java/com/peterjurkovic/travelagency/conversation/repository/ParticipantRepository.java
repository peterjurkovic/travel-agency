package com.peterjurkovic.travelagency.conversation.repository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.peterjurkovic.travelagency.common.model.Participant;
import com.peterjurkovic.travelagency.common.model.ParticipantType;

public class ParticipantRepository {

    private Map<String, Set<UnmodifiableParticipant>> activeSessions = new ConcurrentHashMap<String, Set<UnmodifiableParticipant>>();

    public synchronized void add(String conversationId, UnmodifiableParticipant participantId) {
        if (!activeSessions.containsKey(conversationId)) {
            activeSessions.put(conversationId, new HashSet<>());
        }
        activeSessions.get(conversationId).add(participantId);
    }

    public synchronized Set<UnmodifiableParticipant> getParticipants(String conversationId) {
        if (!activeSessions.containsKey(conversationId)) {
            return Collections.emptySet();
        }
        return activeSessions.get(conversationId);
    }

    public synchronized boolean isActive(String conversationId, String participantId){
        if (!activeSessions.containsKey(conversationId)) {
            return  false;
        }
        return activeSessions.get(conversationId).stream()
                .filter( p -> p.getId().equals( participantId ))
                .count() > 0;
    }
    
    public synchronized Optional<String> remove(String conversationId, String sessionId) {
        if (activeSessions.containsKey(conversationId)) {
            Set<UnmodifiableParticipant> onlineParticipants =  activeSessions.get(conversationId);
            Optional<UnmodifiableParticipant> user = onlineParticipants.stream()
                .filter( p -> p.getSessionId().equals(sessionId))
                .findFirst();
            
            if(user.isPresent()){
                onlineParticipants.remove(user.get());
                if(onlineParticipants.isEmpty()){
                    activeSessions.remove(conversationId);
                }
                return Optional.of(user.get().id);
            }
            
            
        }
        return Optional.empty();
    }
    
    
    public static class UnmodifiableParticipant{
        private final String id;
        private final String sessionId;
        private final ParticipantType type;
        
        public UnmodifiableParticipant(String sessionId) {
            this.id = null;
            this.sessionId = sessionId;
            this.type = null;
        }
        
        public UnmodifiableParticipant(String sessionId, Participant participant) {
            this.id = participant.getId();
            this.sessionId = sessionId;
            this.type = participant.getType();
        }
        public String getId() {
            return id;
        }
        public ParticipantType getType() {
            return type;
        }
         
        public String getSessionId() {
            return sessionId;
        }
        @Override
        public String toString() {
            return "UnmodifiableParticipant [id=" + id + ", type=" + type + "]";
        }
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((sessionId == null) ? 0 : sessionId.hashCode());
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
            UnmodifiableParticipant other = (UnmodifiableParticipant) obj;
            if (sessionId == null) {
                if (other.sessionId != null)
                    return false;
            } else if (!sessionId.equals(other.sessionId))
                return false;
            return true;
        }
        
        
        
        
    }
}
