package br.com.contaazul.marsrobot.components;

import br.com.contaazul.marsrobot.dto.LocalizationDTO;
import br.com.contaazul.marsrobot.enumeration.Command;

public interface CommandExecutor {

    LocalizationDTO execute(LocalizationDTO localizationDTO);

    Command getCommand();
}
