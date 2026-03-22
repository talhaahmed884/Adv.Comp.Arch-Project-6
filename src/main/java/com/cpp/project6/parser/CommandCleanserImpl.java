package com.cpp.project6.parser;

public class CommandCleanserImpl implements CommandCleanser {
    public String CleanseCommand(String command) {
        command = this.removeComments(command);
        return this.trimCommand(command);
    }

    private String removeComments(String command) {
        command = this.trimCommand(command);

        if (this.hasComments(command)) {
            if (command.charAt(0) == '/') {
                return "";
            }

            StringBuilder commandBuilder = new StringBuilder();
            for (int i = 1; i < command.length(); i++) {
                if (command.charAt(i) != '/') {
                    commandBuilder.append(command.charAt(i));
                }
            }
            return commandBuilder.toString();
        }

        return command;
    }

    private boolean hasComments(String command) {
        return command.contains("//");
    }

    private String trimCommand(String command) {
        return command.trim();
    }
}
