package br.com.contaazul.marsrobot.validator.impl;

import br.com.contaazul.marsrobot.constants.MarsConstants;
import br.com.contaazul.marsrobot.exception.LimitMapException;
import br.com.contaazul.marsrobot.validator.MapSizeValidator;
import org.springframework.stereotype.Component;

@Component
public class MapSizeValidatorImpl implements MapSizeValidator {

    @Override
    public void validateLimitSize(Integer coordinateX, Integer coordinateY) {
        if (coordinateXIsInvalid(coordinateX) || coordinateYIsInvalid(coordinateY)) {
            throw new LimitMapException("Robot out of map size");
        }
    }

    private boolean coordinateXIsInvalid(Integer coordinateX) {
        return coordinateX > MarsConstants.MAX_X_AXIS || coordinateX < MarsConstants.MIN_X_AXIS;
    }

    private boolean coordinateYIsInvalid(Integer coordinateY) {
        return coordinateY > MarsConstants.MAX_Y_AXIS || coordinateY < MarsConstants.MIN_Y_AXIS;
    }
}
