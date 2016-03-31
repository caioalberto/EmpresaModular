package br.com.caioribeiro.empresa.template;

import static br.com.caioribeiro.empresa.EnderecoType.RESIDENCIAL;
import static br.com.caioribeiro.empresa.TelefoneType.FAX;

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
    @Override
    public void load() {
        Fixture.of(Empresa.class).addTemplate("valid", new Rule() {
            {
                add("enderecos", has(2).of(Endereco.class, "valid"));
                add("telefones", has(4).of(Telefone.class, "valid"));
                add("cnpj", cnpj(false));
                add("razaoSocial", random(" Bill e Steve Ltda", "Gates e Jobs Ltda", "Bill Jobs Ltda", "Kaza Arquitetura Ltda", "Xpressi Comércio Exterior Ltda"));
                add("nomeFantasia", random("Petit Pet", "Pet Shop Bichos e Cia", "Instituto de Beleza Paixão", "Microsoft Inc", "Apple Inc", "Casa de Doces"));
                add("emails", has(3).of(Email.class, "valid"));
                add("dataDeCadastro", DateTime.now().minusDays(20));
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
                add("bairro", random("Tatuapé", "Vila Alpina", "Vila Prudente", "Penha", "Itaquera"));
                add("cep", regex("\\d{8}"));
                add("cidade", "São Paulo");
                add("estado", "SP");
                add("logradouro", random("Rua José dos Santos", "Rua Jorge Garcia de Medeiros", "Rua Bela Cintra", "Rua da Consolação", "Rua Augusta"));
                add("numero", random(Integer.class, range(1, 310)));
                add("pais", uniqueRandom("BR"));
                add("tipoEndereco", random(EnderecoType.COMERCIAL, RESIDENCIAL));
            }
        });
        Fixture.of(Telefone.class).addTemplate("valid", new Rule() {
            {
                add("telefone",regex("[2-5]{1}\\d{3}\\d{4}"));
                add("ddd", random(Integer.class, range(11, 99)));
                add("tipo", random(TelefoneType.COMERCIAL, FAX));
            }
        });
        Fixture.of(Email.class).addTemplate("valid", new Rule() {
            {
                add("enderecoDeEmail", random("usuario@dominio.com", "empresa@empresa.com.br"));
            }
        });
        Fixture.of(Telefone.class).addTemplate("celular", new Rule() {
            {
                add("telefone", regex("[9]{1}[5-9]{1}\\d{3}\\d{4}"));
                add("ddd", random(Integer.class, range(11, 99)));
                add("tipo", TelefoneType.CELULAR);
            }
        });
    }

}
