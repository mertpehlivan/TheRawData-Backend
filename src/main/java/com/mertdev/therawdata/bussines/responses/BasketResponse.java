package com.mertdev.therawdata.bussines.responses;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasketResponse {
	private UUID publicationPostId;
	private String title;
	private List<SummaryRawDataFileResponse> rawDataFile;
}
