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
import com.mertdev.therawdata.dataAccess.abstracts.RawDataFileRepository;
import com.mertdev.therawdata.dataAccess.abstracts.RawDataRepository;
import com.mertdev.therawdata.entities.concretes.PublicationPost;
import com.mertdev.therawdata.entities.concretes.RawData;
import com.mertdev.therawdata.entities.concretes.RawDataFile;
import com.mertdev.therawdata.entities.concretes.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RawDataServiceImpl implements RawDataService {
	private static final Logger log = LoggerFactory.getLogger(RawDataServiceImpl.class);
	private final UserService userService;
	private final RawDataFileRepository rawFileRepository;
	private final RawDataRepository rawDataRepository;
	private final S3Service s3Service;
	
	@Override
	public void createRawData(CreateRawDataRequest createRawDataRequest) throws Exception {
		try {
			Optional<RawDataFile> file = rawFileRepository.findById(createRawDataRequest.getRawDataFileId());
			String email = userService.getCurrentUsername();
			String imageName = UUID.randomUUID().toString() + "."
					+ exSplit(createRawDataRequest.getImage().getOriginalFilename());
			String rawDataName = UUID.randomUUID().toString() + "."
					+ exSplit(createRawDataRequest.getRawData().getOriginalFilename());

			RawData rawData = new RawData();
			rawData.setName(createRawDataRequest.getName());
			rawData.setPreviewImageName(imageName);
			rawData.setComment(createRawDataRequest.getComment());
			rawData.setRawDataFileId(file.get());
			rawData.setRawDataName(rawDataName);
			rawData.setPrice(createRawDataRequest.getPrice());
			rawData.setSize(bytesToMB(createRawDataRequest.getRawData().getSize()));
			RawData rawDataSaved = rawDataRepository.save(rawData);
			UUID userId = userService.getCurrentUser().getId();
			s3Service.putObject("%s/%s/%s/%s/previewImage/%s".formatted(userId, file.get().getPublicationPostId().getId(),
					file.get().getId(), rawDataSaved.getId(), imageName), createRawDataRequest.getImage().getBytes());
			s3Service.putObject("%s/%s/%s/%s/rawData/%s".formatted(userId, file.get().getPublicationPostId().getId(),
					file.get().getId(), rawDataSaved.getId(), rawDataName), createRawDataRequest.getRawData().getBytes());

		} catch (Exception e) {
			throw e;
		}
	}
	private double bytesToMB(long bytes) {
		return (double) bytes / (1024 * 1024);
    }

	


	@Override
	public void rawDataUpdate(UpdateRawDataRequest updateRawDataRequest) throws Exception {
	    try {
	        Optional<RawData> rawDataOptional = rawDataRepository.findById(updateRawDataRequest.getId());
	        if (rawDataOptional.isPresent()) {
	            RawData data = rawDataOptional.get();

	            if (updateRawDataRequest.getImage() != null) {
	                System.out.println("Resim değişti");
	                s3Service.deleteObject("%s/%s/%s/%s/previewImage/%s".formatted(
	                        data.getRawDataFileId().getPublicationPostId().getUser().getId(),
	                        data.getRawDataFileId().getPublicationPostId().getId(), data.getRawDataFileId().getId(),
	                        data.getId(), data.getPreviewImageName()));
	                s3Service.putObject("%s/%s/%s/%s/previewImage/%s".formatted(
	                        data.getRawDataFileId().getPublicationPostId().getUser().getId(),
	                        data.getRawDataFileId().getPublicationPostId().getId(), data.getRawDataFileId().getId(),
	                        data.getId(), data.getPreviewImageName()), updateRawDataRequest.getImage().getBytes());
	            }

	            if (updateRawDataRequest.getRawData() != null) {
	                String newRawDataName = removeExtension(data.getRawDataName()) + "." +
	                        exSplit(updateRawDataRequest.getRawData().getOriginalFilename());
	                System.out.println("Yeni raw data adı: " + newRawDataName);

	                s3Service.deleteObject("%s/%s/%s/%s/rawData/%s".formatted(
	                        data.getRawDataFileId().getPublicationPostId().getUser().getId(),
	                        data.getRawDataFileId().getPublicationPostId().getId(), data.getRawDataFileId().getId(),
	                        data.getId(), data.getRawDataName()));
	                s3Service.putObject("%s/%s/%s/%s/rawData/%s".formatted(
	                        data.getRawDataFileId().getPublicationPostId().getUser().getId(),
	                        data.getRawDataFileId().getPublicationPostId().getId(), data.getRawDataFileId().getId(),
	                        data.getId(), newRawDataName), updateRawDataRequest.getRawData().getBytes());

	                data.setRawDataName(newRawDataName);
	            }

	            if (!updateRawDataRequest.getName().equals(data.getName())) {
	                data.setName(updateRawDataRequest.getName());
	            }
	            if (updateRawDataRequest.getPrice() != data.getPrice()) {
	                data.setPrice(updateRawDataRequest.getPrice());
	            }
	            if (!updateRawDataRequest.getComment().equals(data.getComment())) {
	                data.setComment(updateRawDataRequest.getComment());
	            }

	            rawDataRepository.save(data);
	        } else {
	            throw new Exception("RawData with ID " + updateRawDataRequest.getId() + " not found.");
	        }
	    } catch (Exception e) {
	        throw e;
	    }
	}


	public static String removeExtension(String fileName) {
		if (fileName == null) {
			return null;
		}
		int lastDotIndex = fileName.lastIndexOf('.');
		if (lastDotIndex == -1) {
			return fileName;
		}
		return fileName.substring(0, lastDotIndex);
	}
	@Override
	public Double totalRawDataSize() {
	    Double totalSize = 0.0;
	    User user = userService.getCurrentUser();
	    if (user != null) {
	        for(PublicationPost publicationPost : user.getPublicationPosts()) {
	            for(RawDataFile rawDataFile : publicationPost.getRawDataFile()) {
	                for(RawData rawData : rawDataFile.getRawDatas()) {
	                    totalSize = totalSize + rawData.getSize();
	                }
	            }
	        }
	    }
	    return totalSize;
	}
	@Override
	public void deleteRawData(Long rawDataId) {
		try {
			Optional<RawData> rawData = rawDataRepository.findById(rawDataId);
			RawData data = rawData.get();
			 s3Service.deleteObject("%s/%s/%s/%s/rawData/%s".formatted(
                     data.getRawDataFileId().getPublicationPostId().getUser().getId(),
                     data.getRawDataFileId().getPublicationPostId().getId(), data.getRawDataFileId().getId(),
                     data.getId(), data.getRawDataName()));
			 s3Service.deleteObject("%s/%s/%s/%s/previewImage/%s".formatted(
                     data.getRawDataFileId().getPublicationPostId().getUser().getId(),
                     data.getRawDataFileId().getPublicationPostId().getId(), data.getRawDataFileId().getId(),
                     data.getId(), data.getPreviewImageName()));
			
			rawDataRepository.deleteById(data.getId());
			
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	@Override
	public byte[] getRawDataMyPublicationToLibrary(Long rawDataId,User user) throws Exception {
		try {
			Optional<RawData> rawData = rawDataRepository.findById(rawDataId);
			
				RawData data = rawData.get();
				return s3Service.getObject("%s/%s/%s/%s/rawData/%s".formatted(
						data.getRawDataFileId().getPublicationPostId().getUser().getId(),
						data.getRawDataFileId().getPublicationPostId().getId(), data.getRawDataFileId().getId(),
						data.getId(), data.getRawDataName()));
			
			
			
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
			UUID userId = rawData.getRawDataFileId().getPublicationPostId().getUser().getId();
			log.info("User email: " + userId);
			UUID postId = rawData.getRawDataFileId().getPublicationPostId().getId();
			log.info("Post id: " + postId.toString());
			UUID fileId = rawData.getRawDataFileId().getId();
			log.info("File id: " + fileId.toString());

			log.info("%s/%s/%s/%s/previewImage/%s".formatted(userId, postId.toString(), fileId.toString(),
					rawData.getId().toString(), previewImageName.toString()));

			return s3Service.getObject("%s/%s/%s/%s/previewImage/%s".formatted(userId, postId.toString(),
					fileId.toString(), rawData.getId().toString(), previewImageName.toString()));
		} catch (Exception e) {
			throw e;
		}

	}

	

}
