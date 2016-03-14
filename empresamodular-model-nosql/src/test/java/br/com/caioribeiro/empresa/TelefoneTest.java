package br.com.caioribeiro.empresa;

import static br.com.caioribeiro.empresa.util.ValidadorUtil.containsError;
import static br.com.six2six.fixturefactory.Fixture.from;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeNoException;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * The Class TelefoneTest.
 *
 * @author Caio Ribeiro
 */
public final class TelefoneTest {

    /** The telefones. */
    private Set<Telefone> telefones = new HashSet<>();

    /** The t3. */
    private Telefone telefone;
    private Telefone t1, t2;

    private Validator validator;

    /**
     * Set up before class.
     */
    @Before
    public void setUp() {
        // Carregando os templates de teste
        FixtureFactoryLoader.loadTemplates("br.com.caioribeiro.empresa.template");

        // Atribuindo aos objetos um template
        telefone = from(Telefone.class).gimme("valid");
        t1 = from(Telefone.class).gimme("valid");
        t2 = from(Telefone.class).gimme("valid");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // Numero do Telefone----------------------------------------------------------------------------------------------------------------------------------------------------------
    @Test
    public void nao_deve_aceitar_um_telefone_nulo() {
        telefone.setTelefone(null);
        assertTrue(containsError(validator.validate(telefone), "O telefone não pode estar vazio!"));
    }

    @Test
    public void nao_deve_aceitar_um_telefone_vazio() {
        telefone.setTelefone("");
        assertTrue(containsError(validator.validate(telefone), "O telefone não pode estar vazio!"));
    }

    @Test
    public void nao_deve_aceitar_um_telefone_com_menos_de_8() {
        telefone.setTelefone("123");
        assertTrue(containsError(validator.validate(telefone), "O telefone não pode ter menos de 8 dígitos e mais de 9 dígitos!"));
    }
    

    /**
     * Deve_gerar_uma_excecao_de_tamanho_maximo_do_telefone.
     */
    @Test
    public void nao_deve_aceitar_um_telefone_com_mais_de_9() {
        telefone.setTelefone("1234567890");
        assertTrue(containsError(validator.validate(telefone), "O telefone não pode ter menos de 8 dígitos e mais de 9 dígitos!"));
    }

    @Test
    public void nao_deve_aceitar_telefone_fora_da_pattern() {
        telefone.setTelefone("44657788");
        assertTrue(containsError(validator.validate(telefone), "O Telefone deve ser no modelo: ([2-5]{1}\\d{3}-\\d{4})|(\\9[4-9]\\d{3}\\-\\d{4})"));
    }

    @Test
    public void deve_aceitar_o_numero_do_telefone() {
        String telefoneComparar = "2459-4064";
        telefone.setTelefone(telefoneComparar);
        assertEquals(telefoneComparar, telefone.getTelefone());
        
    }

    // DDD-------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    @Test
    public void nao_deve_aceitar_um_ddd_maior_que_2() {
        telefone.setDdd(123);
        assertTrue(containsError(validator.validate(telefone), "O DDD deve ser entre 11 e 99!"));
    }

    /**
     * Deve_aceitar_o_numero_do_ddd.
     */
    @Test
    public void deve_aceitar_o_numero_do_ddd() {
        Integer dddCompair = 18;
        telefone.setDdd(dddCompair);
        assertEquals(dddCompair, telefone.getDdd());
    }

    // Contrato equals hashCode----------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * Deve_respeitar_o_contrato_equals_e_hashcode.
     */
    @Test
    public void deve_respeitar_o_contrato_equals_e_hashcode() {
        EqualsVerifier.forClass(Telefone.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    /**
     * Deve_retornar_se_o_objeto_e_da_mesma_instancia.
     */
    @Test
    public void deve_retornar_se_o_objeto_e_da_mesma_instancia() {
        assertThat(telefone, instanceOf(Telefone.class));
    }

    /**
     * Deve_retornar_se_o_numero_do_telefone_comeca_com_determinado_caractere.
     */
    @Test
    public void deve_retornar_se_o_numero_do_telefone_comeca_com_determinado_caractere() {
        telefone.setTelefone("24594064");
        assertThat(telefone.getTelefone(), startsWith("24"));
    }

    /**
     * Deve_retornar_se_a_lista_contem_os_objetos.
     */
    @Test
    public void deve_retornar_se_a_lista_contem_os_objetos() {
        telefones.add(telefone);
        telefones.add(t1);
        telefones.add(t2);
        assertThat(telefones, hasItems(t1, t2, telefone));
    }

    @Test
    public void deve_retornar_o_tipo_do_telefone_como_celular() {
        Telefone tel36 = new Telefone();
        tel36.setTelefone("966560001");
        tel36.setTipo(TelefoneType.CELULAR);
    }

    /**
     * Deve_assumir_a_excecao_e_rodar_mesmo_assim.
     */
    @Test
    public void deve_assumir_a_excecao_e_rodar_mesmo_assim() {
        try {
            t1.setTelefone(null);
        } catch (NullPointerException e) {
            assumeNoException(e);
        }
    }

}
