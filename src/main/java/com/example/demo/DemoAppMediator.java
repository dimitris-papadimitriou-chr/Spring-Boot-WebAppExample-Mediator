package com.example.demo;

import an.awesome.pipelinr.Pipelinr;
import commands.GetClientCommandHandler;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class DemoAppMediator extends Pipelinr {

    public void RegisterHandlers() {
        with(() -> Stream.of(new GetClientCommandHandler()));
    }
}
