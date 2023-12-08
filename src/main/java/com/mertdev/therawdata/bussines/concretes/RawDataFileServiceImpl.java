package com.mertdev.therawdata.bussines.concretes;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.mertdev.therawdata.bussines.abstracts.PublicationPostService;
import com.mertdev.therawdata.bussines.abstracts.RawDataFileService;
import com.mertdev.therawdata.bussines.requests.CreateRawDataFileRequest;
import com.mertdev.therawdata.bussines.responses.CreatedRawDataFileIdResponse;
import com.mertdev.therawdata.dataAccess.abstracts.RawDataFileRepository;
import com.mertdev.therawdata.entities.concretes.PublicationPost;
import com.mertdev.therawdata.entities.concretes.RawDataFile;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@AllArgsConstructor
public class RawDataFileServiceImpl implements RawDataFileService{
	private final PublicationPostService postService;
	private final RawDataFileRepository rawDataFileRepository;
	
	@Override
	public CreatedRawDataFileIdResponse createRawDataFile(CreateRawDataFileRequest createRawDataFileRequest) {
		PublicationPost post = postService.findPost(createRawDataFileRequest.getPublicationPostId());
		RawDataFile rawDataFile = new RawDataFile();
		rawDataFile.setName(createRawDataFileRequest.getName());
		rawDataFile.setPublicationPostId(post);
		
		return new CreatedRawDataFileIdResponse(
				rawDataFileRepository.save(rawDataFile).getId()
		);
	}

	@Override
	public RawDataFile getByFileId(UUID id) {
		
		return rawDataFileRepository.getById(id);
	}

}
