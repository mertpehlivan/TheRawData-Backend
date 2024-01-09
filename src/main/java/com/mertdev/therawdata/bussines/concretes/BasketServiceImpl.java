package com.mertdev.therawdata.bussines.concretes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.amazonaws.services.computeoptimizer.model.Summary;
import com.mertdev.therawdata.bussines.abstracts.BasketService;
import com.mertdev.therawdata.bussines.abstracts.UserService;
import com.mertdev.therawdata.bussines.responses.SummaryRawDataFileResponse;
import com.mertdev.therawdata.bussines.responses.SummaryRawDataResponse;
import com.mertdev.therawdata.dataAccess.abstracts.BasketRepository;
import com.mertdev.therawdata.dataAccess.abstracts.RawDataRepository;
import com.mertdev.therawdata.dataAccess.abstracts.UserRepository;
import com.mertdev.therawdata.entities.abstracts.Publication;
import com.mertdev.therawdata.entities.concretes.Basket;
import com.mertdev.therawdata.entities.concretes.PublicationPost;
import com.mertdev.therawdata.entities.concretes.RawData;
import com.mertdev.therawdata.entities.concretes.User;
import com.mertdev.therawdata.exceptions.BasketException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {
	private final BasketRepository basketRepository;
	private final UserService userService;
	private final UserRepository userRepository;
	private final RawDataRepository rawDataRepository;

	@Override
	public List<BasketResponse> getAllBasket() {
		try {
			User user = userRepository.findByEmail(userService.getCurrentUsername());
			Basket basket = user.getBasket();
			if (basket == null || basket.getRawDatas() == null) {
				System.out.println("Basket or rawDatas is null");
				return new ArrayList<>(); // Boş bir liste döndürülebilir
			}

			List<BasketResponse> tempBaskets = new ArrayList<>();

			List<SummaryRawDataFileResponse> tempFiles = new ArrayList<>();
			for (RawData rawData : basket.getRawDatas()) {
				findPublicationPost(tempBaskets, rawData.getRawDataFileId().getPublicationPostId());
				findRawDataFile(tempBaskets, rawData);
			}


			return tempBaskets;
		} catch (Exception e) {
			System.out.println(e);
			return new ArrayList<>();
		}
	}

	private void findPublicationPost(List<BasketResponse> tempBaskets, PublicationPost publicationPost) {
		BasketResponse findBasket = tempBaskets.stream()
				.filter(basket -> basket.getPublicationPostId() == publicationPost.getId()).findFirst().orElse(null);
		if (findBasket == null) {
			BasketResponse tempBasket = new BasketResponse();
			tempBasket.setPublicationPostId(publicationPost.getId());
			tempBasket.setTitle(publicationPost.getPublication().getTitle());
			tempBasket.setRawDataFile(new ArrayList<SummaryRawDataFileResponse>());
			tempBaskets.add(tempBasket);
		}
	}

	private void findRawDataFile(List<BasketResponse> tempBaskets, RawData rawData) {
		BasketResponse findBasket = tempBaskets.stream().filter(
				basket->basket.getPublicationPostId() == rawData.getRawDataFileId().getPublicationPostId().getId())
				.findFirst().orElse(null);
		if(findBasket == null) {
			System.out.println("Control");
		}
		SummaryRawDataFileResponse findFile = findBasket.getRawDataFile().stream()
				.filter(file -> file.getId().equals(rawData.getRawDataFileId().getId())).findFirst().orElse(null);
		if(findFile == null) {
			SummaryRawDataFileResponse tempFile = new SummaryRawDataFileResponse();
			tempFile.setFilesLenght(0);
			tempFile.setId(rawData.getRawDataFileId().getId());
			tempFile.setTitle(rawData.getRawDataFileId().getName());
			tempFile.setRawDatas(new ArrayList<SummaryRawDataResponse>());
			tempFile.getRawDatas().add(createSummaryRawData(rawData));
			findBasket.getRawDataFile().add(tempFile);
		}else {
			findFile.getRawDatas().add(createSummaryRawData(rawData));
		}
	}

	private SummaryRawDataResponse createSummaryRawData(RawData rawData) {
		SummaryRawDataResponse tempRawData = new SummaryRawDataResponse();
		tempRawData.setComment(rawData.getComment());
		tempRawData.setId(rawData.getId());
		tempRawData.setPreviewImageUrl(rawData.getPreviewImageName());
		tempRawData.setPrice(rawData.getPrice());
		tempRawData.setRawDataExtension(exSplit(rawData.getPreviewImageName()));
		tempRawData.setRawDataLengt(0);
		tempRawData.setTitle(rawData.getName());
		return tempRawData;
	}

	@Override
	public BasketResponse getRawDataForPublicationFromBasket(UUID id) {
		try {
			List<BasketResponse> basketPublications = getAllBasket();
			BasketResponse basketPublication = basketPublications.stream()
					.filter(publication -> Objects.equals(publication.getPublicationPostId(), id)).findFirst()
					.orElse(null);
			return basketPublication;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("An error occurred while getting raw data for publication from basket");
		}
	}

	@Override
	public String addBasket(Long id) {
		try {
			User user = userService.getCurrentUser();

			// Kullanıcının sepetini kontrol et
			Basket existingBasket = user.getBasket();
			if (existingBasket == null) {
				// Kullanıcının henüz sepeti yoksa yeni bir sepet oluştur
				existingBasket = new Basket();
				existingBasket.setUser(user);
				existingBasket.setRawDatas(new HashSet<>());
			}

			RawData rawData = rawDataRepository.getById(id);

			// Sepetin rawDatas alanına veriyi ekle
			existingBasket.getRawDatas().add(rawData);

			// Basket'i veritabanına kaydet
			basketRepository.save(existingBasket);

			return "Added raw data to basket";
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return "Failed to add raw data to basket";
		}
	}

	@Override
	public String deleteBasket(Long id) {
	    try {
	        String currentUsername = userService.getCurrentUsername();
	        if (currentUsername == null) {
	            return "Error getting current username.";
	        }

	        User user = userRepository.findByEmail(currentUsername);
	        if (user == null) {
	            return "User not found with email: " + currentUsername;
	        }

	        Basket basket = user.getBasket();
	        if (basket == null) {
	            return "User does not have a basket.";
	        }

	        basketRepository.removeRawDataFromBasket(basket.getId(), id);
	        return "Raw data removed from the basket successfully.";

	    } catch (Exception e) {
	        return "Error deleting raw data from the basket: " + e.getMessage();
	    }
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

	@Override
	public Boolean existsByRawData(Long id) {
		User user = userRepository.findByEmail(userService.getCurrentUsername());
		RawData rawdata = user.getBasket().getRawDatas().stream().filter(rawData -> rawData.getId() == id).findFirst()
				.orElse(null);
		if (rawdata == null) {
			return false;
		}
		return true;
	}

	@Override
	public Integer getPrice() {
	    User user = userRepository.findByEmail(userService.getCurrentUsername());

	    // Basket null kontrolü eklenmeli
	    if (user.getBasket() == null) {
	        return 0;
	    }

	    return user.getBasket()
	               .getRawDatas()
	               .stream()
	               .mapToInt(RawData::getPrice) 
	               .sum();
	}

}
