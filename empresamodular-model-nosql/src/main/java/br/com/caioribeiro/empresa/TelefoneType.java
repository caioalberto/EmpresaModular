package br.com.caioribeiro.empresa;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

/**
 * The Enum TelefoneType.
 */
/**
 * @author Caio Ribeiro
 *
 */

public enum TelefoneType {

                          /** The celular. */
                          CELULAR(9, "Celular"),
                          /** The fax. */
                          FAX(8, "Fax"),
                          /** The comercial. */
                          COMERCIAL(8, "Comercial");

    /** The qtd numeros. */
    @Range(min = 8, max = 9, message = "Número de telefone inválido, insira um telefone com a qunatidade de números entre {min} e {max}!")
    private final int qtdNumeros;

    /** The nome tipo. */
    @NotBlank(message = "O tipo não pode estar vazio!")
    private final String nomeTipo;

    /**
     * Cria uma instancia de telefone type.
     *
     * @param telefone the telefone
     * @param descricao the descricao
     */
    TelefoneType(int telefone, String descricao) {
        qtdNumeros = telefone;
        nomeTipo = descricao;
    }

    /**
     * Gets the qtd numeros.
     *
     * @return the qtd numeros
     */
    public int getQtdNumeros() {
        return qtdNumeros;
    }

    /**
     * Gets the nome tipo.
     *
     * @return the nome tipo
     */
    public String getNomeTipo() {
        return nomeTipo;
    }

}
