package com.mertdev.therawdata.webApi;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mertdev.therawdata.bussines.abstracts.ConferencePaperService;
import com.mertdev.therawdata.bussines.requests.CreateConferencePaperRequest;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/conferencePaper")
@AllArgsConstructor
public class ConferencePaperController {
	private final ConferencePaperService conferencePaperService;
	
	@PostMapping("/create")
	public PostIdResponse createConferencePaper(@RequestBody CreateConferencePaperRequest conferencePaperRequest) {
		System.out.println(conferencePaperRequest);
		return conferencePaperService.createConferencePaper(conferencePaperRequest);

	}
}
