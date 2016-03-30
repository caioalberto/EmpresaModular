package br.com.caioribeiro.empresa.persist.test;

import static br.com.caioribeiro.empresa.assembler.EmpresaAssembler.empresaToDocument;
import static br.com.six2six.fixturefactory.Fixture.from;
import static junit.framework.Assert.assertNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.caioribeiro.empresa.Email;
import br.com.caioribeiro.empresa.Empresa;
import br.com.caioribeiro.empresa.Endereco;
import br.com.caioribeiro.empresa.Telefone;
import br.com.caioribeiro.empresa.repository.EmpresaRepository;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

public class EmpresaRepositoryTest {
    
    private String cnpj;
    private Empresa empresa, empresa2;
    private Endereco endereco;
    private Endereco endereco2;
    private Telefone telefone;
    private Email email;
    private Set<Endereco> enderecos = new HashSet<Endereco>();
    private Set<Email> emails = new HashSet<Email>();
    private Set<Telefone> telefones = new HashSet<Telefone>();
    
  EmpresaRepository er = new EmpresaRepository("localhost", 27017, "empresa");

  @BeforeClass
  public static void setUpBeforeClass() {
      
      FixtureFactoryLoader.loadTemplates("br.com.caioribeiro.empresa.template");
  }
  
  @Before
  public void setUp() {
      
      cnpj = er.findObjectAndReturnString();
      
      empresa = from(Empresa.class).gimme("valid");
      empresa2 = new Empresa();
      
      endereco = from(Endereco.class).gimme("valid");
      endereco2 = from(Endereco.class).gimme("valid");
      
      enderecos.add(endereco);
      enderecos.add(endereco2);
      
      telefone = from(Telefone.class).gimme("valid");
      
      telefones.add(telefone);
      
      email = from(Email.class).gimme("valid");
      
      emails.add(email);
                        
  }
  
    @Test
    public void deve_salvar_um_objeto_no_banco() {        
        er.saveOne(empresa);
    }
    
    @Test
    public void deve_inserir_200_registros_no_banco() {
        int i = 0;
        while (i < 20) {
            i++;
            empresa = from(Empresa.class).gimme("valid");
            er.saveOne(empresa);
        }
    }

    @Test
    public void deve_retornar_null_se_empresa_for_nula() {        
        assertNull(empresaToDocument(null));
    }
    
    @Test
    public void deve_atualizar_uma_empresa_de_acordo_com_a_empresa_passada() {
        Empresa empresa3 = new Empresa();
        empresa3.setNomeFantasia("Contmatic ix");
        empresa3.setRazaoSocial("Razão SociaTDA.");
        empresa3.setDataDeCadastro(DateTime.now().minusYears(5));
        empresa3.setDataDeAlteracao(DateTime.now().plusDays(20));
        er.updateOne(empresa, empresa3);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void deve_gerar_uma_excecao_de_empresa_com_erros() {
        empresa.setCnpj("1");
        empresa.setNomeFantasia("");
        empresa.setRazaoSocial("");
        empresa.setEndereco(enderecos);
        empresa.setTelefone(telefones);
        empresa.setEmails(emails);
        empresa.setDataDeCadastro(DateTime.now().minusMonths(6));
        empresa.setDataDeAlteracao(DateTime.now());
        er.saveOne(empresa);
    }
    
    @Test
    public void deve_atualizar_todas_as_empresas_de_acordo_com_o_campo() {
        Empresa empresa3 = new Empresa();
        empresa3.setNomeFantasia("Contmatic Phoenix");
        empresa3.setRazaoSocial("Softmatic LTDA.");
        empresa3.setDataDeCadastro(DateTime.now());
        empresa3.setDataDeAlteracao(DateTime.now());
        er.updateVarious(empresa3);
    }
    
    
    @Test
    public void deve_deletar_uma_empresa_do_banco() {
        er.deleteOne(cnpj);
    }
    
    @Test
    public void deve_deletar_varios_objetos_do_banco() {
        empresa2.setNomeFantasia("Teste de empresa");
        empresa2.setRazaoSocial("Contmatic LTDA");
        er.deleteVarious(empresa2);
    }
    
    @Test
    public void deve_localizar_uma_empresa_de_acordo_com_a_chave_primaria() {
        System.out.println(er.find(cnpj));
    }
    
    @Test
    public void deve_localizar_uma_empresa_de_acordo_com_o_filtro() {
        Empresa empresa3 = new Empresa();
        empresa3.setRazaoSocial("Softmatic LTDA.");        
        System.out.println(er.findByFields(empresa, empresa3));
    }
    
    @Test
    public void deve_retornar_uma_pesquisa_apenas_com_os_campos_selecionados() {
        Empresa empresa3 = new Empresa();
        empresa3.setRazaoSocial("Sofmatic LTDA.");
        System.out.println(er.findBySpecifiedFields(empresa3));
    }
    
    @Test
    public void deve_retornar_uma_pesquisa_de_empresas_de_acordo_com_a_quantidade_e_o_numero_de_paginas_passados() {
       System.out.println(er.findByNumberAndQuantity(empresa, 3, 20));
    }

}
