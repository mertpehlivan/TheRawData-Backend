package com.mertdev.therawdata.bussines.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SummaryRawDataResponse {
	private String title;
	private String previewImageUrl;
	private String rawDataExtension;
	private int rawDataLengt;
}
