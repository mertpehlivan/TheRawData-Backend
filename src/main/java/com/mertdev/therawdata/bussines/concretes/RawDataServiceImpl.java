package com.mertdev.therawdata.bussines.concretes;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mertdev.therawdata.bussines.abstracts.RawDataFileService;
import com.mertdev.therawdata.bussines.abstracts.RawDataService;
import com.mertdev.therawdata.bussines.abstracts.UserService;
import com.mertdev.therawdata.bussines.requests.CreateRawDataRequest;
import com.mertdev.therawdata.bussines.requests.UpdateRawDataRequest;
import com.mertdev.therawdata.dataAccess.abstracts.RawDataRepository;
import com.mertdev.therawdata.entities.concretes.RawData;
import com.mertdev.therawdata.entities.concretes.RawDataFile;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RawDataServiceImpl implements RawDataService {
	private static final Logger log = LoggerFactory.getLogger(RawDataServiceImpl.class);
	private final UserService userService;
	private final AmazonClient amazonClient;
	private final RawDataFileService dataFileService;
	private final RawDataRepository rawDataRepository;
	private final S3Service s3Service;
	
	@Override
	public void createRawData(CreateRawDataRequest createRawDataRequest) throws Exception {
		try {
			RawDataFile file = dataFileService.getByFileId(createRawDataRequest.getRawDataFileId());
			String email = userService.getCurrentUsername();
			String imageName = UUID.randomUUID().toString() + "."
					+ exSplit(createRawDataRequest.getImage().getOriginalFilename());
			String rawDataName = UUID.randomUUID().toString() + "."
					+ exSplit(createRawDataRequest.getRawData().getOriginalFilename());

			RawData rawData = new RawData();
			rawData.setName(createRawDataRequest.getName());
			rawData.setPreviewImageName(imageName);
			rawData.setComment(createRawDataRequest.getComment());
			rawData.setRawDataFileId(file);
			rawData.setRawDataName(rawDataName);
			rawData.setPrice(createRawDataRequest.getPrice());
			RawData rawDataSaved = rawDataRepository.save(rawData);

			s3Service.putObject("%s/%s/%s/%s/previewImage/%s".formatted(email, file.getPublicationPostId().getId(),
					file.getId(), rawDataSaved.getId(), imageName), createRawDataRequest.getImage().getBytes());
			s3Service.putObject("%s/%s/%s/%s/rawData/%s".formatted(email, file.getPublicationPostId().getId(),
					file.getId(), rawDataSaved.getId(), rawDataName), createRawDataRequest.getRawData().getBytes());

		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void rawDataUpdate(UpdateRawDataRequest updateRawDataRequest) throws Exception {
		try {
			Optional<RawData> rawData = rawDataRepository.findById(updateRawDataRequest.getId());
			RawData data = rawData.get();
			if (updateRawDataRequest.getImage() != null) {
				s3Service.deleteObject("%s/%s/%s/%s/previewImage/%s".formatted(
						data.getRawDataFileId().getPublicationPostId().getUser().getEmail(),
						data.getRawDataFileId().getPublicationPostId().getId(), data.getRawDataFileId().getId(),
						data.getId(), data.getPreviewImageName()));
				s3Service.putObject("%s/%s/%s/%s/previewImage/%s".formatted(
						data.getRawDataFileId().getPublicationPostId().getUser().getEmail(),
						data.getRawDataFileId().getPublicationPostId().getId(), data.getRawDataFileId().getId(),
						data.getId(), data.getPreviewImageName()), updateRawDataRequest.getImage().getBytes());

			}
			if (updateRawDataRequest.getRawData() != null) {
				s3Service.deleteObject("%s/%s/%s/%s/rawData/%s".formatted(
						data.getRawDataFileId().getPublicationPostId().getUser().getEmail(),
						data.getRawDataFileId().getPublicationPostId().getId(), data.getRawDataFileId().getId(),
						data.getId(), data.getRawDataName()));
				s3Service.putObject(
						"%s/%s/%s/%s/rawData/%s".formatted(
								data.getRawDataFileId().getPublicationPostId().getUser().getEmail(),
								data.getRawDataFileId().getPublicationPostId().getId(), data.getRawDataFileId().getId(),
								data.getId(), data.getRawDataName()),
						updateRawDataRequest.getRawData().getBytes());

			}
			if(updateRawDataRequest.getName() != data.getName()) {
				data.setName(updateRawDataRequest.getName());
			}
			if(updateRawDataRequest.getPrice() != data.getPrice()) {
				data.setPrice(updateRawDataRequest.getPrice());
			}
			if(updateRawDataRequest.getComment() != data.getComment()) {
				data.setComment(updateRawDataRequest.getComment());
			}
			rawDataRepository.save(data);
			
			

		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void deleteRawData(Long rawDataId) {
		try {
			Optional<RawData> rawData = rawDataRepository.findById(rawDataId);
			RawData data = rawData.get();
			s3Service.deleteObject("%s/%s/%s/%s".formatted(
					data.getRawDataFileId().getPublicationPostId().getUser().getEmail(),
					data.getRawDataFileId().getPublicationPostId().getId(), data.getRawDataFileId().getId(),
					data.getId()));
			rawDataRepository.deleteById(data.getId());
			
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	
	private String exSplit(String text) {
		int lastDotIndex = text.lastIndexOf('.');

		if (lastDotIndex != -1) {
			System.out.println(text.substring(lastDotIndex + 1));
			return text.substring(lastDotIndex + 1);
		} else {
			System.out.println("Hata: Nokta bulunamadı");
			return null;
		}
	}

	
	
	
	@Override
	public byte[] getPreviewImage(String previewImageName) {
		try {
			RawData rawData = rawDataRepository.findByPreviewImageName(previewImageName);
			String email = rawData.getRawDataFileId().getPublicationPostId().getUser().getEmail();
			log.info("User email: " + email);
			UUID postId = rawData.getRawDataFileId().getPublicationPostId().getId();
			log.info("Post id: " + postId.toString());
			UUID fileId = rawData.getRawDataFileId().getId();
			log.info("File id: " + fileId.toString());

			log.info("%s/%s/%s/%s/previewImage/%s".formatted(email, postId.toString(), fileId.toString(),
					rawData.getId().toString(), previewImageName.toString()));

			return s3Service.getObject("%s/%s/%s/%s/previewImage/%s".formatted(email, postId.toString(),
					fileId.toString(), rawData.getId().toString(), previewImageName.toString()));
		} catch (Exception e) {
			throw e;
		}

	}

	

}
