package com.mertdev.therawdata.bussines.concretes;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.mertdev.therawdata.bussines.abstracts.RawDataFileService;
import com.mertdev.therawdata.bussines.abstracts.RawDataService;
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
	private final AmazonClient amazonClient;
	private final RawDataFileService dataFileService;
	private final RawDataRepository rawDataRepository;
	@Override
	public void createRawData(CreateRawDataRequest createRawDataRequest) throws Exception {
		RawDataFile file = dataFileService.getByFileId(createRawDataRequest.getRawDataFileId());
		
		String imageName = UUID.randomUUID().toString();
		String rawDataName = UUID.randomUUID().toString();
		
		RawData rawData = new RawData();
		rawData.setName(createRawDataRequest.getName());
		rawData.setPreviewImageName(imageName);
		rawData.setComment(createRawDataRequest.getComment());
		rawData.setRawDataFileId(file);
		rawData.setRawDataName(rawDataName);
		Long rawDataId = rawDataRepository.save(rawData).getId();
		
		
		amazonClient.uploadFile(createRawDataRequest.getImage(),
				createRawDataRequest.getRawDataFileId().toString(),
				rawDataId.toString() ,
				imageName);
		amazonClient.uploadFile(createRawDataRequest.getRawData(),
				createRawDataRequest.getRawDataFileId().toString(),
				rawDataId.toString() ,
				rawDataName);
		
	
	}

}
