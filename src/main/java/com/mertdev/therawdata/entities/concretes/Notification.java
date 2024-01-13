package com.mertdev.therawdata.entities.concretes;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Notification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String type;
	private String content;
	private String publicationTitle;
	private String fullName;
	private String userLink;
	private String publicationLink;
	
	private Boolean status;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_time", updatable = false)
    private Date creationTime;
}
