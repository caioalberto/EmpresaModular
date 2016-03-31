package br.com.caioribeiro.empresa.repository;

import static br.com.caioribeiro.empresa.assembler.EmpresaAssembler.documentToEmpresa;
import static br.com.caioribeiro.empresa.assembler.EmpresaAssembler.documentsToEmpresa;
import static br.com.caioribeiro.empresa.assembler.EmpresaAssembler.empresaToDocument;
import static br.com.caioribeiro.empresa.assembler.EmpresaUpdateToDocument.empresaUpdateToDocument;
import static br.com.caioribeiro.empresa.repository.util.MongoCodecs.isoDateToJoda;
import static br.com.caioribeiro.empresa.repository.util.MongoCodecs.jodaToIsoDate;
import static br.com.caioribeiro.empresa.util.ValidadorUtil.containsError;
import static br.com.caioribeiro.empresa.util.ValidadorUtil.errorMessages;
import static com.google.common.base.Preconditions.checkArgument;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;

import com.mongodb.MongoException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caioribeiro.empresa.Empresa;

/**
 * Classe utilitaria que realiza a conexao com o MongoDB, e executa acoees de CRUD.
 * 
 * @author Caio Ribeiro
 *
 */
public class EmpresaRepository {

    private static final int TAMANHO_DE_ERROS = 0;
    private String host = "localhost";
    private Integer port = 27017;
    private String database;
    private MongoClient mongoClient;
    public static final String COLLECTION = "empresa";

    public EmpresaRepository(String host, Integer port, String database) {
        this.host = host;
        this.port = port;
        this.database = database;
    }


    /**
     * Salva apenas uma instancia de empresa no banco.
     * 
     * @param empresa
     * @throws MongoException
     */
    public void saveOne(Empresa empresa) throws MongoException {
        this.validarEmpresa(empresa);
        try {
            this.mongoClient = new MongoClient(this.host + ":" + this.port, jodaToIsoDate());
            MongoDatabase database = this.mongoClient.getDatabase(this.database);
            MongoCollection<Document> collection = database.getCollection(COLLECTION);
            Document empresaDoc = empresaToDocument(empresa);
            collection.insertOne(empresaDoc);
        } catch (MongoWriteException mwe) {
            throw new IllegalStateException("Esta empresa já está inserida no banco! Favor insira outra!");
        } finally {
            mongoClient.close();
        }
    }
    
    /**
     * Deleta um objeto do tipo empresa do banco, de acordo com o cnpj.
     * 
     * @param cnpj
     * @throws MongoWriteException
     */
    public void deleteOne(String cnpj) throws MongoWriteException {
        this.validateCnpj(cnpj);
        try {
            this.mongoClient = new MongoClient(this.host + ":" + this.port, jodaToIsoDate());
            MongoDatabase database = this.mongoClient.getDatabase(this.database);
            MongoCollection<Document> collection = database.getCollection(COLLECTION);
            collection.deleteOne(new Document("_id", cnpj));
        } finally {
            mongoClient.close();
        }
    }

    /**
     * Deleta todos os objetos do banco.
     * 
     * @param empresaFilter
     * @throws MongoWriteException
     */
    public void deleteVarious(Empresa empresaFiltro) throws MongoWriteException {
        try {
            Document empresaFiltroDoc = empresaUpdateToDocument(empresaFiltro);
            this.mongoClient = new MongoClient(this.host + ":" + this.port, jodaToIsoDate());
            MongoDatabase database = this.mongoClient.getDatabase(this.database);
            MongoCollection<Document> collection = database.getCollection(COLLECTION);
            collection.deleteMany(new Document(empresaFiltroDoc));
        } finally {
            mongoClient.close();
        }
    }

    /**
     * Retorna todas as empresas do banco, de acordo com o cnpj digitado.
     * 
     * @param cnpj
     * @return
     */
    public List<Empresa> find(String cnpj) {
        List<Empresa> empresas = new ArrayList<Empresa>();
        List<Document> empresasDoc = new ArrayList<Document>();
        this.validateCnpj(cnpj);
        try {
            this.mongoClient = new MongoClient(this.host + ":" + this.port, isoDateToJoda());
            MongoDatabase database = this.mongoClient.getDatabase(this.database);
            MongoCollection<Document> collection = database.getCollection(COLLECTION);
            FindIterable<Document> find = collection.find(new Document("_id", cnpj));
            empresasDoc = findForEach(find);
            empresas.addAll(documentsToEmpresa(empresasDoc));
        } finally {
            mongoClient.close();
        }

        return empresas;
    }

