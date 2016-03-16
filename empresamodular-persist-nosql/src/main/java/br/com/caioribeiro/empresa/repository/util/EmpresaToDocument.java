package br.com.caioribeiro.empresa.repository.util;

import static br.com.caioribeiro.empresa.repository.util.EmailsToDocument.emailListToDocument;
import static br.com.caioribeiro.empresa.repository.util.EnderecoToDocument.enderecoListToDocument;
import static br.com.caioribeiro.empresa.repository.util.TelefoneToDocument.telefoneListToDocument;

import org.bson.Document;

import br.com.caioribeiro.empresa.Empresa;

public final class EmpresaToDocument {
    private EmpresaToDocument(){};
        
    public static Document empresaToDocument(Empresa empresa) {
        if (empresa != null) {
            Document empresaDoc = new Document()
                    .append("razaoSocial", empresa.getRazaoSocial())
                    .append("nomeFantasia", empresa.getNomeFantasia())
                    .append("cnpj", empresa.getCnpj())
                    .append("enderecos", enderecoListToDocument(empresa.getEnderecos()))
                    .append("telefones", telefoneListToDocument(empresa.getTelefones()))
                    .append("emails", emailListToDocument(empresa.getEmails()))
                    .append("dataDeCriacao", empresa.getDataDeCadastro().toDate())
                    .append("dataDeAlteracao", empresa.getDataDeAlteracao().toDate());

            return empresaDoc;
        }
        return null;
    }
    
    
}
