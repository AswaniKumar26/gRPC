package com.gRPC.learning.hello;

import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResponse;
import com.proto.greet.GreetServiceGrpc;

import com.proto.greet.Greeting;
import io.grpc.stub.StreamObserver;

public class GreetServiceImpl extends GreetServiceGrpc.GreetServiceImplBase {
    @Override
    public void greet(GreetRequest request,
                       StreamObserver<GreetResponse> responseObserver) {
        //super.greet(request, responseObserver);
        Greeting greeting = request.getGreeting();
        String firstname = greeting.getFirstName();
        String lastName = greeting.getLastName();
        String result = "Hello " + firstname +" " + lastName;
        GreetResponse response = GreetResponse.newBuilder().setResponse(result).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
