package com.example.ngoproject.services;

import com.example.ngoproject.model.Cards;
import com.example.ngoproject.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CardsService {

	    @Autowired
	    private CardRepository cardRepository;

	    public List<Cards> getAllCards() {
	        return cardRepository.findAll();
	    }

	    public Cards saveCard(Cards card) {
	        return cardRepository.save(card);
	    }

	    public void deleteCard(Long id) {
	        cardRepository.deleteById(id);
	    }
	}

