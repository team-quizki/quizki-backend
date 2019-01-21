package com.haxwell.apps.quizki.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.haxwell.apps.quizki.entities.Topic;

public interface TopicRepository extends PagingAndSortingRepository<Topic, Long> {

	Topic findByText(String txt);

	@Query(value = "SELECT t FROM Topic t where t.text like %?1% ")
	List<Topic> findByText(String word, Pageable pageable);

	Page<Topic> findAll(Pageable pageable);
}
