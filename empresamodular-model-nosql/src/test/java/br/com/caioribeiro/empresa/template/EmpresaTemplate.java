package br.com.caioribeiro.empresa.template;

import static br.com.caioribeiro.empresa.EnderecoType.RESIDENCIAL;
import static br.com.caioribeiro.empresa.TelefoneType.FAX;
import static br.com.caioribeiro.empresa.util.PatternType.CELULAR_REGEX;
import static br.com.caioribeiro.empresa.util.PatternType.CEP_REGEX;
import static br.com.caioribeiro.empresa.util.PatternType.TELEFONE_FIXO_REGEX;

import org.joda.time.DateTime;

import br.com.caioribeiro.empresa.Email;
import br.com.caioribeiro.empresa.Empresa;
import br.com.caioribeiro.empresa.Endereco;
import br.com.caioribeiro.empresa.EnderecoType;
import br.com.caioribeiro.empresa.Telefone;
import br.com.caioribeiro.empresa.TelefoneType;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

/**
 * @author Caio Ribeiro
 *
 */
public class EmpresaTemplate implements TemplateLoader {
    
    public void load() {
        Fixture.of(Empresa.class).addTemplate("valid", new Rule() {
            {
                add("enderecos", has(2).of(Endereco.class, "valid"));
                add("telefones", has(4).of(Telefone.class, "valid"));
                add("cnpj", cnpj(true));
                add("razaoSocial", regex("\\w{20}"));
                add("nomeFantasia", regex("\\w{20}"));
                add("emails", has(3).of(Email.class, "valid"));
                add("dataDeCadastro", new DateTime().minusDays(20));
                add("dataDeAlteracao", DateTime.now());
            }
        });
        Fixture.of(Empresa.class).addTemplate("empresa_com_celular").inherits("valid", new Rule() {
            {
                add("telefones", has(4).of(Telefone.class, "celular"));
            }
        });
        Fixture.of(Endereco.class).addTemplate("valid", new Rule() {
            {
                add("bairro", regex("\\w{20}"));
                add("cep", CEP_REGEX);
                add("cidade", "São Paulo");
                add("estado", "SP");
                add("logradouro", regex("\\w{20}"));
                add("numero", random(Integer.class, range(1, 9999)));
                add("pais", "Brasil");
                add("tipoEndereco", random(EnderecoType.COMERCIAL, RESIDENCIAL));
            }
        });
        Fixture.of(Telefone.class).addTemplate("valid", new Rule() {
            {
                add("telefone", TELEFONE_FIXO_REGEX);
                add("ddd", random(Integer.class, range(11, 99)));
                add("tipo", random(TelefoneType.COMERCIAL, FAX));
            }
        });
        Fixture.of(Email.class).addTemplate("valid", new Rule() {
            {
                add("enderecoDeEmail", regex("\\w{4}\\@\\w{3}\\.\\w{3}"));
            }
        });
        Fixture.of(Telefone.class).addTemplate("celular", new Rule() {
            {
                add("telefone", CELULAR_REGEX);
                add("ddd", random(Integer.class, range(11, 99)));
                add("tipo", TelefoneType.CELULAR);
            }
        });
    }

}
