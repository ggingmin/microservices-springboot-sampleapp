package com.ggingmin.cards.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.ggingmin.cards.config.CardsServiceConfig;
import com.ggingmin.cards.model.Card;
import com.ggingmin.cards.model.Customer;
import com.ggingmin.cards.model.Properties;
import com.ggingmin.cards.repository.CardsRepository;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CardsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CardsController.class);

    @Autowired
    private CardsRepository cardsRepository;

    @Autowired
    private CardsServiceConfig cardsConfig;

    @PostMapping("/myCards")
    @Timed(value="getCardDetails.time", description="Time taken to return Card Details")
    public List<Card> getCardDetails(@RequestBody Customer customer) {
        LOGGER.info("getCardDetails() method started");
        List<Card> cards = cardsRepository.findByCustomerId(customer.getCustomerId());
        LOGGER.info("getCardDetails() method ended");
        if (cards != null) {
            return cards;
        } else {
            return null;
        }
    }

    @GetMapping("/cards/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(cardsConfig.getMsg(), cardsConfig.getBuildVersion(),
                cardsConfig.getMailDetails(), cardsConfig.getActiveBranches());
        String jsonStr = ow.writeValueAsString(properties);
        return jsonStr;
    }

}
