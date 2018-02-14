package com.peterjurkovic.travelagency.client.voice;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.voice.Call;
import com.nexmo.client.voice.CallEvent;
import com.nexmo.client.voice.ModifyCallAction;
import com.nexmo.client.voice.ModifyCallResponse;
import com.peterjurkovic.travelagency.client.config.VoiceConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author Nicola Giacchetta
 */
@Service
public class VoiceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoiceService.class);

    private VoiceConfiguration configuration;

    private final NexmoClient nexmoClient;

    @Autowired
    public VoiceService(NexmoClient nexmoClient, VoiceConfiguration configuration){
        this.nexmoClient = nexmoClient;
        this.configuration = configuration;
    }

    public CallEvent callAgent(String from) throws IOException, NexmoClientException {
        return createCall(configuration.getAgentNumber(), from);
    }

    public CallEvent createCall(String to, String from) throws IOException, NexmoClientException {
        LOGGER.info("Creating call [to={}, from={}]...", to, from);
        Call call = new Call(to, from, configuration.getAnswerUrl());
        CallEvent event = this.nexmoClient.getVoiceClient().createCall(call);
        LOGGER.debug("...call [to={}, from={}] created with uuid={}", to, from, event.getUuid());
        return event;
    }

    public ModifyCallResponse terminateCall(String uuid) throws IOException, NexmoClientException {
        ModifyCallResponse response = null;
        LOGGER.debug("Trying to terminate call uuid={}", uuid);
        try {
            response = this.nexmoClient.getVoiceClient().modifyCall(uuid, ModifyCallAction.HANGUP);
        } catch(NullPointerException e){
            // SKIP NPE returned by the NexmoClient
            LOGGER.warn("NullPointerException swallowed - ", e);
        }
        LOGGER.debug("...modify call response: {}", response==null ? "TERMINATED" : response.getMessage());
        return response;
    }
}
