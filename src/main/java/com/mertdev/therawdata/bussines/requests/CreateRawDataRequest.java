package com.mertdev.therawdata.bussines.requests;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class CreateRawDataRequest {
	private UUID rawDataFileId;
	private MultipartFile image;
    private String name;
    private String comment;
    private String price;
    private MultipartFile rawData;
}
