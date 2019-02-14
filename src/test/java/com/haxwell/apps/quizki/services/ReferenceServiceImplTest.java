package com.haxwell.apps.quizki.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.haxwell.apps.quizki.entities.Reference;
import com.haxwell.apps.quizki.entities.Topic;
import com.haxwell.apps.quizki.repositories.ReferenceRepository;
import com.haxwell.apps.quizki.repositories.TopicRepository;

public class ReferenceServiceImplTest {

	
	@Mock
	private ReferenceRepository referenceRepository;
	
	private ReferenceServiceImpl referenceService;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);		
		referenceService = new ReferenceServiceImpl(referenceRepository);
	}
	
	
	@Test
	public void test_getReference_whenCorrectParamsArePassedIn() {

		String q = "biology";
		int page = 1;
		int size = 10;

		List<Reference> list = new ArrayList<>();

		list.add(new Reference("Ref 1"));
		list.add(new Reference("Ref 2"));

		when(referenceRepository.findByText(any(String.class), any(PageRequest.class))).thenReturn(list);

		List<Reference> listResponse = referenceService.getReferenceByText(q, page, size);

		assertThat(listResponse.size(), equalTo(2));
		
		verify(referenceRepository, times(1)).findByText(q, PageRequest.of(page-1,size));
	}

	@Test
	public void test_getAllReference_whenCorrectParamsArePassedIn() {

		int page = 1;
		int size = 10;
		
		Page<Reference> pageOne = mock(Page.class);
		List<Reference> list = new ArrayList<>();

		list.add(new Reference("Ref 1"));
		list.add(new Reference("Ref 2"));

		when(referenceRepository.findAll(any(PageRequest.class))).thenReturn(pageOne);
		when(pageOne.getContent()).thenReturn(list);

		List<Reference> listResponse = referenceService.getReferenceByText(null, page, size);
		
		assertThat(listResponse.size(), equalTo(2));

		verify(referenceRepository, times(1)).findAll(PageRequest.of(page-1,size));
	}
	
}
