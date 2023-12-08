package com.mertdev.therawdata.dataAccess.abstracts;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mertdev.therawdata.entities.concretes.ConferencePaper;

public interface ConferencePaperRepository extends JpaRepository<ConferencePaper, UUID> {

}
