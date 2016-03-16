package br.com.caioribeiro.empresa;

import static br.com.caioribeiro.empresa.util.ValidadorUtil.containsError;
import static br.com.six2six.fixturefactory.Fixture.from;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author Caio Ribeiro
 *
 */
public class EnderecoTest {

    private Endereco endereco;

    private Validator validator;

    @Before
    public void setUp() {
        FixtureFactoryLoader.loadTemplates("br.com.caioribeiro.empresa.template");
        endereco = from(Endereco.class).gimme("valid");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

    }

    // Logradouro----------------------------------------------------------------------------

    @Test
    public void nao_deve_aceitar_logradouro_nulo() {
        endereco.setLogradouro(null);
        assertTrue(containsError(validator.validate(endereco), "O logradouro não pode estar vazio!"));
    }

    @Test
    public void nao_deve_aceitar_logradouro_vazio() {
        endereco.setLogradouro("");
        assertTrue(containsError(validator.validate(endereco), "O logradouro não pode estar vazio!"));
    }

    @Test
    public void nao_deve_aceitar_logradouro_com_menos_de_5_caracteres() {
        endereco.setLogradouro("aaa");
        assertTrue(containsError(validator.validate(endereco), "O Logradouro deve conter, no mínimo 5 e no máximo 80 letras!"));
    }

