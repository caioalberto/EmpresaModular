package br.com.caioribeiro.empresa.repository.util;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.joda.time.DateTime;

/**
 * @author Caio Ribeiro
 *
 */
public final class DateTransform implements Codec<DateTime> {
    
    /* (non-Javadoc)
     * @see org.bson.codecs.Encoder#encode(org.bson.BsonWriter, java.lang.Object, org.bson.codecs.EncoderContext)
     */
    @Override
    public void encode(BsonWriter writer, DateTime value, EncoderContext encoderContext) {
        writer.writeDateTime(value.getMillis());     
    }

    /* (non-Javadoc)
     * @see org.bson.codecs.Encoder#getEncoderClass()
     */
    @Override
    public Class<DateTime> getEncoderClass() {        
        return DateTime.class;
    }

    /* (non-Javadoc)
     * @see org.bson.codecs.Decoder#decode(org.bson.BsonReader, org.bson.codecs.DecoderContext)
     */
    @Override
    public DateTime decode(BsonReader reader, DecoderContext decoderContext) {
        return new DateTime(reader.readDateTime());
    }

}
