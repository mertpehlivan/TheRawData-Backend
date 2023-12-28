package com.mertdev.therawdata.bussines.abstracts;

import java.util.List;

import com.mertdev.therawdata.bussines.requests.CreateChapterInABookRequest;
import com.mertdev.therawdata.bussines.responses.GetPostResponse;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;
import com.mertdev.therawdata.bussines.responses.PublicationPostResponse;

public interface ChapterInABookService {
	public PostIdResponse createArticle(CreateChapterInABookRequest createChapterInABookRequest);
	public List<PublicationPostResponse> getAllChapterInABook(); 
	public List<GetPostResponse> getAllChapterInABook(String uniqueName);
}
