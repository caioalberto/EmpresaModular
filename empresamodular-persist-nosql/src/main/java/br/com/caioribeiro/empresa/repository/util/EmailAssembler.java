package br.com.caioribeiro.empresa.repository.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.Document;

import br.com.caioribeiro.empresa.Email;

public final class EmailAssembler {
    private EmailAssembler() {
    }

    public static Document toDocument(Email email) {
        if (email != null) {
            Document emailDoc = new Document().append("enderecoEmail", email.getEnderecoDeEmail());
            return emailDoc;
        }
        return null;
    }
    
    public static Email documentToEmail(Document emailDoc) {
        Email email = new Email();
        if(emailDoc != null) {
            email.setEnderecoDeEmail(emailDoc.getString("enderecoEmail"));
            return email;
        }
        return null;
    }

    public static List<Document> emailSetToDocument(Set<Email> emails) {
        List<Document> emailsDoc = new ArrayList<Document>();
        if (emails != null) {
            for(Email email : emails) {
                emailsDoc.add(toDocument(email));
                return emailsDoc;
            }
        }
        return null;
    }
    
    public static Set<Email> emailListToSet(List<Document> emailsDoc) {
        Set<Email> emails = new HashSet<Email>();
        if (emailsDoc != null) {
            for(Document emailDoc : emailsDoc) {
                emails.add(documentToEmail(emailDoc));
                return emails;
            }
        }
        return null;
    }
}
