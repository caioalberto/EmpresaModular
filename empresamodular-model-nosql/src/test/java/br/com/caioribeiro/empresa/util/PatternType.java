package br.com.caioribeiro.empresa.util;

/**
 * Classe utilitaria para geracao de regex.
 * @author Caio Ribeiro
 *
 */
public final class PatternType {

    private PatternType() {
    }

    /**
     * Constante de regex para telefone fixo;
     */
    public static final String TELEFONE_FIXO_REGEX = "[2-5]{1}\\d{3}-\\d{4}";
    
    /**
     * Constante de regex para telefone celular;
     */
    public static final String CELULAR_REGEX = "\\9[4-9]{1}\\d{3}\\-\\d{4}";
    
}
