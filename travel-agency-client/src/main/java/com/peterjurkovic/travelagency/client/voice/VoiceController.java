package com.peterjurkovic.travelagency.client.voice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Nicola Giacchetta
 */
@Controller
public class VoiceController {

    public static final String VOICE_URL = "/voice";

    private static final Logger LOGGER = LoggerFactory.getLogger(VoiceController.class);

    @PostMapping(VOICE_URL + "/calls")
    public void createCall(){

    }
}
