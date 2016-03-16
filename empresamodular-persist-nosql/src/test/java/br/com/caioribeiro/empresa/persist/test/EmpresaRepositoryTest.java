package br.com.caioribeiro.empresa.persist.test;

import static br.com.caioribeiro.empresa.repository.util.EmpresaToDocument.empresaToDocument;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.Document;
import org.joda.time.DateTime;
import org.junit.Test;

import br.com.caioribeiro.empresa.Email;
import br.com.caioribeiro.empresa.Empresa;
import br.com.caioribeiro.empresa.Endereco;
import br.com.caioribeiro.empresa.EnderecoType;
import br.com.caioribeiro.empresa.Telefone;
import br.com.caioribeiro.empresa.TelefoneType;
import br.com.caioribeiro.empresa.repository.EmpresaRepository;

public class EmpresaRepositoryTest {
    
    /** The empresa. */
    private Empresa empresa = new Empresa();
    private Empresa empresa2 = new Empresa();
    
  EmpresaRepository er = new EmpresaRepository("localhost", 27017, "empresa");

@Test
    public void deve_salvar_um_objeto_no_banco() {
        Set<Endereco> enderecos = new HashSet<Endereco>();
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua José dos Santos");
        endereco.setNumero(143);
        endereco.setBairro("Jd Flor da Montanha");
        endereco.setCep("07097170");
        endereco.setCidade("Guarulhos");
        endereco.setEstado("SP");
        endereco.setPais("BR");
        endereco.setEnderecoType(EnderecoType.RESIDENCIAL);
        enderecos.add(endereco);
        
        Set<Telefone> telefones = new HashSet<Telefone>();
        Telefone telefone = new Telefone();
        telefone.setDdd(11);
        telefone.setTelefone("2459-4064");
        telefone.setTipo(TelefoneType.COMERCIAL);
        telefones.add(telefone);
        
        Set<Email> emails = new HashSet<Email>();
        Email email = new Email();
        email.setEnderecoDeEmail("caioalberto11@gmail.com");
        emails.add(email);

        
        EmpresaRepository er = new EmpresaRepository("localhost", 27017, "empresa"); 
        empresa.setCnpj("12345678901234");
        empresa.setNomeFantasia("Nome Fantasia LTDA.");
        empresa.setRazaoSocial("Razão Social");
        empresa.setEndereco(enderecos);
        empresa.setTelefone(telefones);
        empresa.setEmails(emails);
        empresa.setDataDeCadastro(DateTime.now().minusMonths(6));
        empresa.setDataDeAlteracao(DateTime.now());
        er.save(empresa);              
    }
    
    @Test
    public void deve_retornar_null_se_empresa_for_nula() {        
        assertNull(empresaToDocument(null));
    }
    

