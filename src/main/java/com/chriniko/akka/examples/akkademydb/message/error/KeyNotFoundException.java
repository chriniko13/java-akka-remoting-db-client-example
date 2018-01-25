package com.chriniko.akka.examples.akkademydb.message.error;

import java.io.Serializable;

public class KeyNotFoundException extends Exception implements Serializable {

    private final String key;

    public KeyNotFoundException(String key) {
        this.key = key;
    }
}
