package br.com.caioribeiro.empresa.repository;

import static br.com.caioribeiro.empresa.assembler.EmpresaAssembler.documentToEmpresa;
import static br.com.caioribeiro.empresa.assembler.EmpresaAssembler.documentsToEmpresa;
import static br.com.caioribeiro.empresa.assembler.EmpresaAssembler.empresaToDocument;
import static br.com.caioribeiro.empresa.assembler.EmpresaAssembler.empresasToDocument;
import static br.com.caioribeiro.empresa.assembler.EmpresaUpdateToDocument.empresaUpdateToDocument;
import static br.com.caioribeiro.empresa.repository.util.MongoCodecs.isoDateToJoda;
import static br.com.caioribeiro.empresa.repository.util.MongoCodecs.jodaToIsoDate;
import static br.com.caioribeiro.empresa.util.ValidadorUtil.containsError;
import static br.com.caioribeiro.empresa.util.ValidadorUtil.errorMessages;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.connection.ConnectionPoolSettings;

import br.com.caioribeiro.empresa.Empresa;
import br.com.caioribeiro.empresa.assembler.EmpresaUpdateToDocument;

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
    private MongoClientOptions option;
    public static final String COLLECTION = "empresa";

    public EmpresaRepository(String host, Integer port, String database) {
        this.host = host;
        this.port = port;
        this.database = database;
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

    /**
     * Salva apenas uma instancia de empresa no banco.
     * 
     * @param empresa
     * @throws MongoException
     */
    public void saveOne(Empresa empresa) throws MongoException {
        this.validarEmpresa(empresa);
        option = jodaToIsoDate();
        try {
            this.mongoClient = new MongoClient(this.host + ":" + this.port, option);
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
     * 
     * Recebe duas instancias de empresa, e as adiciona no banco.
     * 
     * @param empresa
     * @param outra
     * @throws MongoException
     */
    public void saveVarious(Empresa empresa, Empresa outra) throws MongoException {
        option = jodaToIsoDate();
        try {
            this.mongoClient = new MongoClient(this.host + ":" + this.port, option);
            MongoDatabase database = this.mongoClient.getDatabase(this.database);
            MongoCollection<Document> collection = database.getCollection(COLLECTION);
            List<Document> empresasDoc = empresasToDocument(empresa, outra);
            collection.insertMany(empresasDoc);
        } finally {
            mongoClient.close();
        }
    }

    /**
     * Atualiza apenas uma instancia de empresa no banco, de acordo com a empresa filtro.
     * 
     * @param empresaFilter
     * @param empresa
     * @throws MongoException
     */
    public void updateOne(Empresa empresaFilter, Empresa empresa) throws MongoException {
        this.validarEmpresa(empresaFilter);
        option = jodaToIsoDate();
        try {
            Document empresaFilterDoc = empresaToDocument(empresaFilter);
            this.mongoClient = new MongoClient(this.host + ":" + this.port, option);
            MongoDatabase database = this.mongoClient.getDatabase(this.database);
            MongoCollection<Document> collection = database.getCollection(COLLECTION);
            collection.updateOne(new Document("_id", empresaFilterDoc.get("_id")), new Document("$set", empresaUpdateToDocument(empresa)));
        } finally {
            mongoClient.close();
        }
    }

    /**
     * Atualiza todos objetos do tipo empresa, de acordo com o campo chave digitado, e seta o novo valor digitado em todos os campos.
     * 
     * @param empresa
     * @param campo
     * @param valor
     */
    public void updateVarious(Empresa empresa) {
        option = jodaToIsoDate();
        try {
            Document empresaDoc = empresaUpdateToDocument(empresa);
            this.mongoClient = new MongoClient(this.host + ":" + this.port, option);
            MongoDatabase database = this.mongoClient.getDatabase(this.database);
            MongoCollection<Document> collection = database.getCollection(COLLECTION);            
            collection.updateMany(new Document(), new Document("$set", empresaDoc));
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
        option = jodaToIsoDate();
        try {
            this.mongoClient = new MongoClient(this.host + ":" + this.port, option);
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
        option = jodaToIsoDate();
        try {
            Document empresaFiltroDoc = empresaUpdateToDocument(empresaFiltro);
            this.mongoClient = new MongoClient(this.host + ":" + this.port, option);
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
        option = isoDateToJoda();
        try {
            this.mongoClient = new MongoClient(this.host + ":" + this.port, option);
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
     * Encontra no banco um objeto do tipo empresa, de acordo com a empresa filtro.
     * 
     * @param empresaFilter
     * @param empresa
     * @return
     */
    public List<Empresa> findByFields(Empresa empresaFilter, Empresa empresa) {
        List<Empresa> empresas = new ArrayList<Empresa>();
        option = isoDateToJoda();
        try {
            Document empresaDoc = empresaUpdateToDocument(empresaFilter);
            Document empresaFilterDoc = empresaUpdateToDocument(empresa);
            this.mongoClient = new MongoClient(this.host + ":" + this.port, option);
            MongoDatabase database = this.mongoClient.getDatabase(this.database);
            MongoCollection<Document> collection = database.getCollection(COLLECTION);
            FindIterable<Document> find = collection.find(empresaFilterDoc).projection(fields(empresaDoc));
            List<Document> empresasDoc = findForEach(find);
            for(Document document : empresasDoc) {
                empresas.add(documentToEmpresa(document));
            }
        } finally {
            mongoClient.close();
        }
        return empresas;
    }
    
    public String findObjectAndReturnString() {
        List<Empresa> empresas = new ArrayList<Empresa>();
        String cnpj = null;
        option = isoDateToJoda();
        try {
            this.mongoClient = new MongoClient(this.host + ":" + this.port, option);
            MongoDatabase database = this.mongoClient.getDatabase(this.database);
            MongoCollection<Document> collection = database.getCollection(COLLECTION);
            FindIterable<Document> find = collection.find().projection(new Document());
            List<Document> empresasDoc = findForEach(find);
            for(Document document : empresasDoc) {
                empresas.add(documentToEmpresa(document));
            }
            for(Empresa emp : empresas) {
                cnpj = emp.getCnpj();
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
        option = isoDateToJoda();
        try {
            Document empresaFilterDoc = empresaUpdateToDocument(empresaFilter);
            this.mongoClient = new MongoClient(this.host + ":" + this.port, option);
            MongoDatabase database = this.mongoClient.getDatabase(this.database);
            List<String> projKeys = projectionsInclude(empresaFilterDoc);
            MongoCollection<Document> collection = database.getCollection(COLLECTION);
            FindIterable<Document> find = collection.find().projection(fields(include(projKeys)));
            List<Document> empresasDoc = findForEach(find);
            for(Document document : empresasDoc) {
                empresas.add(documentToEmpresa(document));
            }
            List<String> cnpjs = catchCnpj(find);
        } finally {
            mongoClient.close();
        }
        return empresas;
    }
    
    public List<Empresa> findByNumberAndQuantity (Empresa empresa, Integer qtdPaginas, Integer qtdEmpresas) {
        List<Empresa> empresas = new ArrayList<Empresa>();
        option = isoDateToJoda();
        try {
            this.mongoClient = new MongoClient(this.host + ":" + this.port, option);
            MongoDatabase database = this.mongoClient.getDatabase(this.database);
            MongoCollection<Document> collection = database.getCollection(COLLECTION);
            FindIterable<Document> find = collection.find().limit(qtdEmpresas * (qtdPaginas - 1)).skip(qtdPaginas).sort(new Document ("_id", 1));
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
    
    private List<String> catchCnpj(FindIterable<Document> find) {
        List<String> listKeys = new ArrayList<>();
        for(Document empresaDoc : find) {
            Set<String> setKeys = (Set<String>) empresaDoc.get("_id");
            Iterator<String> itr = setKeys.iterator();
            while (itr.hasNext()) {
                listKeys.add(itr.next());
            }
        }
        return listKeys;
    }

}
