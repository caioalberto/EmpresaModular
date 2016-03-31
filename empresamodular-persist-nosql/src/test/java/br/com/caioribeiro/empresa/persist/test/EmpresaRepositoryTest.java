package br.com.caioribeiro.empresa.persist.test;

import static br.com.six2six.fixturefactory.Fixture.from;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.MongoSocketException;

import br.com.caioribeiro.empresa.Email;
import br.com.caioribeiro.empresa.Empresa;
import br.com.caioribeiro.empresa.Endereco;
import br.com.caioribeiro.empresa.Telefone;
import br.com.caioribeiro.empresa.repository.EmpresaRepository;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

public class EmpresaRepositoryTest {
    
    private String cnpj;
    private Empresa empresa;
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
    public void deve_inserir_200000_registros_no_banco() {
        int i = 0;
        while (i < 74000) {
            i++;
            try {
                empresa = from(Empresa.class).gimme("valid");
                er.saveOne(empresa);
            } catch (IllegalStateException ise) {
                continue;
            } catch (MongoSocketException mse) {
                continue;
            }
        }
    }
    
    @Test
    public void deve_atualizar_uma_empresa_de_acordo_com_a_empresa_passada() {
        Empresa empresa3 = new Empresa();
        empresa3.setRazaoSocial("Softmatic LTDA.");
        empresa3.setNomeFantasia("Contmatic Phoenix");
        er.updateById(cnpj, empresa3);
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
            Empresa emp = new Empresa();
            emp.setRazaoSocial("Softmatic LTDA.");
            er.updateVarious(emp, empresa);
    }
    
    
    @Test
    public void deve_deletar_uma_empresa_do_banco() {
        er.deleteOne(cnpj);
    }
    
    @Test
    public void deve_deletar_varios_objetos_do_banco() {
        Empresa empresa2 = new Empresa();
        empresa2.setRazaoSocial("Kaza Arquitetura Ltda");
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
        System.out.println(er.findByFields(empresa3));
    }
    
    @Test
    public void deve_retornar_uma_pesquisa_apenas_com_os_campos_selecionados() {
        Empresa empresa3 = new Empresa();
        empresa3.setRazaoSocial("Softmatic LTDA.");
        System.out.println(er.findBySpecifiedFields(empresa3));
    }
    
    @Test
    public void deve_retornar_uma_pesquisa_de_empresas_de_acordo_com_a_quantidade_e_o_numero_de_paginas_passados() {
       System.out.println(er.findByNumberAndQuantity(empresa, 3, 20));
    }
    
    @Test
    public void deve_pesquisar_uma_empresa_e_retornar_apenas_a_razao_social() {
        Empresa empresaFilter = new Empresa();
        empresaFilter.setRazaoSocial("Softmatic LTDA.");
        System.out.println(er.findBySpecifiedFields(empresaFilter));
    }

    @Test
    public void deve_pesquisar_uma_empresa_e_retornar_a_razao_social_e_o_nome_fantasia() {
        Empresa empresaFilter = new Empresa();
        empresaFilter.setRazaoSocial("Softmatic LTDA.");
        empresaFilter.setNomeFantasia("Contmatic Phoenix");
        System.out.println(er.findBySpecifiedFields(empresaFilter));
    }
    
    @Test
    public void deve_pesquisar_uma_empresa_e_retornar_a_razao_social_o_nome_fantasia_e_os_telefones() {
        Empresa empresaFilter = new Empresa();
        empresaFilter.setRazaoSocial("Softmatic Testes Software LTDA.");
        empresaFilter.setNomeFantasia("Contmatic Phoenix");
        System.out.println(er.findBySpecifiedFields(empresaFilter));
    }
}
