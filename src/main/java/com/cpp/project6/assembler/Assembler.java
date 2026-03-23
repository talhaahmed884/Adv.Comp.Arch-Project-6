package com.cpp.project6.assembler;

import com.cpp.project6.code.Code;
import com.cpp.project6.code.CodeImpl;
import com.cpp.project6.parser.CommandType;
import com.cpp.project6.parser.Parser;
import com.cpp.project6.parser.ParserImpl;
import com.cpp.project6.symbolTable.SymbolTable;
import com.cpp.project6.symbolTable.SymbolTableImpl;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Assembler {
    private final Parser parser;
    private final Code code;
    private final SymbolTable symbolTable;
    private final List<String> assembledCode;
    private final String targetFileName;

    public Assembler(String fileName) throws IOException {
        this.parser = new ParserImpl(fileName);
        this.code = new CodeImpl();
        this.symbolTable = new SymbolTableImpl();
        this.assembledCode = new ArrayList<>();

        this.targetFileName = FilenameUtils.getBaseName(fileName) + ".hack";
    }

    public void firstPass() {
        int romAddress = 0;
        while (this.parser.hasMoreCommands()) {
            if (Objects.equals(parser.commandType(), CommandType.L_COMMAND.toString())) {
                symbolTable.addEntry(parser.symbol(), romAddress);
            } else {
                romAddress++;
            }

            parser.advance();
        }

        parser.resetCommandListCounter();
    }

    public void secondPass() {
        int ramAddress = 16;

        while (this.parser.hasMoreCommands()) {
            if (Objects.equals(parser.commandType(), CommandType.A_COMMAND.toString())) {
                if (symbolTable.contains(parser.symbol())) {
                    int address = symbolTable.getAddress(parser.symbol());
                    assembledCode.add(convertToBinary(String.valueOf(address)));
                } else {
                    if (isAlphabetic(parser.symbol())) {
                        symbolTable.addEntry(parser.symbol(), ramAddress);
                        assembledCode.add(convertToBinary(String.valueOf(ramAddress)));

                        ramAddress++;
                    } else if (isNumeric(parser.symbol())) {
                        assembledCode.add(convertToBinary(parser.symbol()));
                    } else {
                        throw new InvalidParameterException("Invalid command type");
                    }
                }
            } else if (Objects.equals(parser.commandType(), CommandType.C_COMMAND.toString())) {
                String binaryCode = code.comp(parser.comp());
                binaryCode += code.dest(parser.dest());
                binaryCode += code.jump(parser.jump());
                binaryCode = "111" + binaryCode;
                assembledCode.add(binaryCode);
            }
            parser.advance();
        }
    }

    public void writeToTargetFile() throws IOException {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.targetFileName, false));
            for (String code : assembledCode) {
                writer.write(code);
                writer.newLine();
            }
            writer.close();
        } catch (Exception e) {
            throw new IOException("Unable to write to file: " + targetFileName, e);
        }
    }

    private String convertToBinary(String value) {
        StringBuilder binaryValue = new StringBuilder(Integer.toString(Integer.parseInt(value), 2));
        while (binaryValue.length() < 16) {
            binaryValue.insert(0, "0");
        }

        return binaryValue.toString();
    }

    private boolean isAlphabetic(String str) {
        return str != null && !str.isEmpty() && str.chars().allMatch(Character::isLetter);
    }

    private boolean isNumeric(String str) {
        return str != null && !str.isEmpty() && str.chars().allMatch(Character::isDigit);
    }
}
