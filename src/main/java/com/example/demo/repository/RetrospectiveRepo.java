package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Retrospective;

@Repository
public interface RetrospectiveRepo extends JpaRepository<Retrospective, String>{

	List<Retrospective> findAllByDate(LocalDate date,Pageable pageable);

	List<Retrospective> findAllByDate(LocalDate date);
	
}
