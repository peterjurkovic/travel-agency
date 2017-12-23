package com.peterjurkovic.travelagency.client.voice;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.voice.Call;
import com.nexmo.client.voice.CallEvent;
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

    private final NexmoClient nexmoClient;

    @Autowired
    public VoiceService(NexmoClient nexmoClient){
        this.nexmoClient = nexmoClient;
    }

    public CallEvent createCall(String to, String from) throws IOException, NexmoClientException {
        LOGGER.debug("Creating createCall [to={}, from={}]...", to, from);
        // TODO change answerUrl
        Call call = new Call(to, from, "https://nexmo-community.github.io/ncco-examples/first_call_talk.json");
        CallEvent event = this.nexmoClient.getVoiceClient().createCall(call);
        LOGGER.debug("...createCall [to={}, from={}] created with uuid={}", to, from, event.getUuid());
        return event;
    }
}
