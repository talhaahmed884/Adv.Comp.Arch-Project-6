package com.cpp.project6.assembler;

import com.cpp.project6.code.Code;
import com.cpp.project6.code.CodeImpl;
import com.cpp.project6.parser.Parser;
import com.cpp.project6.parser.ParserImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Assembler {
    private Parser parser;
    private Code code;
    private List<String> assembledCode;
    private String targetFileName;

    public Assembler(String fileName) throws IOException {
        this.parser = new ParserImpl(fileName);
        this.code = new CodeImpl();
        this.assembledCode = new ArrayList<>();
    }

    public void firstPass() {

    }
}
