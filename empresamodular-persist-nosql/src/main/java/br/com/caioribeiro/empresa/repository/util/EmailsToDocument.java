package br.com.caioribeiro.empresa.repository.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bson.Document;

import br.com.caioribeiro.empresa.Email;

public final class EmailsToDocument {
    private EmailsToDocument() {
    }

    public static Document toDocument(Email email) {
        Document emailDoc = new Document().append("enderecoEmail", email.getEnderecoDeEmail());
        return emailDoc;
    }

    public static List<Document> emailListToDocument(Set<Email> emails) {
        List<Document> emailsDoc = new ArrayList<Document>();
        if (emails != null) {
            for(Email email : emails) {
                emailsDoc.add(toDocument(email));
            }
        }
        return emailsDoc;
    }
}
