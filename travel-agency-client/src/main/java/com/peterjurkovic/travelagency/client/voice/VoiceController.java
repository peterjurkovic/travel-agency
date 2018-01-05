package com.peterjurkovic.travelagency.client.voice;

import com.nexmo.client.NexmoClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * @author Nicola Giacchetta
 */
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
    public void callAgent() throws IOException, NexmoClientException {
        LOGGER.info("Calling agent...");
        this.voiceService.callAgent("447397921621");
        LOGGER.info("...agent called.");
    }
}
