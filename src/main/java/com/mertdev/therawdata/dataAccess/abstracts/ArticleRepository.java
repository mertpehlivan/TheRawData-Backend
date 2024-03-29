package com.mertdev.therawdata.dataAccess.abstracts;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mertdev.therawdata.entities.concretes.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID>{
	public List<Article> findAllByOrderByCreationTimeDesc();
	List<Article> findByPublicationPost_User_UniqueNameOrderByCreationTimeDesc(String uniqueName);
}
