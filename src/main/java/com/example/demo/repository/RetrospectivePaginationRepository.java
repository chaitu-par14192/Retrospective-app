package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Retrospective;

@Repository
public interface RetrospectivePaginationRepository extends PagingAndSortingRepository<Retrospective, String>{

	List<Retrospective> findAllByDate(LocalDate date1, Pageable pageRequest);

}
