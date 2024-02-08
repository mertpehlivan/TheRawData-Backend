package com.mertdev.therawdata.bussines.requests;


import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRawDataRequest {
	private Long id;
	private MultipartFile image;
    private String name;
    private String comment;
    private int price;
    @Nullable
    private MultipartFile rawData;
}
