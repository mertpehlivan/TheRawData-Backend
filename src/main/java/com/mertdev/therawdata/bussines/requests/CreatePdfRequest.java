package com.mertdev.therawdata.bussines.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePdfRequest {
	private Boolean addOnly;
	private Boolean pdfStatus;
	
}