    @Test
    public void deve_retornar_se_o_documento_gerado_e_igual_ao_esperado() {
        Set<Endereco> enderecos = new HashSet<Endereco>();
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua José dos Santos2");
        endereco.setNumero(143);
        endereco.setBairro("Jd Flor da Montanha");
        endereco.setCep("07097170");
        endereco.setCidade("Guarulhos");
        endereco.setEstado("SP");
        endereco.setPais("BR");
        endereco.setEnderecoType(EnderecoType.RESIDENCIAL);
        enderecos.add(endereco);
        
        Set<Telefone> telefones = new HashSet<Telefone>();
        Telefone telefone = new Telefone();
        telefone.setDdd(11);
        telefone.setTelefone("2459-4064");
        telefone.setTipo(TelefoneType.COMERCIAL);
        telefones.add(telefone);

        Set<Email> emails = new HashSet<Email>();
        Email email = new Email();
        email.setEnderecoDeEmail("caioalberto11@gmail.com");
        emails.add(email);

        empresa.setCnpj("12345678901234");
        empresa.setNomeFantasia("Nome Fantasia LTDA.");
        empresa.setRazaoSocial("Razão Social");
        empresa.setEndereco(enderecos);
        empresa.setTelefone(telefones);
        empresa.setEmails(emails);
        empresa.setDataDeCadastro(DateTime.now().minusMonths(6));
        empresa.setDataDeAlteracao(DateTime.now());

        Document empresaDoc = empresaToDocument(empresa);
        assertEquals(empresaDoc.get("cnpj"), empresa.getCnpj());
        assertEquals(empresaDoc.get("nomeFantasia"), empresa.getNomeFantasia());
        assertEquals(empresaDoc.get("razaoSocial"), empresa.getRazaoSocial());
        assertEquals(empresaDoc.get("dataDeCriacao"), empresa.getDataDeCadastro().toDate());
        assertEquals(empresaDoc.get("dataDeAlteracao"), empresa.getDataDeAlteracao().toDate());

        List<Document> enderecosDoc = (List<Document>) empresaDoc.get("enderecos");
        for(Document document : enderecosDoc) {
            if(document.get("bairro").equals(endereco.getBairro())){
            assertEquals(endereco.getBairro(), document.get("bairro"));
            assertEquals(endereco.getCep(), document.get("cep"));
            assertEquals(endereco.getCidade(), document.get("cidade"));
            assertEquals(endereco.getEnderecoType().name(), document.get("tipoEndereco"));
            assertEquals(endereco.getEstado(), document.get("estado"));
            assertEquals(endereco.getLogradouro(), document.get("logradouro"));
            assertEquals(endereco.getNumero(), document.get("numero"));
            assertEquals(endereco.getPais(), document.get("pais"));
            }
        }
    }
    
    @Test
    public void deve_percorrer_um_set_de_enderecos_com_mais_de_um_e_retornar_se_esta_contido_no_document() {
        Set<Endereco> enderecos = new HashSet<Endereco>();
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua José dos Santos2");
        endereco.setNumero(143);
        endereco.setBairro("Jd Flor da Montanha");
        endereco.setCep("07097170");
        endereco.setCidade("Guarulhos");
        endereco.setEstado("SP");
        endereco.setPais("BR");
        endereco.setEnderecoType(EnderecoType.RESIDENCIAL);
        enderecos.add(endereco);
        
        Endereco endereco2 = new Endereco();
        endereco2.setLogradouro("Rua hagsagsuygasyua");
        endereco2.setNumero(1);
        endereco2.setBairro("asuhdiusahdusai");
        endereco2.setCep("07147-850");
        endereco2.setCidade("Gsadsa");
        endereco2.setEstado("SP");
        endereco2.setPais("BR");
        endereco2.setEnderecoType(EnderecoType.COMERCIAL);
        enderecos.add(endereco2);

        Set<Telefone> telefones = new HashSet<Telefone>();
        Telefone telefone = new Telefone();
        telefone.setDdd(11);
        telefone.setTelefone("2459-4064");
        telefone.setTipo(TelefoneType.COMERCIAL);
        telefones.add(telefone);

        Set<Email> emails = new HashSet<Email>();
        Email email = new Email();
        email.setEnderecoDeEmail("caioalberto11@gmail.com");
        emails.add(email);

        empresa.setCnpj("12345678901234");
        empresa.setNomeFantasia("Nome Fantasia LTDA.");
        empresa.setRazaoSocial("Razão Social");
        empresa.setEndereco(enderecos);
        empresa.setTelefone(telefones);
        empresa.setEmails(emails);
        empresa.setDataDeCadastro(DateTime.now().minusMonths(6));
        empresa.setDataDeAlteracao(DateTime.now());

        Document empresaDoc = empresaToDocument(empresa);
        assertEquals(empresaDoc.get("cnpj"), empresa.getCnpj());
        assertEquals(empresaDoc.get("nomeFantasia"), empresa.getNomeFantasia());
        assertEquals(empresaDoc.get("razaoSocial"), empresa.getRazaoSocial());
        assertEquals(empresaDoc.get("dataDeCriacao"), empresa.getDataDeCadastro().toDate());
        assertEquals(empresaDoc.get("dataDeAlteracao"), empresa.getDataDeAlteracao().toDate());

        List<Document> enderecosDoc = (List<Document>) empresaDoc.get("enderecos");
        for(Document document : enderecosDoc) {
            if(document.get("bairro").equals(endereco.getBairro())){
            assertEquals(endereco.getBairro(), document.get("bairro"));
            assertEquals(endereco.getCep(), document.get("cep"));
            assertEquals(endereco.getCidade(), document.get("cidade"));
            assertEquals(endereco.getEnderecoType().name(), document.get("tipoEndereco"));
            assertEquals(endereco.getEstado(), document.get("estado"));
            assertEquals(endereco.getLogradouro(), document.get("logradouro"));
            assertEquals(endereco.getNumero(), document.get("numero"));
            assertEquals(endereco.getPais(), document.get("pais"));
            }
            if(document.get("bairro").equals(endereco2.getBairro())){
                assertEquals(endereco2.getBairro(), document.get("bairro"));
                assertEquals(endereco2.getCep(), document.get("cep"));
                assertEquals(endereco2.getCidade(), document.get("cidade"));
                assertEquals(endereco2.getEnderecoType().name(), document.get("tipoEndereco"));
                assertEquals(endereco2.getEstado(), document.get("estado"));
                assertEquals(endereco2.getLogradouro(), document.get("logradouro"));
                assertEquals(endereco2.getNumero(), document.get("numero"));
                assertEquals(endereco2.getPais(), document.get("pais"));
            }
        }
    }
     
