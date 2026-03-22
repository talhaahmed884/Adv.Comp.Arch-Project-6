package com.cpp.project6.parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserImpl implements Parser {
    private final String fileName;
    private final CommandCleanser commandCleanser;
    private int commandsListCounter;
    private List<String> commandsList;

    public ParserImpl(String fileName) throws IOException {
        this.fileName = fileName;
        this.commandsListCounter = 0;
        this.commandCleanser = new CommandCleanserImpl();

        this.readFile();
    }

    @Override
    public boolean hasMoreCommands() {
        return this.commandsListCounter < this.commandsList.size();
    }

    @Override
    public void advance() {
        this.commandsListCounter++;
    }

    public String getCurrentCommand() {
        return this.commandsList.get(this.commandsListCounter);
    }

    @Override
    public String commandType() {
        String currentCommand = this.getCurrentCommand();

        if (currentCommand.charAt(0) == '@') {
            return CommandType.A_COMMAND.toString();
        } else if (currentCommand.charAt(0) == '(') {
            return CommandType.L_COMMAND.toString();
        } else if (currentCommand.contains("=") || currentCommand.contains(";")) {
            return CommandType.C_COMMAND.toString();
        } else {
            throw new InvalidParameterException("Invalid command type");
        }
    }

    @Override
    public String symbol() {
        String regex = "";

        if (this.commandType().equals(CommandType.L_COMMAND.toString())) {
            regex = "\\((.*?)\\)";
        } else if (this.commandType().equals(CommandType.A_COMMAND.toString())) {
            regex = "@(.*)";
        } else {
            throw new InvalidParameterException("Invalid command type");
        }

        return this.parseComponentWithRegex(regex);
    }

    @Override
    public String dest() {
        if (!this.commandType().equals(CommandType.C_COMMAND.toString())) {
            throw new InvalidParameterException("Invalid command type");
        }

        if (!this.getCurrentCommand().contains("=")) {
            return null;
        }
        return this.parseComponentWithRegex("^([^=]+)");
    }

    @Override
    public String comp() {
        if (!this.commandType().equals(CommandType.C_COMMAND.toString())) {
            throw new InvalidParameterException("Invalid command type");
        }

        if (!this.getCurrentCommand().contains("=") & !this.getCurrentCommand().contains(";")) {
            return null;
        }
        return this.parseComponentWithRegex("^(?:[^=]*=)?([^;]+)");
    }

    @Override
    public String jump() {
        if (!this.commandType().equals(CommandType.C_COMMAND.toString())) {
            throw new InvalidParameterException("Invalid command type");
        }

        if (!this.getCurrentCommand().contains(";")) {
            return null;
        }
        return this.parseComponentWithRegex(";(.+)");
    }

    private void readFile() throws IOException {
        File file = new File(fileName);
        if (!file.exists() || !file.isFile() || !file.canRead()) {
            throw new InvalidParameterException("Unable to read file: " + fileName);
        }

        try {
            commandsList = Files.readAllLines(file.toPath());
        } catch (Exception e) {
            throw new IOException("Unable to read file: " + fileName, e);
        }

        this.cleanseCommandsList();
    }

    private void cleanseCommandsList() {
        List<String> cleansedCommandsList = new ArrayList<>();

        for (String command : commandsList) {
            String currentCommand = this.commandCleanser.CleanseCommand(command);
            if (!currentCommand.isEmpty()) {
                cleansedCommandsList.add(currentCommand);
            }
        }

        this.commandsList = cleansedCommandsList;
    }

    private String parseComponentWithRegex(String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(this.getCurrentCommand());

        if (matcher.find()) {
            return matcher.group(1);
        }

        throw new InvalidParameterException("Invalid command type");
    }
}
