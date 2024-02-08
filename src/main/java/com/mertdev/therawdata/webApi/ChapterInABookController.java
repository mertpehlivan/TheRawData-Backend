package com.mertdev.therawdata.webApi;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mertdev.therawdata.bussines.abstracts.ChapterInABookService;
import com.mertdev.therawdata.bussines.requests.CreateChapterInABookRequest;
import com.mertdev.therawdata.bussines.responses.GetPostResponse;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/chapterInABook")
@AllArgsConstructor
public class ChapterInABookController {
	private final ChapterInABookService chapterInABookService;
	
	@PostMapping("/create")
	public PostIdResponse createArticle(@RequestBody CreateChapterInABookRequest chapterInABookRequest) {
		System.out.println(chapterInABookRequest);
		return chapterInABookService.createArticle(chapterInABookRequest);
	}
	
	@GetMapping("{uniqueName}/getAllChapterInABook")
	public List<GetPostResponse> getAllChapterInABook(@PathVariable String uniqueName,Pageable pageable){
		
		return chapterInABookService.getAllChapterInABook(uniqueName,pageable);
	}
}
