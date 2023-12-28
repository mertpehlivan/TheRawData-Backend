package com.mertdev.therawdata.bussines.concretes;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.mertdev.therawdata.bussines.abstracts.RawDataFileService;
import com.mertdev.therawdata.bussines.abstracts.RawDataService;
import com.mertdev.therawdata.bussines.abstracts.UserService;
import com.mertdev.therawdata.bussines.requests.CreateRawDataRequest;
import com.mertdev.therawdata.dataAccess.abstracts.RawDataRepository;
import com.mertdev.therawdata.entities.concretes.RawData;
import com.mertdev.therawdata.entities.concretes.RawDataFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@AllArgsConstructor
public class RawDataServiceImpl implements RawDataService{
	private final UserService userService;
	private final AmazonClient amazonClient;
	private final RawDataFileService dataFileService;
	private final RawDataRepository rawDataRepository;
	private final S3Service s3Service;
	@Override
	public void createRawData(CreateRawDataRequest createRawDataRequest) throws Exception {
		RawDataFile file = dataFileService.getByFileId(createRawDataRequest.getRawDataFileId());
		String email = userService.getCurrentUsername();
		String imageName = UUID.randomUUID().toString() +"."+ exSplit(createRawDataRequest.getImage().getOriginalFilename());
		String rawDataName = UUID.randomUUID().toString() +"." + exSplit(createRawDataRequest.getRawData().getOriginalFilename());
		
		RawData rawData = new RawData();
		rawData.setName(createRawDataRequest.getName());
		rawData.setPreviewImageName(imageName);
		rawData.setComment(createRawDataRequest.getComment());
		rawData.setRawDataFileId(file);
		rawData.setRawDataName(rawDataName);
		Long rawDataId = rawDataRepository.save(rawData).getId();
		
		s3Service.putObject(
				"%s/%s/%s/previewImage/%s".formatted(
							email,
							file.getPublicationPostId().getId(),
							createRawDataRequest.getRawDataFileId(),
							imageName
						), 
				createRawDataRequest
					.getImage()
					.getBytes());
		s3Service.putObject(
				"%s/%s/%s/rawData/%s".formatted(
							email,
							file.getPublicationPostId().getId(),
							createRawDataRequest.getRawDataFileId(),
							rawDataName
						), 
				createRawDataRequest
					.getRawData()
					.getBytes());
				
		
	}
	private String exSplit(String text) {
	    String[] temp = text.split("\\.");
	    
	    if (temp.length > 1) {
	        System.out.println(temp[1]);
	        return temp[1];
	    } else {
	        System.out.println("Hata: Dizi elemanları eksik");
	        return null;
	    }
	}


}
