package com.mertdev.therawdata.bussines.concretes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mertdev.therawdata.bussines.abstracts.LibraryService;
import com.mertdev.therawdata.bussines.abstracts.RawDataService;
import com.mertdev.therawdata.bussines.abstracts.UserService;
import com.mertdev.therawdata.bussines.responses.GetLibraryItemResponse;
import com.mertdev.therawdata.bussines.responses.GetLibraryRawDataFileResponse;
import com.mertdev.therawdata.bussines.responses.GetLibraryRawDataResponse;
import com.mertdev.therawdata.dataAccess.abstracts.ShareRepository;
import com.mertdev.therawdata.entities.concretes.RawData;
import com.mertdev.therawdata.entities.concretes.RawDataFile;
import com.mertdev.therawdata.entities.concretes.Share;
import com.mertdev.therawdata.entities.concretes.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService{
	private final UserService userService;
	private final ShareRepository shareRepository;
	private final RawDataService rawDataService;
	private final S3Service s3Service;
	@Override
	public List<GetLibraryItemResponse> getMyPublicationsToMyLibrary(Pageable pageable) throws Exception {
		User user = userService.getCurrentUser(); 
		Page<Share> shares = shareRepository.findByUserUserIdOrderByCreationTime(user.getId(), pageable);
		if(shares.getSize() <= 0) {
			throw new Exception("No other publications found in the library"); 
		}
		List <GetLibraryItemResponse> responseLibraryItems = new ArrayList<>();
		for(Share share: shares) {
			GetLibraryItemResponse getLibraryItemResponse = new GetLibraryItemResponse();
			getLibraryItemResponse.setPostId(share.getPublicationPost().getId());
			getLibraryItemResponse.setPostTitle(share.getPublicationPost().getPublication().getTitle());
			getLibraryItemResponse.setPublicationType(share.getPublicationPost().getPublicationType());
			getLibraryItemResponse.setPublicationSummary(share.getPublicationPost().getPublication().getSummary());
			List<GetLibraryRawDataFileResponse> getLibraryRawDataFileResponses = new ArrayList<>(); 
			for(RawDataFile rawDataFile : share.getPublicationPost().getRawDataFile()) {
				GetLibraryRawDataFileResponse getLibraryRawDataFileResponse = new GetLibraryRawDataFileResponse();
				getLibraryRawDataFileResponse.setTitle(rawDataFile.getName());
				
				List<GetLibraryRawDataResponse> getLibraryRawDataResponses = new ArrayList<>();
				for(RawData rawData :  rawDataFile.getRawDatas()) {
					GetLibraryRawDataResponse getLibraryRawDataResponse = new GetLibraryRawDataResponse();
					getLibraryRawDataResponse.setId(rawData.getId());
					getLibraryRawDataResponse.setSize(rawData.getSize());
					getLibraryRawDataResponse.setTitle(rawData.getName());
					getLibraryRawDataResponse.setExtention(exSplit(rawData.getRawDataName()));
					getLibraryRawDataResponses.add(getLibraryRawDataResponse);
				}
				getLibraryRawDataFileResponse.setRawDatas(getLibraryRawDataResponses);
				getLibraryRawDataFileResponses.add(getLibraryRawDataFileResponse);
			}
			getLibraryItemResponse.setFiles(getLibraryRawDataFileResponses);
			responseLibraryItems.add(getLibraryItemResponse);
		}
		return responseLibraryItems;
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
	public byte[] getMyPublicationsToRawData(Long id) throws Exception {
		User user = userService.getCurrentUser();
		
		try {
			return rawDataService.getRawDataMyPublicationToLibrary(id, user);
		} catch (Exception e) {
			throw e;
		}
	}
}
