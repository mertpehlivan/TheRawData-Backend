package com.mertdev.therawdata.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mertdev.therawdata.entities.concretes.RawData;

public interface RawDataRepository extends JpaRepository<RawData, Long>{
	public RawData findByPreviewImageName(String previewImageName);
}
