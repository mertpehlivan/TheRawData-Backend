package com.mertdev.therawdata.bussines.responses;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SummaryRawDataFileResponse {
	private String title;
	private List<SummaryRawDataResponse> rawDatas;
	private int filesLenght;
}
