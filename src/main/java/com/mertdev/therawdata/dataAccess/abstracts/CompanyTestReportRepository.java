package com.mertdev.therawdata.dataAccess.abstracts;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mertdev.therawdata.entities.concretes.Article;
import com.mertdev.therawdata.entities.concretes.ChapterInBook;
import com.mertdev.therawdata.entities.concretes.CompanyTestReport;

public interface CompanyTestReportRepository extends JpaRepository<CompanyTestReport, UUID> {
	public List<CompanyTestReport> findAllByOrderByCreationTimeDesc();
	public List<CompanyTestReport> findByPublicationPost_User_UniqueNameOrderByCreationTimeDesc(String uniqueName);
}
