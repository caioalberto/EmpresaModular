package br.com.caioribeiro.empresa.repository.util;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import br.com.caioribeiro.empresa.Empresa;
import br.com.caioribeiro.empresa.Endereco;

public final class EmpresaToDocument {
    private EmpresaToDocument(){};
    
    public static Document toDocument(Empresa empresa) {
       Document empresaDoc = new Document()
               .append("razaoSocial", empresa.getRazaoSocial())
               .append("nomeFantasia", empresa.getNomeFantasia())
               .append("cnpj", empresa.getCnpj())
               .append("enderecos", EnderecoToDocument.enderecoToDocument(empresa.getEnderecos()));
               //.append("telefones", empresa.getTelefones().toArray().toString())
               //.append("emails", empresa.getEmails().toArray())     
               //.append("dataDeCriacao", empresa.getDataDeCadastro().toDateTimeISO())
               //.append("dataDeAltecarao", empresa.getDataDeAlteracao().toDateTimeISO());
        
        return empresaDoc;        
    }
    
}
