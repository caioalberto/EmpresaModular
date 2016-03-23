package br.com.caioribeiro.empresa.repository;

import static br.com.caioribeiro.empresa.repository.util.EmpresaAssembler.documentToEmpresa;
import static br.com.caioribeiro.empresa.repository.util.EmpresaAssembler.empresaToDocument;
import static br.com.caioribeiro.empresa.repository.util.EmpresaAssembler.empresasToDocument;
import static br.com.caioribeiro.empresa.repository.util.EmpresaUpdateToDocument.empresaUpdateToDocument;
import static br.com.caioribeiro.empresa.repository.util.MongoCodecs.isoDateToJoda;
import static br.com.caioribeiro.empresa.repository.util.MongoCodecs.jodaToIsoDate;
import static br.com.caioribeiro.empresa.util.ValidadorUtil.containsError;
import static br.com.caioribeiro.empresa.util.ValidadorUtil.errorMessages;
import static com.mongodb.client.model.Projections.fields;

import java.util.List;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

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
            Document empresaDoc = empresaToDocument(empresa);
            this.mongoClient = new MongoClient(this.host + ":" + this.port, option);
            MongoDatabase database = this.mongoClient.getDatabase(this.database);
            MongoCollection<Document> collection = database.getCollection(COLLECTION);
            collection.insertOne(empresaDoc);
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
            List<Document> empresasDoc = empresasToDocument(empresa, outra);
            this.mongoClient = new MongoClient(this.host + ":" + this.port, option);
            MongoDatabase database = this.mongoClient.getDatabase(this.database);
            MongoCollection<Document> collection = database.getCollection(COLLECTION);
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

    public void deleteOne(String cnpj) throws MongoWriteException {
        option = jodaToIsoDate();
        try {
                this.mongoClient = new MongoClient(this.host + ":" + this.port, option);
                MongoDatabase database = this.mongoClient.getDatabase(this.database);
                MongoCollection<Document> collection = database.getCollection(COLLECTION);
                collection.deleteOne(new Document("cnpj", cnpj));
        } finally {
            mongoClient.close();
        }
    }
    
    public void deleteVarious(Empresa empresaFilter) throws MongoWriteException {
        option = jodaToIsoDate();
        this.validarEmpresa(empresaFilter);
        try {
            empresaToDocument(empresaFilter);
                this.mongoClient = new MongoClient(this.host + ":" + this.port, option);
                MongoDatabase database = this.mongoClient.getDatabase(this.database);
                MongoCollection<Document> collection = database.getCollection(COLLECTION);
                collection.deleteMany(new Document());
        } finally {
            mongoClient.close();
        }
    }
    
    public void find(String cnpj) {
        option = isoDateToJoda();
        try {
            this.mongoClient = new MongoClient(this.host + ":" + this.port, option);
            MongoDatabase database = this.mongoClient.getDatabase(this.database);
            MongoCollection<Document> collection = database.getCollection(COLLECTION);
            FindIterable<Document> find = collection.find(new Document("_id", cnpj));
            find.forEach(new Block<Document>() {
                @Override
                public void apply(final Document document) {
                    System.out.println(documentToEmpresa(document));
                }
            });
        } finally {
            mongoClient.close();
        }
    }
    
    public void findByFields(Empresa empresaFilter, Empresa empresa) {
        option = isoDateToJoda();
        try {
            Document empresaDoc = empresaToDocument(empresaFilter);
            Document empresaFilterDoc = empresaUpdateToDocument(empresa);
            //Document empresaDoc = empresaUpdateToDocument(empresa);
            this.mongoClient = new MongoClient(this.host + ":" + this.port, option);
            MongoDatabase database = this.mongoClient.getDatabase(this.database);
            MongoCollection<Document> collection = database.getCollection(COLLECTION);
            FindIterable<Document> find = collection.find(empresaFilterDoc).projection(fields(empresaDoc));
            find.forEach(new Block<Document>() {
                @Override
                public void apply(final Document document){ 
                    System.out.println(documentToEmpresa(document));
                }
            });
        } finally {
            mongoClient.close();
        }
    }
}

