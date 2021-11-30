package com.ggingmin.cards.controller;

import com.ggingmin.cards.model.Card;
import com.ggingmin.cards.model.Customer;
import com.ggingmin.cards.repository.CardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CardsController {

    @Autowired
    private CardsRepository cardsRepository;

    @PostMapping("/myCards")
    public List<Card> getCardDetails(@RequestBody Customer customer) {
        List<Card> cards = cardsRepository.findByCustomerId(customer.getCustomerId());
        if (cards != null) {
            return cards;
        } else {
            return null;
        }
    }
}
