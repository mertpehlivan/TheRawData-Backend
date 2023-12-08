package com.mertdev.therawdata.bussines.concretes;

import org.springframework.stereotype.Service;

import com.mertdev.therawdata.bussines.abstracts.ChapterInABookService;
import com.mertdev.therawdata.bussines.abstracts.PublicationAuthorService;
import com.mertdev.therawdata.bussines.abstracts.PublicationPostService;
import com.mertdev.therawdata.bussines.requests.CreateChapterInABookRequest;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;
import com.mertdev.therawdata.dataAccess.abstracts.ChapterInABookRepository;
import com.mertdev.therawdata.entities.concretes.ChapterInBook;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChapterInABookServiceImpl implements ChapterInABookService{
	private final ChapterInABookRepository chapterInABookRepository;
	private final PublicationPostService postService;
	private final PublicationAuthorService publicationAuthorService;
	@Override
	public PostIdResponse createArticle(CreateChapterInABookRequest createChapterInABookRequest) {
		ChapterInBook chapterInBook = new ChapterInBook();
		chapterInBook.setBookTitle(createChapterInABookRequest.getBookTitle());
		chapterInBook.setChapterNumber(createChapterInABookRequest.getChapterNumber());
		chapterInBook.setDate(createChapterInABookRequest.getDate());
		chapterInBook.setDoi(createChapterInABookRequest.getDoi());
		chapterInBook.setEditor(createChapterInABookRequest.getEditor());
		chapterInBook.setIsbn(createChapterInABookRequest.getIsbn());
		chapterInBook.setPages(createChapterInABookRequest.getPages());
		chapterInBook.setPublisher(chapterInBook.getPublisher());
		chapterInBook.setTitle(createChapterInABookRequest.getTitle());
		publicationAuthorService.createAuthor(createChapterInABookRequest.getAuthors(), chapterInBook);
		return postService.createPublication(chapterInBook);
	}

}
