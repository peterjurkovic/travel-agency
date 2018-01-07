package com.peterjurkovic.travelagency.client.voice;

import com.nexmo.client.NexmoClientException;
import com.nexmo.client.voice.CallEvent;
import com.nexmo.client.voice.ModifyCallResponse;
import com.peterjurkovic.travelagency.common.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

/**
 * @author Nicola Giacchetta
 */
@CrossOrigin
@Controller
public class VoiceController {

    public static final String VOICE_URL = "/voice";

    private static final Logger LOGGER = LoggerFactory.getLogger(VoiceController.class);

    private VoiceService voiceService;

    @Autowired
    public VoiceController(VoiceService voiceService){
        this.voiceService = voiceService;
    }

    @PostMapping(value = VOICE_URL + "/calls/agent", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CallEvent callAgent() throws IOException, NexmoClientException {
        LOGGER.info("Calling agent...");
        CallEvent event = this.voiceService.callAgent("442038731824");
        LOGGER.info("...agent called. ID {}", event.getUuid());
        return event;
    }

    @PostMapping(value = VOICE_URL + "/calls/agent/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ModifyCallResponse terminateCall(@PathVariable String uuid) throws IOException, NexmoClientException {
        LOGGER.info("Terminating call uuid={}...", uuid);
        ModifyCallResponse response = this.voiceService.terminateCall(uuid);
        LOGGER.info("...modify call response: {}", response == null ? "TERMINATED" : response.getMessage());
        return response;
    }

    @ResponseBody
    @RequestMapping(value = VOICE_URL + "/calls/events", produces = MediaType.APPLICATION_JSON_VALUE)
    public void handleEvent(@RequestBody Map<String, String> event){
        LOGGER.info("VAPI EVENT: " +  JsonUtils.toJson(event));
    }

}
