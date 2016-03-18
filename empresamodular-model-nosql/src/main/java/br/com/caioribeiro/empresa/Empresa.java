package br.com.caioribeiro.empresa;

import java.io.Serializable;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.beanvalidation.tck.tests.xmlconfiguration.constraintdefinition.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import br.com.caelum.stella.bean.validation.CNPJ;
import br.com.caioribeiro.empresa.stringbuilder.MyStyle;

/**
 *
 * Classe Empresa, define a criacao de um objeto do tipo empresa, assim como seus atributos e metodos.
 * 
 * @author Caio Ribeiro
 *
 */
public final class Empresa implements Serializable {

    private static final long serialVersionUID = 8234130616027975394L;

    /**
     * 
     */

    // Variaveis de Atributo-------------------------------------------------------------------------------------
    /**
     * Define uma lista de Enderecos, para armazenar os enderecos de uma empresa caso haja mais de um.
     */
    @NotEmpty(message = "A lista de endereços não pode estar vazia!")
    @NotNull(message = "A lista de endereços não pode estar vazia!")
    @Valid
    private Set<Endereco> enderecos;

    /**
     * Define uma lista de Telefones, para armazenar os telefones de uma empresa.
     */
    @NotEmpty(message = "A lista de telefones não pode estar vazia!")
    @NotNull(message = "A lista de telefones não pode estar vazia!")
    @Valid
    private Set<Telefone> telefones;

    /**
     * Define o CNPJ de uma empresa.
     */
    @Length(min = 14, max = 14, message = "O CNPJ deve ter {max} dígitos!")
    @NotBlank(message = "O CNPJ deve ser preenchido!")
    @CNPJ(formatted = false, message = "Não é um CNPJ válido!")
    private String cnpj;

    /**
     * Define a razao social de uma empresa.
     */
    @NotBlank(message = "A Razão Social deve ser preenchida!")
    @NotEmpty(message = "A Razão Social deve ser preenchida!")
    @Length(min = 10, max = 80, message = "A Razão Social deve ter entre {min} e {max} letras!")
    @Pattern(regexp = "^[A-Za-z\\sçáéíóúàâêôãõëöü]+$", message = "A Razão Social deve conter apenas letras!")
    private String razaoSocial;

    /**
     * Define o nome fantasia de uma empresa.
     */
    @NotBlank(message = "O Nome Fantasia deve ser preenchido!")
    @Size(min = 10, max = 80, message = "O Nome Fantasia deve ter entre {min} e {max} letras!")
    @Pattern(regexp = "\\w\\^[^|]|^[^;]*$", message = "O Nome Fantasia não pode conter pipe/ponto-vírgula!")
    private String nomeFantasia;

    /**
     *
     * Define uma String que armazena o email da empresa.
     */
    @NotEmpty(message = "A lista de E-mails não pode estar vazia!")
    @NotNull(message = "A lista de E-mails não pode estar vazia!")
    private Set<Email> emails;

    /**
     *
     * Define a data de cadastro de uma empresa dentro do objeto.
     */
    @Past(message = "A data não pode ser posterior a data atual!")
    @NotNull(message = "A data não pode ser nula!")
    private DateTime dataDeCadastro;

    /**
     * 
     * Define a data de alteracao de uma empresa dentro do objeto.
     */
    @Past(message = "A data de alteração não pode ser anterior a data de cadastro!")
    @NotNull(message = "A data não pode ser nula!")
    private DateTime dataDeAlteracao;

    // Getters e Setters------------------------------------------------------------------------------------------
    /**
     * Gets the enderecos.
     *
     * @return the enderecos
     */
    public Set<Endereco> getEnderecos() {
        return enderecos;
    }

    /**
     * Gets the telefones.
     *
     * @return the telefones
     */
    public Set<Telefone> getTelefones() {
        return telefones;
    }

    /**
     * Gets the cnpj.
     *
     * @return the cnpj
     */
    public String getCnpj() {
        return cnpj;
    }

    /**
     * Gets the razao social.
     *
     * @return the razao social
     */
    public String getRazaoSocial() {
        return razaoSocial;
    }

    /**
     * Gets the nome fantasia.
     *
     * @return the nome fantasia
     */
    public String getNomeFantasia() {
        return nomeFantasia;
    }

    /**
     * Gets the emails.
     *
     * @return the emails
     */
    public Set<Email> getEmails() {
        return emails;
    }

    /**
     * Gets the data de cadastro.
     *
     * @return the data de cadastro
     */
    public DateTime getDataDeCadastro() {
        return dataDeCadastro;
    }

    /**
     * Seta o atributo endereco.
     *
     * @param enderecos novo valor de endereco
     */
    public void setEndereco(Set<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    /**
     * Seta o atributo telefone.
     *
     * @param telefones novo valor de telefone
     */
    public void setTelefone(Set<Telefone> telefones) {
        this.telefones = telefones;
    }

    /**
     * Seta o atributo cnpj.
     *
     * @param cnpj novo valor de cnpj
     */
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    /**
     * Seta o atributo razao social.
     *
     * @param razaoSocial novo valor de razao social
     */
    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    /**
     * Seta o atributo nome fantasia.
     *
     * @param nomeFantasia novo valor de nome fantasia
     */
    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    /**
     * Seta o atributo emails.
     *
     * @param emails novo valor de emails
     */
    public void setEmails(Set<Email> emails) {
        this.emails = emails;
    }

    /**
     * Seta o atributo data de cadastro.
     *
     * @param dataDeCadastro novo valor de data de cadastro
     */
    public void setDataDeCadastro(DateTime dataDeCadastro) {
        this.dataDeCadastro = dataDeCadastro;
    }

    /**
     * Gets the data de alteracao.
     *
     * @return the data de alteracao
     */
    public DateTime getDataDeAlteracao() {
        return dataDeAlteracao;
    }

    /**
     * Seta o atributo data de alteracao.
     *
     * @param dataDeAlteracao novo valor de data de alteracao
     */
    public void setDataDeAlteracao(DateTime dataDeAlteracao) {
        this.dataDeAlteracao = dataDeAlteracao;
    }

    // hashCode, equals e to String----------------------------------------------------------------------------------------------------
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.cnpj).toHashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Empresa)) {
            return false;
        }
        Empresa other = (Empresa) obj;
        return new EqualsBuilder().append(this.cnpj, other.cnpj).isEquals();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/YYYY");
        return new ToStringBuilder(this, MyStyle.MY_STYLE).append(this.razaoSocial != null ? "Razão Social: " + this.razaoSocial : null).append(this.cnpj != null ? "CNPJ: " + this.cnpj : null)
                .append(enderecos != null ? "Endereço: " + enderecos : null).append(telefones != null ? "Contato: " + telefones : null).append(emails != null ? emails + "\n" : null)
                .append(this.dataDeCadastro != null ? "Data de Abertura: " + dtf.print(this.dataDeCadastro) : null)
                .append(this.dataDeCadastro != null ? "Data de Alteração: " + dtf.print(this.dataDeAlteracao) : null).toString();
    }
}
