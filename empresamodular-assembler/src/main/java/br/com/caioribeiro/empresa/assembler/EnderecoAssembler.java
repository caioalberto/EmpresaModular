package br.com.caioribeiro.empresa.assembler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.Document;

import br.com.caioribeiro.empresa.Endereco;
import br.com.caioribeiro.empresa.EnderecoType;

/**
 * Classe utilitaria que permite realizar o assembler de um objeto do tipo endereco.
 * 
 * @author Caio Ribeiro
 *
 */
public final class EnderecoAssembler {

    private EnderecoAssembler() {
    }

    /**
     * Transforma um endereco em um document.
     * 
     * @param endereco
     * @return
     */
    public static Document enderecoToDocument(Endereco endereco) {
        Document enderecoDoc = new Document().append("logradouro", endereco.getLogradouro()).append("numero", endereco.getNumero()).append("bairro", endereco.getBairro())
                .append("cep", endereco.getCep()).append("cidade", endereco.getCidade()).append("estado", endereco.getEstado()).append("pais", endereco.getPais())
                .append("tipoEndereco", endereco.getEnderecoType().name());
        return enderecoDoc;
    }

    /**
     * Transforma um document em um endereco.
     * 
     * @param enderecoDoc
     * @return
     */
    public static Endereco documentToEndereco(Document enderecoDoc) {
        Endereco endereco = new Endereco();
        endereco.setLogradouro(enderecoDoc.getString("logradouro"));
        endereco.setNumero(enderecoDoc.getInteger("numero"));
        endereco.setBairro(enderecoDoc.getString("bairro"));
        endereco.setCep(enderecoDoc.getString("cep"));
        endereco.setCidade(enderecoDoc.getString("cidade"));
        endereco.setEstado(enderecoDoc.getString("estado"));
        endereco.setPais(enderecoDoc.getString("pais"));
        endereco.setEnderecoType(tipoEndereco(enderecoDoc));
        return endereco;
    }

    /**
     * Transforma um set de enderecos numa lista de documents.
     * 
     * @param enderecos
     * @return
     */
    public static List<Document> enderecoListToDocument(Set<Endereco> enderecos) {
        List<Document> enderecosDoc = new ArrayList<Document>();
        if (enderecos != null) {
            for(Endereco endereco : enderecos) {
                enderecosDoc.add(enderecoToDocument(endereco));
                return enderecosDoc;
            }
        }
        return null;
    }

    /**
     * Transforma uma lista de documentos, num set de telefones.
     * 
     * @param enderecosDoc
     * @return
     */
    public static Set<Endereco> enderecosDocumentToSet(List<Document> enderecosDoc) {
        Set<Endereco> enderecos = new HashSet<Endereco>();
        if (enderecosDoc != null) {
            for(Document endDoc : enderecosDoc) {
                enderecos.add(documentToEndereco(endDoc));
                return enderecos;
            }
        }
        return null;
    }

    /**
     * Recebe um enderco, e seta o nome contido no tipo do telefone, transformando este e uma enum.
     * 
     * @param enderecoDoc
     * @return
     */
    private static EnderecoType tipoEndereco(Document enderecoDoc) {
        if (((Document) enderecoDoc).get("tipoEndereco").equals(EnderecoType.valueOf("RESIDENCIAL"))) {
            return EnderecoType.RESIDENCIAL;
        }
        return EnderecoType.COMERCIAL;
    }
}
