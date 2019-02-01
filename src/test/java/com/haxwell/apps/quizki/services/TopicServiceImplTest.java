package com.haxwell.apps.quizki.services;


import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.web.util.HtmlUtils;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.haxwell.apps.quizki.entities.Topic;
import com.haxwell.apps.quizki.repositories.TopicRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;


@RunWith(SpringRunner.class)
public class TopicServiceImplTest {

	@TestConfiguration
	static class TopicServiceTestContextConfiguration {
		@Bean
		public TopicService topicService() {
			return new TopicServiceImpl();
		}
	}
	
	String TEXT_TOPIC_1 = "topic1";
	String TEXT_TOPIC_2 = "topic2";
	
	Long ID_TOPIC_1 = 1273L;
	Long ID_TOPIC_2 = 1274L;
	
	@Autowired
	private TopicService topicService;
	
	@MockBean
	private TopicRepository topicRepository;
	
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

		Topic fullTopic1 = new Topic(TEXT_TOPIC_1);
		fullTopic1.setId(ID_TOPIC_1);


		when(
			topicRepository.findByText(q, pageRequest))
		.thenReturn(list);

		List<Topic> listResponse = topicService.getTopicByText(q, page, size);

		verify(topicRepository, times(1)).findByText(q, PageRequest.of(page-1,size));
		verify(topicRepository, times(0)).findAll(PageRequest.of(page-1,size));
		//assertThat(listResponse.size(), equalTo(list.size()));
	}

	@Ignore("Null pointer exception with String q")
	@Test
	public void test_getTopic_whenNoQParamArePassedIn() {
		String q = "";
		int page = 1;
		int size = 10;
		Page<Topic> pageTopic = mock(Page.class);
		Pageable pageRequest = PageRequest.of(page,size);

		when(
			topicRepository.findAll(pageRequest))
		.thenReturn(pageTopic);

		List<Topic> listResponse = topicService.getTopicByText("", page, size);

		verify(topicRepository, times(0)).findByText(q, PageRequest.of(page-1,size));
		verify(topicRepository, times(1)).findAll(PageRequest.of(page-1,size));

	}

	// TODO: Test the case when a list of topics is passed in, and only one of them does not yet exist.

}
