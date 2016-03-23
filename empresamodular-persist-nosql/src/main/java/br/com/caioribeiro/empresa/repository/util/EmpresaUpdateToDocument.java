package br.com.caioribeiro.empresa.repository.util;



import static br.com.caioribeiro.empresa.repository.util.EmailAssembler.emailSetToDocument;
import static br.com.caioribeiro.empresa.repository.util.EnderecoAssembler.enderecoListToDocument;
import static br.com.caioribeiro.empresa.repository.util.TelefoneAssembler.telefoneListToDocument;

import org.bson.Document;

import br.com.caioribeiro.empresa.Empresa;

public final class EmpresaUpdateToDocument {
    private EmpresaUpdateToDocument(){};
    
    public static Document empresaUpdateToDocument(Empresa empresa) {
        Document empresaFilterDoc = new Document();
        
        if (empresa.getRazaoSocial() != null) {
            empresaFilterDoc.append("razaoSocial", empresa.getRazaoSocial());
        if (empresa.getNomeFantasia() != null) 
            empresaFilterDoc.append("nomeFantasia", empresa.getNomeFantasia());
        if(empresa.getCnpj() != null)
            empresaFilterDoc.append("cnpj", empresa.getCnpj());
        if(empresa.getEnderecos() != null)
            empresaFilterDoc.append("enderecos", enderecoListToDocument(empresa.getEnderecos()));
        if(empresa.getTelefones() != null)
            empresaFilterDoc.append("telefones", telefoneListToDocument(empresa.getTelefones()));
        if(empresa.getEmails() != null)
            empresaFilterDoc.append("emails", emailSetToDocument(empresa.getEmails())); 
        if(empresa.getDataDeCadastro() != null)        
            empresaFilterDoc.append("dataDeCriacao", empresa.getDataDeCadastro());
        if(empresa.getDataDeAlteracao() != null)
        empresaFilterDoc.append("dataDeAlteracao", empresa.getDataDeAlteracao());
        return empresaFilterDoc;                            
    }
        return null;
    }
}
