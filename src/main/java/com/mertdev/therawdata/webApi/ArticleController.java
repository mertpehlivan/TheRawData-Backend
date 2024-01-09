package com.mertdev.therawdata.webApi;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mertdev.therawdata.bussines.abstracts.ArticleService;
import com.mertdev.therawdata.bussines.requests.CreateArticleRequest;
import com.mertdev.therawdata.bussines.responses.GetPostResponse;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;
import com.mertdev.therawdata.bussines.responses.PublicationPostResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/article")
@AllArgsConstructor
public class ArticleController {
	private ArticleService articleService;
	
	@PostMapping("/create")
	public PostIdResponse createArticle(@RequestBody CreateArticleRequest articleRequest) {
		System.out.println(articleRequest);
		return articleService.createArticle(articleRequest);

	}
	@GetMapping("/{uniqueName}/getAllArticle")
	public List<GetPostResponse> getAllArticle(@PathVariable String uniqueName){
		return articleService.getAllArticle(uniqueName);
	}
	

}
