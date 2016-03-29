package br.com.caioribeiro.empresa.repository.util;

import java.util.ArrayList;
import java.util.List;

import br.com.caioribeiro.empresa.Empresa;

public final class EmpresaProjection {

    private EmpresaProjection() {
    };

    public static List<String> createEmpresaProjectionFields(Empresa empresaProj) {
        List<String> fieldsProj = new ArrayList<String>();

        if (empresaProj.getCnpj() != null) {
            fieldsProj.add("cnpj");
        }

        if (empresaProj.getNomeFantasia() != null) {
            fieldsProj.add("nomeFantasia");
        }

        if (empresaProj.getRazaoSocial() != null) {
            fieldsProj.add("razaoSocial");
        }
        
        if(empresaProj.getEmails() != null) {
            fieldsProj.add("emails");
        }
        
        if(empresaProj.getEnderecos() != null) {
            fieldsProj.add("enderecos");
        }
        
        if(empresaProj.getTelefones() != null) {
            fieldsProj.add("telefones");
        }
        
        return fieldsProj;
    }

}
