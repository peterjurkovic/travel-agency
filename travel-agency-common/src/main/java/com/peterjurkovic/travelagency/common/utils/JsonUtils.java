package com.peterjurkovic.travelagency.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public abstract class JsonUtils {

  private final static Logger Log = LoggerFactory.getLogger(JsonUtils.class);
    
    private JsonUtils(){}
    
    public final static ObjectMapper OBJECT_MAPPER =  new ObjectMapper()
            .configure(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS, false)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .configure(MapperFeature.USE_ANNOTATIONS, true);
    
    
    
    public static String toJson(Object object){
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            Log.error("[toJson] can not convert: " + 
                        (object != null ? object.getClass().getName() : "null") + " to JSON.", e);
        }
        return "{}";
    }
    
    public static <T> T fromJson(String json, Class<T> clazz){
        try {
            return (T) OBJECT_MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            Log.error("[fromJson] failed map ["+json+"] to:" + clazz, e);
        }
        return null;
    }
}
