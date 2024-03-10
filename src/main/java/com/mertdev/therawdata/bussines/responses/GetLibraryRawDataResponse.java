package com.mertdev.therawdata.bussines.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetLibraryRawDataResponse {
	private Long id;
	private String title;
	private Double size;
	private String extention;
}
