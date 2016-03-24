package br.com.caioribeiro.empresa.repository.util;

import static br.com.caioribeiro.empresa.repository.util.EmailAssembler.emailListToSet;
import static br.com.caioribeiro.empresa.repository.util.EmailAssembler.emailSetToDocument;
import static br.com.caioribeiro.empresa.repository.util.EnderecoAssembler.enderecoListToDocument;
import static br.com.caioribeiro.empresa.repository.util.EnderecoAssembler.enderecosDocumentToSet;
import static br.com.caioribeiro.empresa.repository.util.TelefoneAssembler.telefoneDocumentToSet;
import static br.com.caioribeiro.empresa.repository.util.TelefoneAssembler.telefoneListToDocument;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.joda.time.DateTime;

import br.com.caioribeiro.empresa.Empresa;

public final class EmpresaAssembler {
    private EmpresaAssembler() {
    };
        
    public static Document empresaToDocument(Empresa empresa) {
        if (empresa != null) {
            Document empresaDoc = new Document()
                    .append("razaoSocial", empresa.getRazaoSocial())
                    .append("nomeFantasia", empresa.getNomeFantasia())
                    .append("_id", empresa.getCnpj())
                    .append("enderecos", enderecoListToDocument(empresa.getEnderecos()))
                    .append("telefones", telefoneListToDocument(empresa.getTelefones()))
                    .append("emails", emailSetToDocument(empresa.getEmails()))
                    .append("dataDeCriacao", empresa.getDataDeCadastro())
                    .append("dataDeAlteracao", empresa.getDataDeAlteracao());

            return empresaDoc;
        }
        return null;
    }
    
    public static List<Document> empresasToDocument(Empresa empresa, Empresa outra) {
        List<Empresa> empresas = new ArrayList<Empresa>();
        List<Document> empresasDoc = new ArrayList<Document>();
        empresas.add(empresa);
        empresas.add(outra);
        for(Empresa empDoc : empresas) {
            empresasDoc.add(empresaToDocument(empDoc));
        }
        return empresasDoc;
    }
    
    public static Empresa documentToEmpresa(Document empresaDoc) {
        Empresa empresa = new Empresa();       
        
            empresa.setCnpj(empresaDoc.getString("_id"));
            empresa.setRazaoSocial(empresaDoc.getString("razaoSocial"));
            empresa.setNomeFantasia(empresaDoc.getString("nomeFantasia"));
            empresa.setEndereco(enderecosDocumentToSet((List<Document>) empresaDoc.get("enderecos")));
            empresa.setTelefone(telefoneDocumentToSet((List<Document>) empresaDoc.get("telefones")));
            empresa.setEmails(emailListToSet((List<Document>) empresaDoc.get("emails")));
            empresa.setDataDeCadastro((DateTime) empresaDoc.get("dataDeCriacao"));
            empresa.setDataDeAlteracao((DateTime) empresaDoc.get("dataDeAlteracao"));
            empresa.toString();
        return empresa;
    }
    
    public static List<Empresa> documentsToEmpresa(List<Document> empresasDoc) {
        List<Empresa> empresas = new ArrayList<Empresa>();
        if (empresasDoc != null) {
            for(Document empresaDoc : empresasDoc) {
                empresas.add(documentToEmpresa(empresaDoc));
                return empresas;
            }
        }
        return null;
    }
    
}
