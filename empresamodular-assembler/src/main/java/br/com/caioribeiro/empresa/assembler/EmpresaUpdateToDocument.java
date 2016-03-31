package br.com.caioribeiro.empresa.assembler;

import static br.com.caioribeiro.empresa.assembler.EmailAssembler.emailSetToDocument;
import static br.com.caioribeiro.empresa.assembler.EnderecoAssembler.enderecoSetToDocument;
import static br.com.caioribeiro.empresa.assembler.TelefoneAssembler.telefoneListToDocument;

import org.bson.Document;

import br.com.caioribeiro.empresa.Empresa;

/**
 * Classe utilitaria pra a geracao de documents para update.
 * 
 * @author Caio Ribeiro
 *
 */
public final class EmpresaUpdateToDocument {
    private EmpresaUpdateToDocument() {
    };

    /**
     * Metodo que transforma um objeto do tipo empresa, num objeto do tipo document.
     * 
     * @param empresa
     * @return
     */
    public static Document empresaUpdateToDocument(Empresa empresa) {
        Document empresaFilterDoc = new Document();

        if (empresa.getRazaoSocial() != null) {
            empresaFilterDoc.append("razaoSocial", empresa.getRazaoSocial());
            if (empresa.getNomeFantasia() != null)
                empresaFilterDoc.append("nomeFantasia", empresa.getNomeFantasia());
            if (empresa.getCnpj() != null)
                empresaFilterDoc.append("cnpj", empresa.getCnpj());
            if (empresa.getEnderecos() != null)
                empresaFilterDoc.append("enderecos", enderecoSetToDocument(empresa.getEnderecos()));
            if (empresa.getTelefones() != null)
                empresaFilterDoc.append("telefones", telefoneListToDocument(empresa.getTelefones()));
            if (empresa.getEmails() != null)
                empresaFilterDoc.append("emails", emailSetToDocument(empresa.getEmails()));
            if (empresa.getDataDeCadastro() != null)
                empresaFilterDoc.append("dataDeCriacao", empresa.getDataDeCadastro());
            if (empresa.getDataDeAlteracao() != null)
                empresaFilterDoc.append("dataDeAlteracao", empresa.getDataDeAlteracao());
            return empresaFilterDoc;
        }
        return null;
    }
}
