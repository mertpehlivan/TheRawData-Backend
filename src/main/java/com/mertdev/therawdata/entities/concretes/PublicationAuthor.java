package com.mertdev.therawdata.entities.concretes;

import com.mertdev.therawdata.entities.abstracts.Publication;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PublicationAuthor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "authorId", nullable = false)
	private User author;
	
	@ManyToOne
	@JoinColumn(name = "publicationId", nullable = false)
	private Publication publicationId;
	
	
}
