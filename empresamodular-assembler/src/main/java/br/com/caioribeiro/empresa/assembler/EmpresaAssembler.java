package br.com.caioribeiro.empresa.assembler;

import static br.com.caioribeiro.empresa.assembler.EmailAssembler.emailListToSet;
import static br.com.caioribeiro.empresa.assembler.EmailAssembler.emailSetToDocument;
import static br.com.caioribeiro.empresa.assembler.EnderecoAssembler.enderecoListToDocument;
import static br.com.caioribeiro.empresa.assembler.EnderecoAssembler.enderecosDocumentToSet;
import static br.com.caioribeiro.empresa.assembler.TelefoneAssembler.telefoneDocumentToSet;
import static br.com.caioribeiro.empresa.assembler.TelefoneAssembler.telefoneListToDocument;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.joda.time.DateTime;

import br.com.caioribeiro.empresa.Empresa;

/**
 * Classe utilitaria que permite realizar o assembler de um objeto do tipo empresa.
 * 
 * @author Caio Ribeiro
 *
 */
public final class EmpresaAssembler {
    private EmpresaAssembler() {
    };

    /**
     * Tranforma uma empresa em um documento.
     * 
     * @param empresa
     * @return
     */
    public static Document empresaToDocument(Empresa empresa) {
        if (empresa != null) {
            Document empresaDoc = new Document().append("razaoSocial", empresa.getRazaoSocial()).append("nomeFantasia", empresa.getNomeFantasia()).append("_id", empresa.getCnpj())
                    .append("enderecos", enderecoListToDocument(empresa.getEnderecos())).append("telefones", telefoneListToDocument(empresa.getTelefones()))
                    .append("emails", emailSetToDocument(empresa.getEmails())).append("dataDeCriacao", empresa.getDataDeCadastro()).append("dataDeAlteracao", empresa.getDataDeAlteracao());

            return empresaDoc;
        }
        return null;
    }

    /**
     * Tranforma duas empresas em um documento.
     * 
     * @param empresa
     * @param outra
     * @return
     */
    public static List<Document> empresasToDocument(Empresa empresa, Empresa outra) {
        List<Empresa> empresas = new ArrayList<Empresa>();
        List<Document> empresasDoc = new ArrayList<Document>();
        empresas.add(empresa);
        empresas.add(outra);
        for(Empresa empDoc : empresas) {
            empresasDoc.add(empresaToDocument(empDoc));
        }
        return empresasDoc;
    }

    /**
     * Transforma um objeto document em uma empresa.
     * 
     * @param empresaDoc
     * @return
     */
    public static Empresa documentToEmpresa(Document empresaDoc) {
        Empresa empresa = new Empresa();

        empresa.setCnpj(empresaDoc.getString("_id"));
        empresa.setRazaoSocial(empresaDoc.getString("razaoSocial"));
        empresa.setNomeFantasia(empresaDoc.getString("nomeFantasia"));
        empresa.setEndereco(enderecosDocumentToSet((List<Document>) empresaDoc.get("enderecos")));
        empresa.setTelefone(telefoneDocumentToSet((List<Document>) empresaDoc.get("telefones")));
        empresa.setEmails(emailListToSet((List<Document>) empresaDoc.get("emails")));
        empresa.setDataDeCadastro((DateTime) empresaDoc.get("dataDeCriacao"));
        empresa.setDataDeAlteracao((DateTime) empresaDoc.get("dataDeAlteracao"));
        empresa.toString();
        return empresa;
    }

    /**
     * Transforma uma lista de documentos, em uma lista de empresas.
     * 
     * @param empresasDoc
     * @return
     */
    public static List<Empresa> documentsToEmpresa(List<Document> empresasDoc) {
        List<Empresa> empresas = new ArrayList<Empresa>();
        if (empresasDoc != null) {
            for(Document empresaDoc : empresasDoc) {
                empresas.add(documentToEmpresa(empresaDoc));
                return empresas;
            }
        }
        return null;
    }

}
