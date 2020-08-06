package com.example.demo;

import commands.GetClientCommandHandler;

import java.util.stream.Stream;

public class DemoAppMediator extends mediator.Pipelinr{

    public void RegisterHandlers ()  {
        with(() -> Stream.of(new GetClientCommandHandler()));
    }
}
