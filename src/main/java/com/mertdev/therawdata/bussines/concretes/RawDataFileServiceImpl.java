package com.mertdev.therawdata.bussines.concretes;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.amazonaws.services.alexaforbusiness.model.NotFoundException;
import com.mertdev.therawdata.bussines.abstracts.PublicationPostService;
import com.mertdev.therawdata.bussines.abstracts.RawDataFileService;
import com.mertdev.therawdata.bussines.abstracts.UserService;
import com.mertdev.therawdata.bussines.requests.CreateRawDataFileRequest;
import com.mertdev.therawdata.bussines.requests.UpdateTitleRawDataFileRequest;
import com.mertdev.therawdata.bussines.responses.CreatedRawDataFileIdResponse;
import com.mertdev.therawdata.dataAccess.abstracts.RawDataFileRepository;
import com.mertdev.therawdata.entities.concretes.PublicationPost;
import com.mertdev.therawdata.entities.concretes.RawDataFile;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RawDataFileServiceImpl implements RawDataFileService {
	private final PublicationPostService postService;
	private final RawDataFileRepository rawDataFileRepository;
	private final S3Service s3Service;
	private UserService userService;

	@Override
	public CreatedRawDataFileIdResponse createRawDataFile(CreateRawDataFileRequest createRawDataFileRequest) {
		PublicationPost post = postService.findPost(createRawDataFileRequest.getPublicationPostId());
		RawDataFile rawDataFile = new RawDataFile();
		rawDataFile.setName(createRawDataFileRequest.getName());
		rawDataFile.setPublicationPostId(post);

		return new CreatedRawDataFileIdResponse(rawDataFileRepository.save(rawDataFile).getId());
	}

	@Override
	public RawDataFile getByFileId(UUID id) {

		return rawDataFileRepository.getById(id);
	}

	public void updateRawDataFileName(UpdateTitleRawDataFileRequest request) {
		try {
			Optional<RawDataFile> optionalFile = rawDataFileRepository.findById(request.getId());

			if (optionalFile.isPresent()) {
				RawDataFile file = optionalFile.get();
				file.setName(request.getTitle());
				rawDataFileRepository.save(file);
			} else {
				// Handle the case where the file is not found
				// For example, you can throw an exception or log a message
				throw new NotFoundException("RawDataFile not found with id: " + request.getId());
			}
		} catch (Exception e) {
			// Handle the exception appropriately (e.g., log it)
			System.err.println("Error updating RawDataFile: " + e.getMessage());
		}
	}

	@Override
	public void deleteRawDataFile(UUID fileId) throws Exception {
		Optional<RawDataFile> rawDataFile = rawDataFileRepository.findById(fileId);
		RawDataFile data = rawDataFile.get();
		if(userService.getCurrentUser().getId() != data.getPublicationPostId().getUser().getId()) {
			throw new Exception("Unauthorised Access");
		}
		
		try {
			data.getRawDatas().clear();
			rawDataFileRepository.save(data);
			s3Service.deleteObject("%s/%s/%s".formatted(
					data.getPublicationPostId().getUser().getEmail(),
					data.getPublicationPostId().getId(), 
					data.getId()));
			rawDataFileRepository.deleteById(data.getId());
			
			
		} catch (Exception e) {
			throw e;
		}
		
	}

}
