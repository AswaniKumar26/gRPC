package com.gRPC.learning.hello.server;

import com.gRPC.learning.hello.GreetServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.File;
import java.io.IOException;

public class GreetingServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("gRPC Server Started");

        // Plain Text Server
        //Server server = ServerBuilder.forPort(50001).
        //                 addService(new GreetServiceImpl()).
        //                 build();

        // TLSv enabled connection.
        Server server = ServerBuilder.forPort(50001).
                addService(new GreetServiceImpl()).
                useTransportSecurity(new File("ssl/server.crt"),
                                     new File("ssl/server.pem")).
                build();

        server.start();

        Runtime.getRuntime().addShutdownHook( new Thread( () -> {
            System.out.println("Received Shutdown Signal");
            server.shutdown();
            System.out.println("Shutdown Successful");
        }));

        server.awaitTermination();
    }
}
