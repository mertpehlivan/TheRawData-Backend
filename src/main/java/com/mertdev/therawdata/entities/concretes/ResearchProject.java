package com.mertdev.therawdata.entities.concretes;

import java.util.Date;

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
@Table(name = "reaserchProjects")
public class ResearchProject extends Publication implements PublicationType{

	private Date date;
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

	
	

	
}
