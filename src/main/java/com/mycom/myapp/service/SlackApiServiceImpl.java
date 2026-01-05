package com.mycom.myapp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SlackApiServiceImpl implements SlackApiService{

	private final WebClient webClient;
	
	@Value("${slack.bot.token}")
	private String slackBotToken;
	
	@Value("${slack.channel}")
	private String slackChannel;
	
	// 생성자 DI - webClient
	public SlackApiServiceImpl() {
		this.webClient = WebClient.builder()
							.baseUrl("https://slack.com/api")
							.defaultHeader("Content-Type", "application/json")
							.build();
	}
	
	@Override
	public void sendMessage(String message) {
		sendMessageToChannel(slackChannel, message);	
	}
	
	public void sendMessageToChannel(String channel, String message) {
		String jsonMessage = String.format(
			"""
			{
				"channel" : "%s",
				"text" : "%s"
			}
			""",	
			channel,
			message
		);
		
		this.webClient.post()
					  .uri("/chat.postMessage")
					  .header("Authorization", "Bearer " + slackBotToken)
					  .bodyValue(jsonMessage)
					  .retrieve() // 응답 처리 설정
					  .bodyToMono(String.class) // 응답을 단일한 String type 으로 변환
					  .doOnSuccess( response -> System.out.println("Slack Response : " + response)) // 성공
					  .doOnError( error -> System.err.println("Slack Error : " + error.getMessage())) // 실패
					  .subscribe();
					  
	}

}