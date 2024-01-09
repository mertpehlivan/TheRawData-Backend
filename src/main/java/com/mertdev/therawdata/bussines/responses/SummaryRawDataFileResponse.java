package com.mertdev.therawdata.bussines.responses;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SummaryRawDataFileResponse {
	private UUID id;
	private String title;
	private List<SummaryRawDataResponse> rawDatas;
	private int filesLenght;
}
