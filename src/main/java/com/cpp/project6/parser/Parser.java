package com.cpp.project6.parser;

public interface Parser {
    boolean hasMoreCommands();

    void advance();

    String commandType();

    String symbol();

    String dest();

    String comp();

    String jump();
}
