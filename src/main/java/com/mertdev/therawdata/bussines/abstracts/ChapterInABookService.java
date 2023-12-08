package com.mertdev.therawdata.bussines.abstracts;

import com.mertdev.therawdata.bussines.requests.CreateChapterInABookRequest;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;

public interface ChapterInABookService {
	public PostIdResponse createArticle(CreateChapterInABookRequest createChapterInABookRequest);
}
