package com.mertdev.therawdata.webApi;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mertdev.therawdata.bussines.abstracts.CompanyTestReportService;
import com.mertdev.therawdata.bussines.requests.CreateCompanyTestReportRequest;
import com.mertdev.therawdata.bussines.responses.GetPostResponse;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/companyTestReport")
@AllArgsConstructor
public class CompanyTestReportController {
	private final CompanyTestReportService companyTestReportService;
	@PostMapping("/create")
	public PostIdResponse createCompanyTestReport(@RequestBody CreateCompanyTestReportRequest reportRequest) {
		System.out.println(reportRequest);
		return companyTestReportService.createCompanyTestReport(reportRequest);
	}
	@GetMapping("{uniqueName}/getAllCompanyTestReport")
	public List<GetPostResponse> getAllCompanyTestReport(@PathVariable String uniqueName,Pageable pageable){
		
		return companyTestReportService.getAllCompanyTestReport(uniqueName,pageable);
	}
}
