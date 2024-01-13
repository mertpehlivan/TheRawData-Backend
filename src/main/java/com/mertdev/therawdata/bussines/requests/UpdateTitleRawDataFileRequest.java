package com.mertdev.therawdata.bussines.requests;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTitleRawDataFileRequest {
	private UUID id;
	private String title;
	
}