    /**
     * Encontra no banco um objeto do tipo empresa, de acordo com a empresa filtro.
     * 
     * @param empresaFilter
     * @param empresa
     * @return
     */
    public List<Empresa> findByFields(Empresa empresaFilter) {
        List<Empresa> empresas = new ArrayList<Empresa>();
        try {
            Document empresaDoc = empresaUpdateToDocument(empresaFilter);
            this.mongoClient = new MongoClient(this.host + ":" + this.port, isoDateToJoda());
            MongoDatabase database = this.mongoClient.getDatabase(this.database);
            MongoCollection<Document> collection = database.getCollection(COLLECTION);
            FindIterable<Document> find = collection.find(new Document(empresaDoc)).sort(new Document("_id", 1));
            List<Document> empresasDoc = findForEach(find);
            for(Document document : empresasDoc) {
                empresas.add(documentToEmpresa(document));
            }
        } finally {
            mongoClient.close();
        }
        return empresas;
    }

    /**
     * Metodo que retorna o cnpj da primeira instancia de empresa encontrada, utilizado para comparacao de empresa.
     * 
     * @return
     */
    public String findObjectAndReturnString() {
        List<Empresa> empresas = new ArrayList<Empresa>();
        String cnpj = null;
        try {
            this.mongoClient = new MongoClient(this.host + ":" + this.port, isoDateToJoda());
            MongoDatabase database = this.mongoClient.getDatabase(this.database);
            MongoCollection<Document> collection = database.getCollection(COLLECTION);
            FindIterable<Document> find = collection.find().projection(new Document()).limit(20);
            List<Document> empresasDoc = findForEach(find);
            for(Document document : empresasDoc) {
                empresas.add(documentToEmpresa(document));
                cnpj = document.getString("_id");
            }
        } finally {
            mongoClient.close();
        }
        return cnpj;
    }

    /**
     * Localiza no banco, um objeto do tipo empresa, de acordo com os campos especificados no filtro.
     * 
     * @param empresaFilter
     * @return
     */
    public List<Empresa> findBySpecifiedFields(Empresa empresaFilter) {
        List<Empresa> empresas = new ArrayList<Empresa>();
        try {
            Document empresaFilterDoc = empresaUpdateToDocument(empresaFilter);
            this.mongoClient = new MongoClient(this.host + ":" + this.port, isoDateToJoda());
            MongoDatabase database = this.mongoClient.getDatabase(this.database);
            List<String> projKeys = projectionsInclude(empresaFilterDoc);
            MongoCollection<Document> collection = database.getCollection(COLLECTION);
            FindIterable<Document> find = collection.find(empresaFilterDoc).limit(30).sort(new Document("_id", 1)).projection(fields(include(projKeys)));
            List<Document> empresasDoc = findForEach(find);
            for(Document document : empresasDoc) {
                empresas.add(documentToEmpresa(document));
            }
        } finally {
            mongoClient.close();
        }
        return empresas;
    }

    /**
     * Metodo de pesquisa por paginacao, que recebe um filtro de empresa, a quantidade de paginas e a quantidade de empresas que deve ser pesquisada, e retorna a pesquisa ordenada por numero de cnpj.
     * 
     * @param empresa
     * @param qtdPaginas
     * @param qtdEmpresas
     * @return
     */
    public List<Empresa> findByNumberAndQuantity(Empresa empresa, Integer qtdPaginas, Integer qtdEmpresas) {
        List<Empresa> empresas = new ArrayList<Empresa>();
        try {
            this.mongoClient = new MongoClient(this.host + ":" + this.port, isoDateToJoda());
            MongoDatabase database = this.mongoClient.getDatabase(this.database);
            MongoCollection<Document> collection = database.getCollection(COLLECTION);
            FindIterable<Document> find = collection.find().limit(qtdEmpresas * (qtdPaginas - 1)).skip(qtdPaginas).sort(new Document("_id", 1));
            List<Document> empresasDoc = findForEach(find);
            for(Document document : empresasDoc) {
                empresas.add(documentToEmpresa(document));
            }
            List<String> cnpjs = catchCnpj(find);
            for(int i = 0 ; i < empresas.size() ; i++) {
                if (cnpjs.equals(empresas.get(i).getCnpj())) {
                    throw new IllegalArgumentException("Já existe uma empresa com esse cnpj em outra página!");
                }
            }
        } finally {
            mongoClient.close();
        }
        return empresas;
    }
    
