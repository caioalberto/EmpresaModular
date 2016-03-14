package br.com.caioribeiro.empresa.repository.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bson.Document;

import br.com.caioribeiro.empresa.Endereco;

public final class EnderecoToDocument {
    
    private EnderecoToDocument(){}
    
    public static Document enderecoToDocument(Endereco endereco) {
        Document enderecoDoc = new Document()
                .append("logradouro", endereco.getLogradouro())
                .append("numero", endereco.getNumero())
                .append("bairro", endereco.getBairro())
                .append("cep", endereco.getCep())
                .append("cidade", endereco.getCidade())
                .append("estado", endereco.getEstado())
                .append("pais", endereco.getPais())
                .append("tipoEndereco", endereco.getEnderecoType().name());                
        return enderecoDoc;
    }
    
    public static List<Document> enderecoToDocument(Set<Endereco> enderecos) {
        List<Document> enderecosDoc = new ArrayList<Document>();        
        for(Endereco endereco : enderecos) {
            enderecosDoc.add(enderecoToDocument(endereco));
        }        
        return enderecosDoc;        
    }
}
