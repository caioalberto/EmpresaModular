package br.com.caioribeiro.empresa.persist.test;

import static br.com.six2six.fixturefactory.Fixture.from;
import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import br.com.caioribeiro.empresa.Email;
import br.com.caioribeiro.empresa.Empresa;
import br.com.caioribeiro.empresa.Endereco;
import br.com.caioribeiro.empresa.EnderecoType;
import br.com.caioribeiro.empresa.Telefone;
import br.com.caioribeiro.empresa.repository.EmpresaRepository;
import br.com.caioribeiro.empresa.repository.util.EmpresaToDocument;
import br.com.caioribeiro.empresa.repository.util.EnderecoToDocument;

public class EmpresaTest {
    
    /** The empresa. */
    private Empresa empresa = new Empresa();
    
    private Set<Email> emails = new HashSet();
    
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

        
        EmpresaRepository er = new EmpresaRepository("localhost", 27017, "empresa"); 
        empresa.setCnpj("12345678901234");
        empresa.setNomeFantasia("Nome Fantasia LTDA.");
        empresa.setRazaoSocial("Razão Social");
        empresa.setEndereco(enderecos);
        //empresa.setEmails(emails);
        //empresa.setTelefone(telefones);
        //empresa.setDataDeAlteracao(DateTime.now());
        //empresa.setDataDeCadastro(DateTime.now());
        er.save(EmpresaToDocument.toDocument(empresa));
    }

}
