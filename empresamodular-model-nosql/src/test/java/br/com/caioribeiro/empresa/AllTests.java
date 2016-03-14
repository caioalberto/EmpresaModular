package br.com.caioribeiro.empresa;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author Caio Ribeiro
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ EmpresaTest.class, EnderecoTest.class, TelefoneTest.class })
public class AllTests {

}
