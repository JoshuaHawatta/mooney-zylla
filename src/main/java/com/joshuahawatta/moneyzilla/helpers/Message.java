package com.joshuahawatta.moneyzilla.helpers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String content;

    public static Map<String, String> asJson(String content) {
        var jsonMessage = new HashMap<String, String>();

        jsonMessage.put("message", content);

        return jsonMessage;
    }

    public static void putOnJson(String content, Map<String, Object> json) {
        json.put("message", content);
    }
}
