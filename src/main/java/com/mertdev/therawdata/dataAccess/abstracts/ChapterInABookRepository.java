package com.mertdev.therawdata.dataAccess.abstracts;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mertdev.therawdata.entities.concretes.Article;
import com.mertdev.therawdata.entities.concretes.ChapterInBook;

public interface ChapterInABookRepository extends JpaRepository<ChapterInBook, UUID> {
	public List<ChapterInBook> findAllByOrderByCreationTimeDesc();
	public List<ChapterInBook> findByPublicationPost_User_UniqueNameOrderByCreationTimeDesc(String uniqueName);
}
