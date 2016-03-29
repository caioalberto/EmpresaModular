package br.com.caioribeiro.empresa.assembler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.Document;

import br.com.caioribeiro.empresa.Telefone;
import br.com.caioribeiro.empresa.TelefoneType;

/**
 * Classe utilitaria que permite realizar o assembler de um objeto do tipo telefone.
 * 
 * @author Caio Ribeiro
 *
 */
public final class TelefoneAssembler {
    private TelefoneAssembler() {
    }

    /**
     * Transforma um telefone em um documento.
     * 
     * @param telefone
     * @return
     */
    public static Document telefoneToDocument(Telefone telefone) {
        if (telefone != null) {
            Document telDoc = new Document().append("ddd", telefone.getDdd()).append("telefone", telefone.getTelefone()).append("tipoTelefone", telefone.getTipo().name());
            return telDoc;
        }
        return null;
    }

    /**
     * Tranforma um documento em um telefone.
     * 
     * @param telDoc
     * @return
     */
    public static Telefone documentToTelefone(Document telDoc) {
        if (telDoc != null) {
            Telefone telefone = new Telefone();
            telefone.setDdd(telDoc.getInteger("ddd"));
            telefone.setTelefone(telDoc.getString("telefone"));
            telefone.setTipo(tipoTelefone(telDoc));
            return telefone;
        }
        return null;
    }

    /**
     * Transforma um set de telefone, numa lista de documentos.
     * 
     * @param telefones
     * @return
     */
    public static List<Document> telefoneListToDocument(Set<Telefone> telefones) {
        List<Document> telefonesDoc = new ArrayList<Document>();
        if (telefonesDoc != null) {
            for(Telefone telefone : telefones) {
                telefonesDoc.add(telefoneToDocument(telefone));
            }
        }
        return telefonesDoc;
    }

    /**
     * Transforma uma lista de documento, num set de telefone.
     * 
     * @param telefonesDoc
     * @return
     */
    public static Set<Telefone> telefoneDocumentToSet(List<Document> telefonesDoc) {
        Set<Telefone> telefones = new HashSet<Telefone>();
        if (telefonesDoc != null) {
            for(Document telDoc : telefonesDoc) {
                telefones.add(documentToTelefone(telDoc));
                return telefones;
            }
        }
        return null;
    }

    /**
     * Recebe um documento, e seta o nome contido no tipo do telefone, transformando este e uma enum.
     * 
     * @param telDoc
     * @return
     */
    private static TelefoneType tipoTelefone(Document telDoc) {
        if (((Document) telDoc).get("tipoTelefone").equals(TelefoneType.valueOf("CELULAR"))) {
            return TelefoneType.CELULAR;
        }
        if (((Document) telDoc).get("tipoTelefone").equals(TelefoneType.valueOf("COMERCIAL"))) {
            return TelefoneType.COMERCIAL;
        }
        return TelefoneType.FAX;
    }
}
