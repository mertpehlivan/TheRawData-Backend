package com.mertdev.therawdata.bussines.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mertdev.therawdata.bussines.abstracts.ChapterInABookService;
import com.mertdev.therawdata.bussines.abstracts.PublicationAuthorService;
import com.mertdev.therawdata.bussines.abstracts.PublicationPostService;
import com.mertdev.therawdata.bussines.requests.CreateChapterInABookRequest;
import com.mertdev.therawdata.bussines.responses.GetPostResponse;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;
import com.mertdev.therawdata.bussines.responses.PublicationPostResponse;
import com.mertdev.therawdata.dataAccess.abstracts.ChapterInABookRepository;
import com.mertdev.therawdata.entities.abstracts.Publication;
import com.mertdev.therawdata.entities.concretes.Article;
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
		chapterInBook.setComment(createChapterInABookRequest.getComment());
		PostIdResponse id = postService.createPublication(chapterInBook);
		publicationAuthorService.createAuthor(createChapterInABookRequest.getAuthors(), chapterInBook);
		return id;
	}
	@Override
	public List<PublicationPostResponse> getAllChapterInABook() {
		
		return postService.getAll(
				chapterInABookRepository.findAllByOrderByCreationTimeDesc()
			);
	}
	@Override
	public List<GetPostResponse> getAllChapterInABook(String uniqueName) {
		List<ChapterInBook> chapterInBooks = chapterInABookRepository.findByPublicationPost_User_UniqueNameOrderByCreationTimeDesc(uniqueName);

		List<Publication> publication = new ArrayList<>();
		for(ChapterInBook chapterInBook : chapterInBooks) {
			publication.add(chapterInBook);
		}
		return postService.getAllPost(publication);
	}

}