    @Test
    public void nao_deve_aceitar_logradouro_com_mais_de_80_caracteres() {
        endereco.setLogradouro("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        assertTrue(containsError(validator.validate(endereco), "O Logradouro deve conter, no mínimo 5 e no máximo 80 letras!"));
    }

    @Test
    public void deve_aceitar_o_logradouro() {
        String logradouroCompair = "Rua Teste";
        endereco.setLogradouro(logradouroCompair);
        assertEquals("Rua Teste", endereco.getLogradouro());
    }

    // Bairro----------------------------------------------------------------------------------
    @Test
    public void nao_deve_aceitar_bairro_nulo() {
        endereco.setBairro(null);
        assertTrue(containsError(validator.validate(endereco), "O bairro não pode estar vazio!"));
    }

    @Test
    public void nao_deve_aceitar_bairro_vazio() {
        endereco.setBairro("");
        assertTrue(containsError(validator.validate(endereco), "O bairro não pode estar vazio!"));
    }

    @Test
    public void nao_deve_aceitar_bairro_com_menos_de_5_letras() {
        endereco.setBairro("abcd");
        assertTrue(containsError(validator.validate(endereco), "O Bairro deve conter, no mínino 5 e no máximo 80 letras!"));
    }

    @Test
    public void nao_deve_aceitar_bairro_com_mais_de_80_letras() {
        endereco.setBairro("abcdefghijlkabcdefghijlkabcdefghijlkabcdefghijlkabcdefghijlkabcdefghijlkabcdefghijlkabcdefghijlk");
        assertTrue(containsError(validator.validate(endereco), "O Bairro deve conter, no mínino 5 e no máximo 80 letras!"));
    }

    @Test
    public void deve_aceitar_o_bairro() {
        String bairroCompair = "Jardim Flor da Montanha";
        endereco.setBairro(bairroCompair);
        assertEquals("Jardim Flor da Montanha", endereco.getBairro());
    }

    // CEP-------------------------------------------------------------------------------------------

    @Test
    public void nao_deve_aceitar_cep_nulo() {
        endereco.setCep(null);
        assertTrue(containsError(validator.validate(endereco), "O CEP não pode estar vazio!"));
    }

    @Test
    public void nao_deve_aceitar_cep_vazio() {
        endereco.setCep("");
        assertTrue(containsError(validator.validate(endereco), "O CEP não pode estar vazio!"));
    }

    @Test
    public void nao_deve_aceitar_um_cep_com_tamanho_diferente_de_8() {
        endereco.setCep("123456789");
        assertTrue(containsError(validator.validate(endereco), "O CEP não pode ter tamanho diferente de 8!"));
    }

    @Test
    public void nao_deve_aceitar_cep_fora_da_pattern() {
        endereco.setCep("07097170");
        assertTrue(containsError(validator.validate(endereco), "O CEP deve ser no formato \\d{4}\\-\\d{2}"));
    }

    @Test
    public void deve_aceitar_o_cep() {
        String cepCompair = "07097-170";
        endereco.setCep(cepCompair);
        assertEquals("07097-170", endereco.getCep());
    }

    // Cidade-------------------------------------------------------------------------------

    @Test
    public void nao_deve_aceitar_cidade_nula() {
        endereco.setCidade(null);
        assertTrue(containsError(validator.validate(endereco), "A cidade não pode estar vazia!"));
    }

    @Test
    public void nao_deve_aceitar_uma_cidade_vazia() {
        endereco.setCidade("");
        assertTrue(containsError(validator.validate(endereco), "A cidade não pode estar vazia!"));
    }

    @Test
    public void deve_gerar_excecao_de_tamanho_minimo_da_cidade() {
        endereco.setCidade("ABC");
        assertTrue(containsError(validator.validate(endereco), "A cidade não pode conter menos de 7 e mais de 50 letras!"));
    }

    @Test
    public void deve_gerar_excecao_de_tamanho_maximo_da_cidade() {
        endereco.setCidade("abcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcde");
        assertTrue(containsError(validator.validate(endereco), "A cidade não pode conter menos de 7 e mais de 50 letras!"));
    }

    @Test
    public void deve_aceitar_cidade() {
        String cidadeCompair = "São Paulo";
        endereco.setCidade(cidadeCompair);
        assertEquals("São Paulo", endereco.getCidade());
    }

    // Estado--------------------------------------------------------------------------------

    @Test
    public void nao_deve_aceitar_estado_nulo() {
        endereco.setEstado(null);
        assertTrue(containsError(validator.validate(endereco), "O estado não pode estar vazio!"));
    }

    @Test
    public void nao_deve_aceitar_estado_vazio() {
        endereco.setEstado("");
        assertTrue(containsError(validator.validate(endereco), "O estado não pode estar vazio!"));
    }

    @Test
    public void deve_gerar_excecao_de_tamanho_minimo_do_estado() {
        endereco.setEstado("A");
        assertTrue(containsError(validator.validate(endereco), "O estado deve conter 2 letras!"));
    }

    @Test
    public void deve_gerar_excecao_de_tamanho_maximo_do_estado() {
        endereco.setEstado("AAAA");
        assertTrue(containsError(validator.validate(endereco), "O estado deve conter 2 letras!"));
    }

    @Test
    public void deve_aceitar_o_estado() {
        String estadoCompair = "SP";
        endereco.setEstado(estadoCompair);
        assertEquals("SP", endereco.getEstado());
    }

    // Pais---------------------------------------------------------------------------------

    @Test
    public void nao_deve_aceitar_um_pais_nulo() {
        endereco.setPais(null);
        assertTrue(containsError(validator.validate(endereco), "O país não pode estar vazio!"));
    }

    @Test
    public void nao_deve_aceitar_pais_vazio() {
        endereco.setPais("");
        assertTrue(containsError(validator.validate(endereco), "O país deve conter 2 letras!"));
    }

    @Test
    public void nao_deve_aceitar_um_pais_menor_que_2() {
        endereco.setPais("B");
        assertTrue(containsError(validator.validate(endereco), "O país deve conter 2 letras!"));
    }

    @Test
    public void deve_gerar_excecao_de_tamanho_maximo_de_pais() {
        endereco.setPais("BRBRBRBRBRBRBRBRBRBRBRBR");
        assertTrue(containsError(validator.validate(endereco), "O país deve conter 2 letras!"));
    }

    @Test
    public void deve_aceitar_o_pais() {
        String paisCompair = "BR";
        endereco.setPais(paisCompair);
        assertEquals("BR", endereco.getPais());
    }

    // Numero--------------------------------------------------------------------------------
    @Test
    public void nao_deve_aceitar_que_numero_seja_igual_0() {
        endereco.setNumero(0);
        assertTrue(containsError(validator.validate(endereco), "O número deve conter, entre 1 e 9999, desde que não seja 0!"));
    }

    @Test
    public void nao_deve_aceitar_que_numero_seja_maior_que_9999() {
        endereco.setNumero(99999);
        assertTrue(containsError(validator.validate(endereco), "O número deve conter, entre 1 e 9999, desde que não seja 0!"));
    }

    @Test
    public void deve_aceitar_o_numero() {
        Integer numeroCompair = 144;
        endereco.setNumero(numeroCompair);
        assertEquals(numeroCompair, endereco.getNumero());
    }

    // Ignore-------------------------------------------------------------------------------------------
    @Ignore
    @Test
    public void deve_ignorar_teste() {
        endereco.setBairro(null);
    }

    // Timeout---------------------------------------------------------------------------------------

    @Test(timeout = 500)
    public void deve_retornar_o_endereco_em_25_milissegundos() throws InterruptedException {
        Thread.sleep(500);
    }

    // Tipo de Endereço----------------------------------------------------------------------------

    @Test
    public void nao_deve_aceitar_o_tipo_de_endereco_nulo() {
        endereco.setEnderecoType(null);
        assertTrue(containsError(validator.validate(endereco), "O tipo do endereço pode ser nulo!"));

    }

    @Test
    public void deve_aceitar_o_tipo_de_endereco() {
        endereco.setEnderecoType(EnderecoType.COMERCIAL);
    }

    @Test
    public void deve_aceitar_o_novo_tipo_de_endereco() {
        endereco.setEnderecoType(EnderecoType.RESIDENCIAL);
        assertEquals(endereco.getEnderecoType(), EnderecoType.RESIDENCIAL);
    }

    @Test
    public void deve_respeitar_o_contrato_equals_e_hashcode() {
        EqualsVerifier.forClass(Endereco.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

}
