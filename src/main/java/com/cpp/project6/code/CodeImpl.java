package com.cpp.project6.code;

import java.security.InvalidParameterException;

public class CodeImpl implements Code {
    @Override
    public String dest(String mnemonic) {
        return switch (mnemonic) {
            case null -> "000";
            case "M" -> "001";
            case "D" -> "010";
            case "MD" -> "011";
            case "A" -> "100";
            case "AM" -> "101";
            case "AD" -> "110";
            case "AMD" -> "111";
            default -> throw new InvalidParameterException("Invalid command type");
        };
    }

    @Override
    public String comp(String mnemonic) {
        return switch (mnemonic) {
            case "0" -> "101010";
            case "1" -> "111111";
            case "-1" -> "111010";
            case "D" -> "001100";
            case "A", "M" -> "110000";
            case "!D" -> "001101";
            case "!A", "!M" -> "110001";
            case "-D" -> "001111";
            case "-A", "-M" -> "110011";
            case "D+1" -> "011111";
            case "A+1", "M+1" -> "110111";
            case "D-1" -> "001110";
            case "A-1", "M-1" -> "110010";
            case "D+A", "D+M" -> "000010";
            case "D-A", "D-M" -> "010011";
            case "A-D", "M-D" -> "000111";
            case "D&A", "D&M" -> "000000";
            case "D|A", "D|M" -> "010101";
            default -> throw new InvalidParameterException("Invalid command type");
        };
    }

    @Override
    public String jump(String mnemonic) {
        return switch (mnemonic) {
            case null -> "000";
            case "JGT" -> "001";
            case "JEQ" -> "010";
            case "JGE" -> "011";
            case "JLT" -> "100";
            case "JNE" -> "101";
            case "JLE" -> "110";
            case "JMP" -> "111";
            default -> throw new InvalidParameterException("Invalid command type");
        };
    }
}
