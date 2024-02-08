package com.mertdev.therawdata.dataAccess.abstracts;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mertdev.therawdata.entities.concretes.PublicationPost;
@Repository
public interface PublicationPostRepository extends JpaRepository<PublicationPost, UUID>{
	public List<PublicationPost> findAllByOrderByCreationTimeDesc(Pageable pageable);
	public List<PublicationPost> findAllByOrderByCreationTimeDesc();
	public List<PublicationPost> findPublicationPostsByUserUniqueName(@Param("uniqueName") String uniqueName,Pageable pageable);
	@Query("SELECT pp FROM PublicationPost pp WHERE LOWER(pp.publication.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    Page<PublicationPost> findByPublicationTitleContainingIgnoreCase(@Param("title") String title, Pageable pageable);
	
}
