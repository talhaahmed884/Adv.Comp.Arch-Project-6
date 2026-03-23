package com.cpp.project6.symbolTable;

public interface SymbolTable {
    void addEntry(String symbol, int address);

    boolean contains(String symbol);

    int getAddress(String symbol);
}
