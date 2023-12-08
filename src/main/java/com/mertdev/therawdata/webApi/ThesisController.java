package com.mertdev.therawdata.webApi;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mertdev.therawdata.bussines.abstracts.ThesisService;
import com.mertdev.therawdata.bussines.requests.CreateThesisRequest;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/thesis")
@AllArgsConstructor
public class ThesisController {
	private ThesisService thesisService;
	@PostMapping("/create")
	public PostIdResponse createArticle(@RequestBody CreateThesisRequest thesisRequest) {
		System.out.println(thesisRequest);
		return thesisService.createThesis(thesisRequest);

	}
}
