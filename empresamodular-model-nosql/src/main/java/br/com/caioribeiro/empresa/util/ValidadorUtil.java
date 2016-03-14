package br.com.caioribeiro.empresa.util;

import java.util.Set;

import javax.validation.ConstraintViolation;

/**
 * The Class ValidadorUtil.
 */
/**
 * @author Caio Ribeiro
 *
 */
public final class ValidadorUtil {

    /**
     * Cria uma instancia de validador util.
     */
    private ValidadorUtil() {
    }

    /**
     * Contains error.
     *
     * @param errors the errors
     * @param expectedMessage the expected message
     * @return true, se tiver sucesso
     */
    public static boolean containsError(Set<ConstraintViolation<Object>> errors, String expectedMessage) {
        for(ConstraintViolation<Object> constraintViolation : errors) {
            if (constraintViolation.getMessage().equals(expectedMessage)) {
                return true;
            }
        }
        return false;
    }
}
