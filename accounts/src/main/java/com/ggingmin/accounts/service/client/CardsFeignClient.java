package com.ggingmin.accounts.service.client;

import com.ggingmin.accounts.model.Card;
import com.ggingmin.accounts.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("cards")
public interface CardsFeignClient {

    @RequestMapping(method = RequestMethod.POST, value = "myCards", consumes = "application/json")
    List<Card> getCardDetails(
            @RequestHeader("ggingbank-correlation-id")
            String correlationId,
            @RequestBody Customer customer);
}
