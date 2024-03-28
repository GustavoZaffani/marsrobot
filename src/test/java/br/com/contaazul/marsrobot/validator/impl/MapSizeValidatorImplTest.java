package br.com.contaazul.marsrobot.validator.impl;

import br.com.contaazul.marsrobot.exception.LimitMapException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class MapSizeValidatorImplTest {

    @InjectMocks
    private MapSizeValidatorImpl mapSizeValidatorImpl;

    @Test
    @DisplayName("Não deve lançar exceção ao validar o tamanho do mapa, para uma posição válida")
    void testValidateLimitSizeWhenIsValid() {
        assertDoesNotThrow(() -> mapSizeValidatorImpl.validateLimitSize(5, 5));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 11})
    @DisplayName("Deve lançar exceção ao validar o tamanho do mapa, para uma posição X inválida")
    void testValidateLimitSizeWhenCoordinateXIsInvalid(Integer coordinate) {
        assertThrows(LimitMapException.class, () -> mapSizeValidatorImpl.validateLimitSize(coordinate, 5));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 11})
    @DisplayName("Deve lançar exceção ao validar o tamanho do mapa, para uma posição Y inválida")
    void testValidateLimitSizeWhenCoordinateYIsInvalid(Integer coordinate) {
        assertThrows(LimitMapException.class, () -> mapSizeValidatorImpl.validateLimitSize(5, coordinate));
    }
}
