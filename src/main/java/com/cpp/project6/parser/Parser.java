package com.cpp.project6.parser;

public interface Parser {
    String getCurrentCommand();

    void resetCommandListCounter();

    boolean hasMoreCommands();

    void advance();

    String commandType();

    String symbol();

    String dest();

    String comp();

    String jump();
}
