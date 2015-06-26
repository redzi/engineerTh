package com.red.webapp.api.currency;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.Collections;

/**
 * Created by tom on 2015-06-14.
 */
public class CurrencyRateHttpMessageConverter extends MappingJackson2HttpMessageConverter
{

    public CurrencyRateHttpMessageConverter() {
        setSupportedMediaTypes(Collections.singletonList(
                new MediaType("text", "html")
        ));
    }
}
