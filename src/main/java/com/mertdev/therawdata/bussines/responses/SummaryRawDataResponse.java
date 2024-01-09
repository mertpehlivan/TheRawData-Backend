package com.mertdev.therawdata.bussines.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.services.s3.endpoints.internal.Value.Int;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SummaryRawDataResponse {
	private Long id;
	private String title;
	private String comment;
	private String previewImageUrl;
	private String rawDataExtension;
	private int price;
	private int rawDataLengt;
}
