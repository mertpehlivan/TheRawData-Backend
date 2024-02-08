package com.mertdev.therawdata.dataAccess.abstracts;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mertdev.therawdata.entities.concretes.PublicationPost;
import com.mertdev.therawdata.entities.concretes.Share;
import com.mertdev.therawdata.entities.concretes.User;

public interface ShareRepository extends JpaRepository<Share, UUID> {
	List<Share> findAllByOrderByCreationTimeDesc(Pageable pageable);

	@Query("SELECT s.publicationPost FROM Share s WHERE s.user IN :users ORDER BY s.creationTime DESC")
	Page<PublicationPost> findPublicationPostsByUsers(Set<User> users, Pageable pageable);

	@Query("SELECT s FROM Share s WHERE s.user IN :users ORDER BY s.creationTime DESC")
	Page<Share> findByUserOrderByCreationTime(@Param("users") Set<User> users, Pageable pageable);

	@Query("SELECT s FROM Share s WHERE s.user.uniqueName = :uniqueName ORDER BY s.creationTime DESC")
	Page<Share> findByUserUniqueNameOrderByCreationTime( @Param("uniqueName") String uniqueName, Pageable pageable);
	
	@Query("SELECT s FROM Share s WHERE s.publicationPost.publicationType = :publicationType AND s.user.uniqueName = :uniqueName ORDER BY s.creationTime DESC")
	Page<Share> findByPublicationTypeAndUserUniqueNameOrderByCreationTime(
			@Param("publicationType") String publicationType, @Param("uniqueName") String uniqueName, Pageable pageable);
}
