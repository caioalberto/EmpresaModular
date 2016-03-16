package br.com.caioribeiro.empresa.repository.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bson.Document;

import br.com.caioribeiro.empresa.Telefone;

public final class TelefoneToDocument {
    private TelefoneToDocument() {
    }

    public static Document telefoneToDocument(Telefone telefone) {
        Document telDoc = new Document()
                .append("ddd", telefone.getDdd())
                .append("telefone", telefone.getTelefone())
                .append("tipoTelefone", telefone.getTipo().name());
        return telDoc;
    }

    public static List<Document> telefoneListToDocument(Set<Telefone> telefones) {
        List<Document> telefonesDoc = new ArrayList<Document>();
        if (telefonesDoc != null) {
            for(Telefone telefone : telefones) {
                telefonesDoc.add(telefoneToDocument(telefone));
            }
        }
        return telefonesDoc;
    }
}
