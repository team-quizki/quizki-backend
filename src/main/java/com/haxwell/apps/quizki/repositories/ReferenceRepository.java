package com.haxwell.apps.quizki.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.haxwell.apps.quizki.entities.Reference;

public interface ReferenceRepository extends CrudRepository<Reference, Long> {
	
	Reference findByText(String txt);	
	@Query(value = "SELECT t FROM Reference t where t.text like %?1% ")
	List<Reference> findByText(String word, Pageable pageable);
	Page<Reference> findAll(Pageable pageable);
}