    /**
     * Atualiza todos objetos do tipo empresa, de acordo com o campo chave digitado, e seta o novo valor digitado em todos os campos.
     * 
     * @param empresa
     * @param campo
     * @param valor
     */
    public void updateVarious(Empresa empresaFilter, Empresa empresa) {
        try {
            Document empresaFilterDoc = empresaUpdateToDocument(empresaFilter);
            Document empresaDoc = empresaUpdateToDocument(empresa);
            this.mongoClient = new MongoClient(this.host + ":" + this.port, jodaToIsoDate());
            MongoDatabase database = this.mongoClient.getDatabase(this.database);
            MongoCollection<Document> collection = database.getCollection(COLLECTION);
            collection.updateMany(empresaFilterDoc, new Document("$set",empresaDoc));
        } finally {
            mongoClient.close();
        }
    }

    /**
     * Atualiza a empresa no banco pela chave primaria.
     * 
     * @param cnpj
     * @param update
     */
    public void updateById(String cnpj, Empresa update) {
        this.validateCnpj(cnpj);
        try {
            this.mongoClient = new MongoClient(this.host + ":" + this.port, isoDateToJoda());
            MongoDatabase database = this.mongoClient.getDatabase(this.database);
            MongoCollection<Document> collection = database.getCollection(COLLECTION);
            FindIterable<Document> find = collection.find(new Document("_id", cnpj));
            Document empresaFilterDoc = catchEmpresaByCnpj(find);
            collection.updateOne(new Document("_id", empresaFilterDoc.get("_id")), new Document("$set", empresaUpdateToDocument(update)));
        } finally {
            mongoClient.close();
        }
    }
    
    /**
     * Metodo privado que recebe uma lista de documentos iteraveis e retorna uma lista de strings, de acordo com o cnpj de cada empresa
     * 
     * @param find
     * @return
     */
    private List<String> catchCnpj(FindIterable<Document> find) {
        List<String> listKeys = new ArrayList<>();
        for(Document empresaDoc : find) {
            Set<String> setKeys = new HashSet<String>();
            setKeys.add((String) empresaDoc.get("_id"));
            Iterator<String> itr = setKeys.iterator();
            while (itr.hasNext()) {
                listKeys.add(itr.next());
            }
        }
        return listKeys;
    }

    /**
     * Pesquisa no banco uma empresa pela chave primaria, e retorna um documento.
     * 
     * @param find
     * @return
     */
    private Document catchEmpresaByCnpj(FindIterable<Document> find) {
        List<Document> empresasDoc = findForEach(find);
        List<String> cnpjs = catchCnpj(find);
        for(Document empresaDoc : empresasDoc) {
            for(String str : cnpjs) {
                if (str.equals(empresaDoc.get("_id")))
                    ;
                Document empresaFilterDoc = empresaDoc;

                return empresaFilterDoc;
            }
        }
        return null;
    }
    
    /**
     * Metodo privado que retorna uma lista de campos a serem utilizados como filtro de pesquisa.
     * 
     * @param empresaDoc
     * @return
     */
    private List<String> projectionsInclude(Document empresaDoc) {
        Set<String> setKeys = empresaDoc.keySet();
        List<String> listKeys = new ArrayList<>();
        Iterator<String> itr = setKeys.iterator();
        while (itr.hasNext()) {
            listKeys.add(itr.next());
        }
        return listKeys;
    }
    
    /**
     * Metodo privado, que traz a lista de documentos contidas dentro do banco de acordo com a instrucao passada.
     * 
     * @param find
     * @return
     */
    private List<Document> findForEach(FindIterable<Document> find) {
        List<Document> empresasDoc = new ArrayList<Document>();
        find.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                empresasDoc.add(document);
            }
        });
        return empresasDoc;
    }
    
    /**
     * Valida um cnpj da empresa, utilizado nos metodos de pesquisa, exclusão e atualização.
     * 
     * @param cnpj
     */
    private void validateCnpj(String cnpj) {
        List<ValidationMessage> messages = new CNPJValidator().invalidMessagesFor(cnpj);
        checkArgument(messages.size() == TAMANHO_DE_ERROS, "Sua empresa contém erros e não pôde ser salva no banco!" + messages);
    }
    
    /**
     * Valida uma empresa, de acordo com a qunatidade de erros: Se a quantidade for igual a zero, a instancia de empresa e considerada valida.
     * 
     * @param empresa
     */
    private void validarEmpresa(Empresa empresa) {
        if (containsError(empresa).size() != TAMANHO_DE_ERROS) {
            throw new IllegalArgumentException("Sua empresa contém erros e não pôde ser salva no banco!" + errorMessages(containsError(empresa)));
        }
    }
}
