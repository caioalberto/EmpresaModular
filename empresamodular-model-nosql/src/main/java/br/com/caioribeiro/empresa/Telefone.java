package br.com.caioribeiro.empresa;

import static org.apache.commons.lang3.StringUtils.isNumeric;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import br.com.caioribeiro.empresa.stringbuilder.MyTelephoneStyle;

/**
 *
 * Classe Telefone, define a criacao de um objeto do tipo telefone, assim como seus atributos e metodos.
 *
 * @author Caio Alberto
 */
/**
 * @author Caio Ribeiro
 *
 */
public final class Telefone {

    // Variaveis e constantes------------------------------------------------------------------------
    /**
     * Define o numero de telefone da classe.
     */
    @NotBlank(message = "O telefone não pode estar vazio!")
    @Size(max = 9, min = 8, message = "O telefone não pode ter menos de {min} dígitos e mais de {max} dígitos!")
    @Pattern(regexp = "([2-5]{1}\\d{3}-\\d{4})|(\\9[4-9]\\d{3}\\-\\d{4})", message = "O Telefone deve ser no modelo: {regexp}")
    private String telefone;

    /**
     * Define o tipo de telefone da classe. Se fixo, celular, fax etc.
     */
    @Valid
    private TelefoneType tipo;

    /**
     * Define o DDD do telefone, sempre com apenas dois numeros.
     */
    @Range(min = 11, max = 99, message = "O DDD deve ser entre {min} e {max}!")
    private Integer ddd;

    // Getters e Setters-----------------------------------------------------------------------------------------
    /**
     * Gets the telefone.
     *
     * @return the telefone
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Gets the tipo.
     *
     * @return the tipo
     */
    public TelefoneType getTipo() {
        return tipo;
    }

    /**
     * Gets the ddd.
     *
     * @return the ddd
     */
    public Integer getDdd() {
        return ddd;
    }

    /**
     * Seta o atributo telefone.
     *
     * @param telefone novo valor de telefone
     */
    public void setTelefone(String telefone) {
        isNumeric(telefone);
        this.telefone = telefone;
    }

    /**
     * Seta o atributo ddd.
     *
     * @param ddd novo valor de ddd
     */
    public void setDdd(Integer ddd) {
        this.ddd = ddd;
    }

    /**
     * Seta o atributo tipo.
     *
     * @param tipo novo valor de tipo
     */
    public void setTipo(TelefoneType tipo) {
        // this.validaTipo(tipo);
        this.tipo = tipo;
    }

    // Equals, HashCode e toString---------------------------------------------------------------------------------------------------
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.tipo).append(this.telefone).toHashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Telefone)) {
            return false;
        }
        Telefone other = (Telefone) obj;
        return new EqualsBuilder().append(this.tipo, other.tipo).append(this.telefone, other.telefone).isEquals();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, MyTelephoneStyle.MY_TELEPHONE_STYLE).append(this.ddd != null ? this.ddd : null)
                .append(this.telefone != null ? this.telefone : null).append(this.tipo != null ? this.tipo : null).toString();
    }

}
