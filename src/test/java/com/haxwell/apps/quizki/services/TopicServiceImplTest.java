package com.haxwell.apps.quizki.services;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.haxwell.apps.quizki.entities.Topic;
import com.haxwell.apps.quizki.repositories.TopicRepository;

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
		Mockito.when(topicRepository.findByText(TEXT_TOPIC_1)).thenReturn(fullTopic1);
		Mockito.when(topicRepository.findByText(TEXT_TOPIC_2)).thenReturn(fullTopic2);
		
		// when
		String rtn = topicService.createNew(list);
		
		// then
		assertTrue(rtn.equals("[" + ID_TOPIC_1 + "," + ID_TOPIC_2 + "]"));
		verify(topicRepository, times(0)).save(Mockito.any(Topic.class));
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
		Mockito.when(topicRepository.save(Mockito.any(Topic.class))).thenReturn(fullTopic1).thenReturn(fullTopic2);
		
		// when
		String rtn = topicService.createNew(list);
		
		// then
		assertTrue(rtn.equals("[" + ID_TOPIC_1 + "," + ID_TOPIC_2 + "]"));
	}

	// TODO: Test the case when a list of topics is passed in, and only one of them does not yet exist.

}
