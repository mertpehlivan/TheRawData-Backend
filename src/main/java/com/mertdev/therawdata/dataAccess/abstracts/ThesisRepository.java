package com.mertdev.therawdata.dataAccess.abstracts;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mertdev.therawdata.entities.concretes.ResearchProject;
import com.mertdev.therawdata.entities.concretes.Thesis;

public interface ThesisRepository extends JpaRepository<Thesis, UUID> {
	public List<Thesis> findAllByOrderByCreationTimeDesc();
	List<Thesis> findByPublicationPost_User_UniqueNameOrderByCreationTimeDesc(String uniqueName);
}
