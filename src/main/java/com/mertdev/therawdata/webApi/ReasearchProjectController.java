package com.mertdev.therawdata.webApi;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mertdev.therawdata.bussines.abstracts.ReasearchProjectService;
import com.mertdev.therawdata.bussines.requests.CreateReasearchProjectRequest;
import com.mertdev.therawdata.bussines.responses.GetPostResponse;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;
import com.mertdev.therawdata.bussines.responses.PublicationPostResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/reasearchProject")
@AllArgsConstructor
public class ReasearchProjectController {
	private final ReasearchProjectService projectService;
	@PostMapping("/create")
	public PostIdResponse createArticle(@RequestBody CreateReasearchProjectRequest creProjectRequest) {
		System.out.println(creProjectRequest);
		return projectService.createReasearchProject(creProjectRequest);

	}
	
	@GetMapping("/{uniqueName}/getAllReasearchProject")
	public List<GetPostResponse> getAllReasearchProject(@PathVariable String uniqueName){
		
		return projectService.getAllReasearchProject(uniqueName);
	}
}
