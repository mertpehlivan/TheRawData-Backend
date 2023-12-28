package com.mertdev.therawdata.dataAccess.abstracts;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mertdev.therawdata.entities.concretes.ConferencePaper;
import com.mertdev.therawdata.entities.concretes.ResearchProject;

public interface ResearchProjectRepository extends JpaRepository<ResearchProject, UUID>{
	public List<ResearchProject> findAllByOrderByCreationTimeDesc();
	List<ResearchProject> findByPublicationPost_User_UniqueNameOrderByCreationTimeDesc(String uniqueName);
}
