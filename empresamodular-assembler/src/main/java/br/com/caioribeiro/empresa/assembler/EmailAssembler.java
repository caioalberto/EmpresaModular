package br.com.caioribeiro.empresa.assembler;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.Document;

import br.com.caioribeiro.empresa.Email;

/**
 * @author Caio Ribeiro
 *
 */
public final class EmailAssembler {
    private EmailAssembler() {
    }

    /**
     * Transforma um email em um document.
     * 
     * @param email
     * @return
     */
    public static Document toDocument(Email email) {
        checkArgument(email.equals(null), "Os documentos de email não podem ser nulos!");
        Document emailDoc = new Document().append("enderecoEmail", email.getEnderecoDeEmail());
        return emailDoc;
    }

    /**
     * Transforma um document em um email.
     * 
     * @param emailDoc
     * @return
     */
    public static Email documentToEmail(Document emailDoc) {
        checkArgument(emailDoc.equals(null), "Os documentos de email não podem ser nulos!");
        Email email = new Email();
        email.setEnderecoDeEmail(emailDoc.getString("enderecoEmail"));
        return email;
    }

    /**
     * Transforma um set de email numa lista de document.
     * 
     * @param emails
     * @return
     */
    public static List<Document> emailSetToDocument(Set<Email> emails) {
        checkArgument(emails.equals(null), "Os emails nao podem ser nulos!");
        List<Document> emailsDoc = new ArrayList<Document>();
        for(Email email : emails) {
            emailsDoc.add(toDocument(email));
        }
        return emailsDoc;
    }

    /**
     * transforma uma lista de document num set de email.
     * 
     * @param emailsDoc
     * @return
     */
    public static Set<Email> emailListToSet(List<Document> emailsDoc) {
        checkArgument(emailsDoc.equals(null), "Os documentos de email não podem ser nulos!");
        Set<Email> emails = new HashSet<Email>();
        for(Document emailDoc : emailsDoc) {
            emails.add(documentToEmail(emailDoc));
        }
        return emails;
    }
}
