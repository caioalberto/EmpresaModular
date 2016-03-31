package br.com.caioribeiro.empresa.assembler.test;

import static br.com.caioribeiro.empresa.assembler.EmailAssembler.documentToEmail;
import static br.com.caioribeiro.empresa.assembler.EmailAssembler.emailListToSet;
import static br.com.caioribeiro.empresa.assembler.EmailAssembler.emailSetToDocument;
import static br.com.caioribeiro.empresa.assembler.EmpresaAssembler.documentToEmpresa;
import static br.com.caioribeiro.empresa.assembler.EmpresaAssembler.documentsToEmpresa;
import static br.com.caioribeiro.empresa.assembler.EmpresaAssembler.empresaToDocument;
import static br.com.caioribeiro.empresa.assembler.EnderecoAssembler.documentToEndereco;
import static br.com.caioribeiro.empresa.assembler.EnderecoAssembler.enderecoToDocument;
import static br.com.caioribeiro.empresa.assembler.EnderecoAssembler.enderecosDocumentToSet;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.Document;
import org.junit.Test;

import br.com.caioribeiro.empresa.Email;
import br.com.caioribeiro.empresa.Empresa;
import br.com.caioribeiro.empresa.Endereco;
import br.com.caioribeiro.empresa.Telefone;
import br.com.caioribeiro.empresa.assembler.EmailAssembler;
import br.com.caioribeiro.empresa.assembler.EnderecoAssembler;

public class AssemblerTest {

    private Empresa empresa;
    private Empresa empresa2;

    private Endereco endereco;
    private Endereco endereco2;

    private Telefone telefone;

    private Email email;

    private Set<Endereco> enderecos = new HashSet<Endereco>();
    private Set<Email> emails = new HashSet<Email>();
    private Set<Telefone> telefones = new HashSet<Telefone>();

   /* @BeforeClass
    public static void setUpBeforeClass() {
        
        loadTemplates("br.com.caioribeiro.empresa.template");
    }
    
    @Before
    public void setUp() {
                
        empresa = from(Empresa.class).gimme("valid");
        
        endereco = from(Endereco.class).gimme("valid");
        endereco2 = from(Endereco.class).gimme("valid");
        
        enderecos.add(endereco);
        enderecos.add(endereco2);
        
        telefone = from(Telefone.class).gimme("valid");
        
        telefones.add(telefone);
        
        email = from(Email.class).gimme("valid");
        
        emails.add(email);
                          
    }*/

    @Test
    public void deve_retornar_se_o_documento_gerado_e_igual_ao_esperado() {
        Document empresaDoc = new Document();
        List<Document> enderecosDoc = (List<Document>) empresaDoc.get("enderecos");
        for(Document document : enderecosDoc) {
            if (document.get("bairro").equals(endereco.getBairro())) {
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
        Document empresaDoc = empresaToDocument(empresa);
        assertEquals(empresaDoc.get("_id"), empresa.getCnpj());
        assertEquals(empresaDoc.get("nomeFantasia"), empresa.getNomeFantasia());
        assertEquals(empresaDoc.get("razaoSocial"), empresa.getRazaoSocial());
        assertEquals(empresaDoc.get("dataDeCriacao"), empresa.getDataDeCadastro());
        assertEquals(empresaDoc.get("dataDeAlteracao"), empresa.getDataDeAlteracao());

        List<Document> enderecosDoc = (List<Document>) empresaDoc.get("enderecos");
        for(Document document : enderecosDoc) {
            if (document.get("bairro").equals(endereco.getBairro())) {
                assertEquals(endereco.getBairro(), document.get("bairro"));
                assertEquals(endereco.getCep(), document.get("cep"));
                assertEquals(endereco.getCidade(), document.get("cidade"));
                assertEquals(endereco.getEnderecoType().name(), document.get("tipoEndereco"));
                assertEquals(endereco.getEstado(), document.get("estado"));
                assertEquals(endereco.getLogradouro(), document.get("logradouro"));
                assertEquals(endereco.getNumero(), document.get("numero"));
                assertEquals(endereco.getPais(), document.get("pais"));
            }
            if (document.get("bairro").equals(endereco2.getBairro())) {
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
    public void nao_deve_aceitar_empresa_nula() {
        empresaToDocument(new Empresa());
    }
    
    @Test
    public void nao_deve_aceitar_documento_de_empresa_nulo() {
        documentToEmpresa(new Document());
    }
    
    @Test
    public void nao_deve_aceitar_lista_de_documentos_de_empresa_nula() {
        documentsToEmpresa(new ArrayList<Document>());
    }
    
    @Test
    public void nao_deve_aceitar_endereco_nulo() {
        enderecoToDocument(new Endereco());
    }
    
    @Test
    public void nao_deve_aceitar_documento_de_endereco_nulo() {
        documentToEndereco(new Document());
    }
    
    @Test
    public void nao_deve_aceitar_lista_de_documentos_de_endereco_nula() {
        enderecosDocumentToSet(new ArrayList<Document>());
    }
    
    @Test
    public void nao_deve_aceitar_um_set_de_enderecos_nulo() {
        EnderecoAssembler.enderecoSetToDocument(new HashSet<Endereco>());
    }
    
    @Test
    public void nao_deve_aceitar_email_nulo() {
        EmailAssembler.toDocument(new Email());
    }
    
    @Test
    public void nao_deve_aceitar_documento_de_email_nulo() {
        documentToEmail(new Document());
    }
    
    @Test
    public void nao_deve_aceitar_lista_de_documentos_de_email_nula() {
        emailListToSet(new ArrayList<Document>());
    }
    
    @Test
    public void nao_deve_aceitar_um_set_de_emails_nulo() {
        emailSetToDocument(new HashSet<Email>());
    }
}
