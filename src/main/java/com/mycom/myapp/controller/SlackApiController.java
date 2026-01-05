package com.mycom.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.mycom.myapp.service.SlackApiService;

@Controller
public class SlackApiController {
	
	private final SlackApiService slackApiService;
	
	public SlackApiController(SlackApiService slackApiService) {
		this.slackApiService = slackApiService;
	}
	
	// get
	@GetMapping("/notify")
	public String sendSlackNotification() {
		slackApiService.sendMessage("🔔  SpringBootSlackApiTest App 메세지를 전송했습니다.");
		return "Slack에 Message를 보냈습니다.";
	}
}
