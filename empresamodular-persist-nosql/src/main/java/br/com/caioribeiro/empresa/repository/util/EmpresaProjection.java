package br.com.caioribeiro.empresa.repository.util;

import java.util.ArrayList;
import java.util.List;

import br.com.caioribeiro.empresa.Empresa;

public final class EmpresaProjection {
    private EmpresaProjection(){};
    
    public static List<String> createEmpresaProjectionFields(Empresa empresaProj) {
        List<String> fieldsProj = new ArrayList<String>();
        if (empresaProj.getCnpj().length() != 0) {
            fieldsProj.add("cnpj");
            if (empresaProj.getNomeFantasia().length() != 0)
                fieldsProj.add("nomeFantasia");
            if (empresaProj.getRazaoSocial().length() != 0)
                fieldsProj.add("razaoSocial");
        }
        return fieldsProj;
    }

}
