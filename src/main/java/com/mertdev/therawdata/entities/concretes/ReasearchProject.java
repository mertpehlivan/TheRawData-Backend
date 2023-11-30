package com.mertdev.therawdata.entities.concretes;

import java.util.Date;

import com.mertdev.therawdata.entities.abstracts.Publication;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reaserchProjects")
public class ReasearchProject extends Publication{

	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
