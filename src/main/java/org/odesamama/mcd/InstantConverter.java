package org.odesamama.mcd;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Instant;
import java.util.Date;

/**
 * Created by starnakin on 16.10.2015.
 */

@Converter(autoApply = true)
public class InstantConverter implements AttributeConverter<Instant,Date>{
    @Override
    public Date convertToDatabaseColumn(Instant date) {
        if(date != null) {
            return Date.from(date);
        }

        return null;
    }

    @Override
    public Instant convertToEntityAttribute(Date date) {
        if(date != null) {
            return date.toInstant();
        }

        return null;
    }
}
