package com.mertdev.therawdata.dataAccess.abstracts;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mertdev.therawdata.entities.concretes.RawDataFile;

public interface RawDataFileRepository extends JpaRepository<RawDataFile, UUID>{

}
