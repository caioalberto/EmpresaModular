package br.com.caioribeiro.empresa.repository.util;

import java.util.HashMap;
import java.util.Map;

import org.bson.BsonType;
import org.bson.codecs.BsonTypeClassMap;
import org.bson.codecs.DocumentCodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.joda.time.DateTime;
import org.joda.time.Instant;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;

/**
 * 
 * @author Caio Ribeiro
 *
 */
public class MongoCodecs {
    private MongoCodecs() {
    }

    /**
     * Metodo que inclui nos codecs do mongodb a conversao de datetime para isodate;
     * 
     * @return
     */
    public static MongoClientOptions jodaToIsoDate() {
        Map<BsonType, Class<?>> replacements = new HashMap<BsonType, Class<?>>();
        replacements.put(BsonType.DATE_TIME, Instant.class);
        BsonTypeClassMap bsonTypeClassMap = new BsonTypeClassMap(replacements);
        DocumentCodecProvider documentCodecProvider = new DocumentCodecProvider(bsonTypeClassMap);
        CodecRegistry cr = CodecRegistries.fromRegistries(CodecRegistries.fromCodecs(new DateTransform()), CodecRegistries.fromProviders(documentCodecProvider), MongoClient.getDefaultCodecRegistry());
        MongoClientOptions option = MongoClientOptions.builder().codecRegistry(cr).build();
        return option;
    }

    /**
     * Metodo que inclui nos codecs mongodb a conversao de isodate para datetime;
     * 
     * @return
     */
    public static MongoClientOptions isoDateToJoda() {
        Map<BsonType, Class<?>> replacements = new HashMap<BsonType, Class<?>>();
        replacements.put(BsonType.DATE_TIME, DateTime.class);
        BsonTypeClassMap bsonTypeClassMap = new BsonTypeClassMap(replacements);
        DocumentCodecProvider documentCodecProvider = new DocumentCodecProvider(bsonTypeClassMap);
        CodecRegistry cr = CodecRegistries.fromRegistries(CodecRegistries.fromCodecs(new DateTransform()), CodecRegistries.fromProviders(documentCodecProvider), MongoClient.getDefaultCodecRegistry());
        MongoClientOptions option = MongoClientOptions.builder().codecRegistry(cr).build();
        return option;
    }
}
