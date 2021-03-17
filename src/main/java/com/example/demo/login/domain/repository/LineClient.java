package com.example.demo.login.domain.repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.Multicast;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;

@Repository
public class LineClient {

	@Autowired
	LineMessagingClient lineMessagingClient;
	@Autowired
	IndividualDaoJdbc individualDao;

	public void pushMessage(String message) {

		try {

	    	TextMessage textMessage = new TextMessage(message);
	    	PushMessage pushMessage = new PushMessage("Cbd6bbdf148755beb5e88f452df463f38", textMessage);
	    	//PushMessage pushMessage = new PushMessage("U3ffa70b26534f3f36f59c17b82e7295d", textMessage);

	    	List<String> getList = individualDao.getLineMember();
	    	Set<String> set = new HashSet<>(getList);

	    	//Collections.addAll(set, "U3ffa70b26534f3f36f59c17b82e7295d", "U865a594ac019120cb7ae63039f0f3a15", "U609c2791228a2161de15122fd8992c8e", "U64ca0abe38ddaa36c978f612ab7d29fc");
	    	Multicast multicast = new Multicast(set, textMessage);

	      BotApiResponse response1 = lineMessagingClient.pushMessage(pushMessage).get();
	      BotApiResponse response2 = lineMessagingClient.multicast(multicast).get();

	      System.out.println(response1);
	      System.out.println(response2);

	    } catch (InterruptedException | ExecutionException e) {
	      throw new RuntimeException(e);
	    }

	}

}
