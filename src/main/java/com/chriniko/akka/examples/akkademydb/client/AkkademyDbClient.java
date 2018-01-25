package com.chriniko.akka.examples.akkademydb.client;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import com.chriniko.akka.examples.akkademydb.message.GetRequest;
import com.chriniko.akka.examples.akkademydb.message.SetRequest;
import scala.compat.java8.FutureConverters;
import scala.concurrent.Future;

import java.util.Arrays;
import java.util.concurrent.CompletionStage;

public class AkkademyDbClient {

    public static void main(String[] args) {

        String remoteAddress = "127.0.0.1:2557";

        ActorSystem system = ActorSystem.create("LocalSystem");
        ActorSelection remoteDb = system.actorSelection("akka.tcp://akkademy@" + remoteAddress + "/user/akkademy-db");

        set(remoteDb, "chriniko", "software engineer")
                .thenCombine(get(remoteDb, "chriniko"), (_r1, _r2) -> Arrays.asList(_r1, _r2))
                .whenComplete((_r, _e) -> System.out.println("result = " + _r + " --- error = " + _e));

    }

    private static CompletionStage<Object> set(ActorSelection actorSelection, String key, Object value) {
        Future<Object> resultInScala = Patterns.ask(actorSelection, new SetRequest(key, value), 5000);
        return FutureConverters.toJava(resultInScala);
    }

    private static CompletionStage<Object> get(ActorSelection actorSelection, String key) {
        Future<Object> resultInScala = Patterns.ask(actorSelection, new GetRequest(key), 5000);
        return FutureConverters.toJava(resultInScala);
    }

}
