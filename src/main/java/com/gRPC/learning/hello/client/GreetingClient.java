package com.gRPC.learning.hello.client;

import com.proto.dummy.DummyServiceGrpc;
import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResponse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;

import javax.net.ssl.SSLException;
import java.io.File;

public class GreetingClient {

    private void doUnaryCall(ManagedChannel channel) {

        //DummyServiceGrpc.DummyServiceBlockingStub syncClient = DummyServiceGrpc.newBlockingStub(channel);
        GreetServiceGrpc.GreetServiceBlockingStub greetClient = GreetServiceGrpc.newBlockingStub(channel);
        //Build  a Greeting.
        Greeting greeting = Greeting.newBuilder().
                                     setFirstName("Aswani").
                                     setLastName("Kumar").
                                     build();
        GreetRequest greetRequest =GreetRequest.newBuilder().
                                                setGreeting(greeting).
                                                build();
        GreetResponse greetResponse = greetClient.greet(greetRequest);
        System.out.println(greetResponse.getResponse());
    }

    public static void main(String[] args) throws SSLException {
        // non-SSL connection
        //ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",50001).
        //           usePlaintext().build();

        // SSL Connection.
        ManagedChannel channel = NettyChannelBuilder.
                                   forAddress("localhost", 50001).
                                   sslContext(GrpcSslContexts.
                                                  forClient().
                                                trustManager(new File("ssl/ca.crt")).
                                               build()).
                                   build();
        GreetingClient gc = new GreetingClient();
        gc.doUnaryCall(channel);
        System.out.println("Client Shutting down");
        channel.shutdown();
    }
}
