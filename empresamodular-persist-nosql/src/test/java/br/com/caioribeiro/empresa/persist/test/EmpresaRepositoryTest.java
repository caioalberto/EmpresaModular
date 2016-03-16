package br.com.caioribeiro.empresa.persist.test;

import static br.com.caioribeiro.empresa.repository.util.EmailsToDocument.emailListToDocument;
import static br.com.caioribeiro.empresa.repository.util.EmailsToDocument.toDocument;
import static br.com.caioribeiro.empresa.repository.util.EmpresaToDocument.empresaToDocument;
import static br.com.caioribeiro.empresa.repository.util.EnderecoToDocument.enderecoListToDocument;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;

import java.util.HashSet;
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
import br.com.caioribeiro.empresa.repository.util.EmailsToDocument;
import br.com.caioribeiro.empresa.repository.util.EnderecoToDocument;
import junit.framework.Assert;

public class EmpresaRepositoryTest {
    
    /** The empresa. */
    private Empresa empresa = new Empresa();
    
  EmpresaRepository er = new EmpresaRepository("localhost", 27017, "empresa"); 
    
    @Test
    public void test() {
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
        er.save(empresaToDocument(empresa));
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
        
        for(Endereco end : enderecos) {
           System.out.println(end.getBairro() == empresaDoc.get("enderecos"));
        }
    }
    

}
