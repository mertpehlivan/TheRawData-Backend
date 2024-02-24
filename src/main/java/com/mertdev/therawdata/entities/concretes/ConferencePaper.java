package com.mertdev.therawdata.entities.concretes;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.mertdev.therawdata.entities.abstracts.Publication;
import com.mertdev.therawdata.entities.abstracts.PublicationType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Entity

public class ConferencePaper extends Publication implements PublicationType{

	private Date date;
	private String conferenceName;
	private String location;
	private String doi;
	private String pages;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_time", updatable = false)
    private Date creationTime;

    @PrePersist
    protected void onCreate() {
        creationTime = new Date();
    }

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getConferenceName() {
		return conferenceName;
	}

	public void setConferenceName(String conferenceName) {
		this.conferenceName = conferenceName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDoi() {
		return doi;
	}

	public void setDoi(String doi) {
		this.doi = doi;
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
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
		return String.format("%s, %s, %s, %s, %s, %s, %s",
			    getTitle(),
			    getDate().toString(),
			    getConferenceName(),
			    getLocation(),
			    getPages(),
			    getDoi(),
			    getPublicationAuthors().stream()
			                            .map(author -> author.getAuthor().getFirstname() + " " + author.getAuthor().getLastname())
			                            .collect(Collectors.joining(", "))
			);

	}
	
}
