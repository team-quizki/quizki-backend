package com.haxwell.apps.quizki.services;


import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.mockito.InjectMocks;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
//import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;

import com.haxwell.apps.quizki.entities.Topic;
import com.haxwell.apps.quizki.repositories.TopicRepository;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import com.haxwell.apps.quizki.services.TopicService;

public class TopicServiceImplTest {
	
	TopicService topicService;
	
	String TEXT_TOPIC_1 = "topic1";
	String TEXT_TOPIC_2 = "topic2";
	
	Long ID_TOPIC_1 = 1273L;
	Long ID_TOPIC_2 = 1274L;
	
	@Mock
	private TopicRepository topicRepository;

	private TopicServiceImpl topicservice;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);		
		topicService = new TopicServiceImpl(topicRepository);
	}

	@Test
	public void test_createNew_whenAListOfTopicsThatAlreadyExistIsPassedIn() {
		// given
		List<Topic> list = new ArrayList<>();

		Topic topic1 = new Topic(TEXT_TOPIC_1);
		Topic topic2 = new Topic(TEXT_TOPIC_2);
		
		list.add(topic1);
		list.add(topic2);
		
		Topic fullTopic1 = new Topic(TEXT_TOPIC_1);
		fullTopic1.setId(ID_TOPIC_1);

		Topic fullTopic2 = new Topic(TEXT_TOPIC_2);
		fullTopic2.setId(ID_TOPIC_2);
		
		///
		when(topicRepository.findByText(TEXT_TOPIC_1)).thenReturn(fullTopic1);
		when(topicRepository.findByText(TEXT_TOPIC_2)).thenReturn(fullTopic2);
		
		// when
		String rtn = topicService.createNew(list);
		
		// then
		assertTrue(rtn.equals("[" + ID_TOPIC_1 + "," + ID_TOPIC_2 + "]"));
		verify(topicRepository, times(0)).save(any(Topic.class));
	}
	
	@Test
	public void test_createNew_whenAListOfTopicsThatDoesNotAlreadyExistIsPassedIn() {
		// given
		List<Topic> list = new ArrayList<>();
		
		Topic topic1 = new Topic(TEXT_TOPIC_1);
		Topic topic2 = new Topic(TEXT_TOPIC_2);
		
		list.add(topic1);
		list.add(topic2);
		
		Topic fullTopic1 = new Topic(TEXT_TOPIC_1);
		fullTopic1.setId(ID_TOPIC_1);

		Topic fullTopic2 = new Topic(TEXT_TOPIC_2);
		fullTopic2.setId(ID_TOPIC_2);
		
		///
		when(topicRepository.save(any(Topic.class))).thenReturn(fullTopic1).thenReturn(fullTopic2);
		
		// when
		String rtn = topicService.createNew(list);
		
		// then
		assertTrue(rtn.equals("[" + ID_TOPIC_1 + "," + ID_TOPIC_2 + "]"));
	}

	@Test
	public void test_getTopic_whenCorrectParamsArePassedIn() {

		String q = "biology";
		int page = 1;
		int size = 10;

		//Page<Topic> pageTopic = any(Page.class);
		Pageable pageRequest = PageRequest.of(page,size);

		List<Topic> list = new ArrayList<>();

		list.add(new Topic(TEXT_TOPIC_1));
		list.add(new Topic(TEXT_TOPIC_2));

		when(topicRepository.findByText(any(String.class), any(PageRequest.class))).thenReturn(list);

		List<Topic> listResponse = topicService.getTopicByText(q, page, size);

		assertThat(listResponse.size(), equalTo(2));
		
		verify(topicRepository, times(1)).findByText(q, PageRequest.of(page-1,size));
	}

	@Test
	public void test_getAllTopics_whenCorrectParamsArePassedIn() {

		int page = 1;
		int size = 10;
		
		List<Topic> list = new ArrayList<>();
		
		Page<Topic> pageOne = mock(Page.class);

		list.add(new Topic(TEXT_TOPIC_1));
		list.add(new Topic(TEXT_TOPIC_2));

		when(topicRepository.findAll(any(PageRequest.class))).thenReturn(pageOne);
		when(pageOne.getContent()).thenReturn(list);

		List<Topic> listResponse = topicService.getTopicByText(null, page, size);
		
		assertThat(listResponse.size(), equalTo(2));

		verify(topicRepository, times(1)).findAll(PageRequest.of(page-1,size));
	}

}
