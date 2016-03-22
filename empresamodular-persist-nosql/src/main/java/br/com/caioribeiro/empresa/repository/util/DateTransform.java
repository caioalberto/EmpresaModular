package br.com.caioribeiro.empresa.repository.util;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.joda.time.DateTime;

public final class DateTransform implements Codec<DateTime> {
    
    @Override
    public void encode(BsonWriter writer, DateTime value, EncoderContext encoderContext) {
        writer.writeDateTime(value.getMillis());
        
    }

    @Override
    public Class<DateTime> getEncoderClass() {        
        return DateTime.class;
    }

    @Override
    public DateTime decode(BsonReader reader, DecoderContext decoderContext) {
        return new DateTime(reader.readDateTime());
    }
}
