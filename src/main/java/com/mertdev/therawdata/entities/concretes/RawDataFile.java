package com.mertdev.therawdata.entities.concretes;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mertdev.therawdata.entities.abstracts.Publication;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rawDataFile")
public class RawDataFile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@ManyToOne
	@JoinColumn(name="publicationId")
	@JsonBackReference
	private Publication publicationId;
	
	@OneToMany(mappedBy = "rawDataFileId")
	private List<RawData> rawDatas;
	
}
