package br.com.caioribeiro.empresa.repository;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

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

    public void save(Document empresa) {
        try {
            this.mongoClient = new MongoClient(this.host, this.port);
            MongoDatabase database = this.mongoClient.getDatabase(this.database);
            MongoCollection<Document> collection = database.getCollection(COLLECTION);
            collection.insertOne(empresa);
        } finally {
            mongoClient.close();
        }
    }
        
        public void delete(Document empresa) {
            try {
                this.mongoClient = new MongoClient(this.host, this.port);
                MongoDatabase database = this.mongoClient.getDatabase(this.database);
                MongoCollection<Document> collection = database.getCollection(COLLECTION);
                collection.insertOne(empresa);
            } finally {
                mongoClient.close();
            }
        }

    
}
