package com.peterjurkovic.travelagency.admin.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.peterjurkovic.travelagency.admin.user.AdminUserUtils;
import com.peterjurkovic.travelagency.common.model.AdminUser;
import com.peterjurkovic.travelagency.common.model.Conversation;
import com.peterjurkovic.travelagency.common.model.Participant;
import com.peterjurkovic.travelagency.common.repository.ConversationRepository;
import com.peterjurkovic.travelagency.common.utils.JsonUtils;

@Controller
public class AdminConversationsController {
    
    @Autowired
    private ConversationRepository repository;

    @Autowired
    private AdminUserUtils userUtils;
    
    @GetMapping("/conversations")
    public String showPage(ModelMap model){
        PageRequest req = PageRequest.of(0, 30, Direction.DESC, "date");
        model.put("conversations", repository.findAll(req));
        return "conversations";
    }
    
    @GetMapping("/conversations/{id}")
    public String showConversation(ModelMap model, @PathVariable String id){
        Optional<Conversation> conversation = repository.findById(id);
        if(conversation.isPresent()){
            model.put("conversation", conversation.get());
            model.put("showJoinButton", showJoinButton(conversation.get()));
            model.put("participant",toEncoededJson(userUtils.getLoggedUserOrThrow().toParticipant()));
        }
        return "conversation";
    }
    
    private boolean showJoinButton(Conversation conversation){
        AdminUser user = userUtils.getLoggedUserOrThrow();
        
        return conversation.getParticipants().stream()
            .filter( p -> p.getId().equals( user.getId()) )
            .count() == 0;        
        
    }
    
    private static String toEncoededJson(Participant participant){
        return JsonUtils.toJson(participant) ;
    }
    
    
}
