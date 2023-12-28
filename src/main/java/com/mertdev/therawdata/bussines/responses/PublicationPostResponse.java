package com.mertdev.therawdata.bussines.responses;

import java.util.List;

import com.mertdev.therawdata.bussines.responses.abstracts.PublicationResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicationPostResponse {
	private PublicationResponse publicationResponse;
	private List<RawDataFileResponse> rawDataFiles;
}
