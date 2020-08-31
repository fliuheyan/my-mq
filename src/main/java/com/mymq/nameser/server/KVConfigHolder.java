package com.mymq.nameser.server;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class KVConfigHolder {
    private final HashMap<String, HashMap<String, String>> configTable =
            new HashMap<>();
}
