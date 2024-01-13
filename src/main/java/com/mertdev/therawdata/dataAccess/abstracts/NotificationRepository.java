package com.mertdev.therawdata.dataAccess.abstracts;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mertdev.therawdata.entities.concretes.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>{
	Page<Notification> findAll(Pageable pageable);

	Page<Notification> findByUserId(UUID id, Pageable pageable);
	Long countByUserIdAndStatus(UUID userId, boolean status);
	
}