    @Test
    public void deve_atualizar_uma_empresa_de_acordo_com_a_empresa_passada() {
        Set<Endereco> enderecos = new HashSet<Endereco>();
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua José dos Santos2");
        endereco.setNumero(143);
        endereco.setBairro("Jd Flor da Montanha");
        endereco.setCep("07097170");
        endereco.setCidade("Guarulhos");
        endereco.setEstado("SP");
        endereco.setPais("BR");
        endereco.setEnderecoType(EnderecoType.RESIDENCIAL);
        enderecos.add(endereco);
        
        Endereco endereco2 = new Endereco();
        endereco2.setLogradouro("Rua hagsagsuygasyua");
        endereco2.setNumero(1);
        endereco2.setBairro("asuhdiusahdusai");
        endereco2.setCep("07147-850");
        endereco2.setCidade("Gsadsa");
        endereco2.setEstado("SP");
        endereco2.setPais("BR");
        endereco2.setEnderecoType(EnderecoType.COMERCIAL);
        enderecos.add(endereco2);

        Set<Telefone> telefones = new HashSet<Telefone>();
        Telefone telefone = new Telefone();
        telefone.setDdd(11);
        telefone.setTelefone("2459-4064");
        telefone.setTipo(TelefoneType.COMERCIAL);
        telefones.add(telefone);

        Set<Email> emails = new HashSet<Email>();
        Email email = new Email();
        email.setEnderecoDeEmail("caioalberto11@gmail.com");
        emails.add(email);
        
        EmpresaRepository er = new EmpresaRepository("localhost", 27017, "empresa"); 
        empresa.setCnpj("12345678901234");
        empresa.setNomeFantasia("Nome Fantasia LTDA.");
        empresa.setRazaoSocial("Razão Social");
        empresa.setEndereco(enderecos);
        empresa.setTelefone(telefones);
        empresa.setEmails(emails);
        empresa.setDataDeCadastro(DateTime.now().minusMonths(6));
        empresa.setDataDeAlteracao(DateTime.now());
        
        empresa2.setCnpj("12345678901234");
        empresa2.setNomeFantasia("Novo Nome Fantasia");
        empresa2.setRazaoSocial("Razão Social");
        empresa2.setEndereco(enderecos);
        empresa2.setTelefone(telefones);
        empresa2.setEmails(emails);
        empresa2.setDataDeCadastro(DateTime.now().minusMonths(6));
        empresa2.setDataDeAlteracao(DateTime.now());
        
        er.updateOne(empresa2);
    }
}
