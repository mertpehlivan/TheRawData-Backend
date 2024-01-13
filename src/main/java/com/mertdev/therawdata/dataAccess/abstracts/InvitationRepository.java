package com.mertdev.therawdata.dataAccess.abstracts;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mertdev.therawdata.entities.concretes.Invitations;
import com.mertdev.therawdata.entities.concretes.Notification;

public interface InvitationRepository extends JpaRepository<Invitations, Long>{
	Page<Invitations> findByUserId(UUID id, Pageable pageable);
}
