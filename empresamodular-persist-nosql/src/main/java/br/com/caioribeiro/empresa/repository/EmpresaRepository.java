package br.com.caioribeiro.empresa.repository;

import static br.com.caioribeiro.empresa.repository.util.EmpresaToDocument.empresaToDocument;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import br.com.caioribeiro.empresa.Empresa;

/**
 * Classe que realiza a conexao com o MongoDB.
 * 
 * @author Caio Ribeiro
 *
 */
public class EmpresaRepository {

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

    public void save(Empresa empresa) throws MongoException {
        try {
            Document empresaDoc = empresaToDocument(empresa);
            this.mongoClient = new MongoClient(this.host, this.port);
            MongoDatabase database = this.mongoClient.getDatabase(this.database);
            MongoCollection<Document> collection = database.getCollection(COLLECTION);
            collection.insertOne(empresaDoc);
        }
        finally {
            mongoClient.close();
        }
    }
        public void updateOne(Empresa empresa) throws MongoException {
            try{
                Document empresaDoc = empresaToDocument(empresa);                           
                if(empresa.getCnpj().equals(empresaDoc.get("cnpj"))){
                    this.mongoClient = new MongoClient(this.host, this.port);
                    MongoDatabase database = this.mongoClient.getDatabase(this.database);
                    MongoCollection<Document> collection = database.getCollection(COLLECTION);
                    collection.updateOne(new Document("cnpj", empresa.getCnpj()), new Document("$set",empresaDoc));
                }                
            }            
            finally{
                mongoClient.close();
            }
        }
}
