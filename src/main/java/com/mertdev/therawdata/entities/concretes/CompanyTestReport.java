package com.mertdev.therawdata.entities.concretes;

import java.util.Date;
import java.util.List;

import com.mertdev.therawdata.entities.abstracts.Publication;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CompanyTestReport extends Publication{
	private Date date;
	private String companyName;

	
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
	
	
	
}
