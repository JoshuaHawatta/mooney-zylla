package com.joshuahawatta.moneyzilla.helpers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Collections;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String content;

    public static Map<String, String> asJson(String content) {
        return Collections.singletonMap("messagge", content);
    }

    public static void putOnJson(String content, Map<String, Object> json) {
        json.put("message", content);
    }
}
