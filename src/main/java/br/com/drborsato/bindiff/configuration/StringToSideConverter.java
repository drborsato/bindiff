package br.com.drborsato.bindiff.configuration;

import br.com.drborsato.bindiff.model.Side;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSideConverter implements Converter<String, Side> {
    @Override
    public Side convert(String side) {
        return Side.valueOf(side.toUpperCase());
    }
}
