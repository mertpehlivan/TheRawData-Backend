package com.mertdev.therawdata.webApi;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mertdev.therawdata.bussines.abstracts.ArticleService;
import com.mertdev.therawdata.bussines.requests.CreateArticleRequest;
import com.mertdev.therawdata.bussines.responses.GetPostResponse;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/article")
@AllArgsConstructor
public class ArticleController {
	private ArticleService articleService;
	
	@PostMapping("/create")
	public PostIdResponse createArticle(CreateArticleRequest articleRequest) {
		System.out.println(articleRequest);
		return articleService.createArticle(articleRequest);

	}
	@GetMapping("/{uniqueName}/getAllArticle")
	public List<GetPostResponse> getAllArticle(@PathVariable String uniqueName,Pageable pageable){
		return articleService.getAllArticle(uniqueName,pageable);
	}
	

}
