package com.mertdev.therawdata.dataAccess.abstracts;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mertdev.therawdata.entities.concretes.Article;
import com.mertdev.therawdata.entities.concretes.ConferencePaper;

public interface ConferencePaperRepository extends JpaRepository<ConferencePaper, UUID> {
	public List<ConferencePaper> findAllByOrderByCreationTimeDesc();
	public List<ConferencePaper> findByPublicationPost_User_UniqueNameOrderByCreationTimeDesc(String uniqueName);
}
