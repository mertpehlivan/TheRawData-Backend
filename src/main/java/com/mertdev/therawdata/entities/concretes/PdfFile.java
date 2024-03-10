package com.mertdev.therawdata.entities.concretes;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PdfFile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Boolean pdfStatus;
	private Boolean addOnly;
	private String pdfFileName;
	private String pdfExtension;
	@OneToOne(mappedBy = "pdfFile")
	private PublicationPost publicationPost;

}
