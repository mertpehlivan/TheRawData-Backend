package com.mertdev.therawdata.bussines.concretes;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mertdev.therawdata.bussines.abstracts.ArticleService;
import com.mertdev.therawdata.bussines.abstracts.PublicationAuthorService;
import com.mertdev.therawdata.bussines.abstracts.PublicationPostService;
import com.mertdev.therawdata.bussines.requests.CreateArticleRequest;
import com.mertdev.therawdata.bussines.responses.GetPostResponse;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;
import com.mertdev.therawdata.bussines.responses.PublicationPostResponse;
import com.mertdev.therawdata.core.utilities.mappers.abstracts.DTOToArticleMappers;
import com.mertdev.therawdata.dataAccess.abstracts.ArticleRepository;
import com.mertdev.therawdata.entities.concretes.Article;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class ArticleServiceImpl implements ArticleService {
	
	private final DTOToArticleMappers articleMappers;
	private final PublicationPostService postService;
	private final ArticleRepository articleRepository ;
	private final PublicationAuthorService publicationAuthorService;


	
	@Override
	public PostIdResponse createArticle(CreateArticleRequest createArticleRequest) {
		 
		Article article = articleMappers.createArticleToArticle(createArticleRequest);
		PostIdResponse id = postService.createPublication(article,createArticleRequest);
		publicationAuthorService.createAuthor(createArticleRequest.getAuthors(), article);
		return id;
	}


	@Override
	public List<PublicationPostResponse> getAllArticle() {
		
		return postService.getAll(
					articleRepository.findAllByOrderByCreationTimeDesc()
				);
	}
	@Override
	public List<GetPostResponse> getAllArticle(String uniqueName,Pageable pageable) {
		 
		
		return postService.getAllByUniqueName(uniqueName, "Article", pageable);

	}

}
