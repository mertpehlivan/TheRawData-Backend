package com.mertdev.therawdata.webApi;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mertdev.therawdata.bussines.abstracts.ThesisService;
import com.mertdev.therawdata.bussines.requests.CreateThesisRequest;
import com.mertdev.therawdata.bussines.responses.GetPostResponse;
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
	@GetMapping("/{uniqueName}/getAllThesis")
	public List<GetPostResponse> getAllThesis(@PathVariable String uniqueName, Pageable pageable){
		return  thesisService.getAllThesis(uniqueName,pageable);
	}
}
