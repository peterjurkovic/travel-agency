package com.peterjurkovic.travelagency.conversation.utils;

public class AwaitUtils {

    public static void await(long period){
        try {
            Thread.sleep(50L);
        } catch (InterruptedException e) {
            
        }
    }
}
