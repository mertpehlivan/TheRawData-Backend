package com.mertdev.therawdata.entities.concretes;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rawData")
public class RawData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String previewImageName;
	private String name;
	private String comment;
	private String rawDataName;
	
	@ManyToOne
	@JoinColumn(name = "rawdataFileId", nullable = false)
	private RawDataFile rawDataFileId;
}
