package br.com.caioribeiro.empresa.util;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

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
    
    /**
     * Verifica se o objeto contem erros
     * @param obj
     * @return erros, se contiver
     */
    public static Set<ConstraintViolation<Object>> containsError(Object obj) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> errors = validator.validate(obj);
        if (errors.size() != 0);            
            return errors;
    }
        
    /**
     * Recebe os erros, e cria um set de string com as mensagens de erro
     * @param errors
     * @return, o set das mensagens de erro
     */
    public static Set<String> errorMessages (Set<ConstraintViolation<Object>> errors) {
        Set<String> messageErrors = new HashSet<>();
        for(ConstraintViolation<Object> constraintViolation : errors) {            
            messageErrors.add(constraintViolation.getMessage());            
        }
        return messageErrors;
    }
    
}
