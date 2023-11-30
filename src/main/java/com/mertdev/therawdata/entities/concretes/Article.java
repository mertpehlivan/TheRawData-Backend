package com.mertdev.therawdata.entities.concretes;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mertdev.therawdata.entities.abstracts.Publication;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "articles")
public class Article extends Publication{

	private String journalName;
	private String volume;
	private String issue;
	private String pages;
	private String doi;
	
	@OneToMany(mappedBy = "publicationId")
	@JsonBackReference
	private List<RawDataFile> rawDataFiles;
	
	public String getJournalName() {
		return journalName;
	}
	public void setJournalName(String journalName) {
		this.journalName = journalName;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	public String getPages() {
		return pages;
	}
	public void setPages(String pages) {
		this.pages = pages;
	}
	public String getDoi() {
		return doi;
	}
	public void setDoi(String doi) {
		this.doi = doi;
	}
	
}
