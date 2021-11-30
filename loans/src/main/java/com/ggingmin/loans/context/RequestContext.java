package com.ggingmin.loans.context;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
public class RequestContext {

    public static final String CORRELATION_ID = "ggingbank-correlation-id";

    private String correlationId = new String();
}
