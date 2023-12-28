package com.mertdev.therawdata.bussines.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RawDataResponse {
	private Long id;
	private String previewImageName;
	private String name;
	private String comment;
	private String rawDataName;
}
