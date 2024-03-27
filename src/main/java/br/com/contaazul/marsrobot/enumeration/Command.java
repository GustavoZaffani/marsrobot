package br.com.contaazul.marsrobot.enumeration;

public enum Command {

    TURN_LEFT("L"),
    TURN_RIGHT("R"),
    MOVE_FORWARD("M");

    private String value;

    Command(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Command fromValue(String value) {
        for (Command command : Command.values()) {
            if (command.getValue().equals(value)) {
                return command;
            }
        }
        throw new IllegalArgumentException("Invalid command value: " + value);
    }
}
