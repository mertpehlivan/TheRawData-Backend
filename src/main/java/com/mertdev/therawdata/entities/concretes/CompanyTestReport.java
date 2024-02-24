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

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CompanyTestReport extends Publication implements PublicationType{
	private Date date;
	private String companyName;
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_time", updatable = false)
    private Date creationTime;
	private List<String> authors;
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
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	@Override
	public PublicationType getObject() {
		// TODO Auto-generated method stub
		return this;
	}

	public List<String> getAuthors() {
		return authors;
	}

	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}
	@Override
	public String getSummary() {
		return String.format("%s, %s, %s, %s",
			    getTitle(),
			    getDate(),
			    getCompanyName(),
			    getPublicationAuthors().stream()
			                            .map(author -> author.getAuthor().getFirstname() + " " + author.getAuthor().getLastname())
			                            .collect(Collectors.joining(", "))
			);

	}
	
	
	
}
