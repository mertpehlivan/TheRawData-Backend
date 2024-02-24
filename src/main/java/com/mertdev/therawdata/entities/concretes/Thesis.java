package com.mertdev.therawdata.entities.concretes;

import java.util.Date;
import java.util.stream.Collectors;

import com.mertdev.therawdata.entities.abstracts.Publication;
import com.mertdev.therawdata.entities.abstracts.PublicationType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "thesis")
public class Thesis extends Publication implements PublicationType{
	
	private String degree;
	private String pages;
	private String university;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_time", updatable = false)
	private Date creationTime;
    @PrePersist
    protected void onCreate() {
        creationTime = new Date();
    }	
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getPages() {
		return pages;
	}
	public void setPages(String pages) {
		this.pages = pages;
	}
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	@Override
	public PublicationType getObject() {
		return this;
	}

	@Override
	public String getSummary() {
		return String.format("%s, %s, %s, %s, %s",
			    getTitle(),
			    getDegree(),
			    getUniversity(),
			    getPages(),
			    getPublicationAuthors().stream()
			                            .map(author -> author.getAuthor().getFirstname() + " " + author.getAuthor().getLastname())
			                            .collect(Collectors.joining(", "))
			);

	}
}
