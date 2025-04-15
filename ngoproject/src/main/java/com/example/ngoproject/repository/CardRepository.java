package com.example.ngoproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ngoproject.model.Cards;

@Repository
public interface CardRepository extends JpaRepository<Cards, Long>{

	List<Cards> findAll();
}
