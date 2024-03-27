package br.com.contaazul.marsrobot.components.impl;

import br.com.contaazul.marsrobot.components.CommandExecutor;
import br.com.contaazul.marsrobot.enumeration.Command;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CommandStrategy {

    private Map<Command, CommandExecutor> strategies;

    private CommandStrategy(Set<CommandExecutor> strategiesSet) {
        strategies = strategiesSet.stream().collect(Collectors.toMap(CommandExecutor::getCommand, Function.identity()));
    }

    public CommandExecutor getExecutor(Command command) {
        return strategies.get(command);
    }
}
