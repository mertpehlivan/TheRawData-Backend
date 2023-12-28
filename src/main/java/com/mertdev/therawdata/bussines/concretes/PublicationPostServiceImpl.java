package com.mertdev.therawdata.bussines.concretes;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.mertdev.therawdata.bussines.abstracts.PublicationPostService;
import com.mertdev.therawdata.bussines.abstracts.UserService;
import com.mertdev.therawdata.bussines.responses.AuthorResponse;
import com.mertdev.therawdata.bussines.responses.GetPostResponse;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;
import com.mertdev.therawdata.bussines.responses.PublicationPostResponse;
import com.mertdev.therawdata.bussines.responses.SummaryRawDataFileResponse;
import com.mertdev.therawdata.bussines.responses.SummaryRawDataResponse;
import com.mertdev.therawdata.core.utilities.mappers.abstracts.PublicationToResponse;
import com.mertdev.therawdata.core.utilities.mappers.abstracts.RawDataFileToResponse;
import com.mertdev.therawdata.dataAccess.abstracts.PublicationPostRepository;
import com.mertdev.therawdata.dataAccess.abstracts.UserRepository;
import com.mertdev.therawdata.entities.abstracts.Publication;
import com.mertdev.therawdata.entities.abstracts.PublicationType;
import com.mertdev.therawdata.entities.concretes.Article;
import com.mertdev.therawdata.entities.concretes.ChapterInBook;
import com.mertdev.therawdata.entities.concretes.CompanyTestReport;
import com.mertdev.therawdata.entities.concretes.ConferencePaper;
import com.mertdev.therawdata.entities.concretes.PublicationAuthor;
import com.mertdev.therawdata.entities.concretes.PublicationPost;
import com.mertdev.therawdata.entities.concretes.RawData;
import com.mertdev.therawdata.entities.concretes.RawDataFile;
import com.mertdev.therawdata.entities.concretes.ResearchProject;
import com.mertdev.therawdata.entities.concretes.Thesis;
import com.mertdev.therawdata.entities.concretes.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PublicationPostServiceImpl implements PublicationPostService {
	private final PublicationPostRepository repository;
	private final UserRepository userRepository;
	private final UserService userService;
	private final PublicationToResponse publicationToResponse;
	private final RawDataFileToResponse dataFileToResponse;

	@Override
	public PublicationPost findPost(UUID id) {
		return repository.getById(id);
	}

	@Override
	public PostIdResponse createPublication(Article article) {
		String email = userService.getCurrentUsername();
		User user = userRepository.findAllByEmail(email).orElseThrow();
		PublicationPost publicationPost = new PublicationPost();
		publicationPost.setPublication(article);
		publicationPost.setUser(user);
		PublicationPost data = repository.save(publicationPost);
		PostIdResponse idResponse = new PostIdResponse();
		idResponse.setId(data.getId());
		return idResponse;

	}

	public PostIdResponse createPublication(ChapterInBook chapterInBook) {
		String email = userService.getCurrentUsername();
		User user = userRepository.findAllByEmail(email).orElseThrow();
		PublicationPost publicationPost = new PublicationPost();
		publicationPost.setPublication(chapterInBook);
		publicationPost.setUser(user);
		PublicationPost data = repository.save(publicationPost);
		PostIdResponse idResponse = new PostIdResponse();
		idResponse.setId(data.getId());
		return idResponse;

	}

	@Override
	public PostIdResponse createPublication(ConferencePaper conferencePaper) {
		String email = userService.getCurrentUsername();
		User user = userRepository.findAllByEmail(email).orElseThrow();
		PublicationPost publicationPost = new PublicationPost();
		publicationPost.setPublication(conferencePaper);
		publicationPost.setUser(user);
		PublicationPost data = repository.save(publicationPost);
		PostIdResponse idResponse = new PostIdResponse();
		idResponse.setId(data.getId());
		return idResponse;
	}

	@Override
	public PostIdResponse createPublication(Thesis thesis) {
		String email = userService.getCurrentUsername();
		User user = userRepository.findAllByEmail(email).orElseThrow();
		PublicationPost publicationPost = new PublicationPost();
		publicationPost.setPublication(thesis);
		publicationPost.setUser(user);
		PublicationPost data = repository.save(publicationPost);
		PostIdResponse idResponse = new PostIdResponse();
		idResponse.setId(data.getId());
		return idResponse;
	}

	@Override
	public PostIdResponse createPublication(ResearchProject reasearchProject) {
		String email = userService.getCurrentUsername();
		User user = userRepository.findAllByEmail(email).orElseThrow();
		PublicationPost publicationPost = new PublicationPost();
		publicationPost.setPublication(reasearchProject);
		publicationPost.setUser(user);
		PublicationPost data = repository.save(publicationPost);
		PostIdResponse idResponse = new PostIdResponse();
		idResponse.setId(data.getId());
		return idResponse;
	}

	@Override
	public PostIdResponse createPublication(CompanyTestReport companyTestReport) {
		String email = userService.getCurrentUsername();
		User user = userRepository.findAllByEmail(email).orElseThrow();
		PublicationPost publicationPost = new PublicationPost();
		publicationPost.setPublication(companyTestReport);
		publicationPost.setUser(user);
		PublicationPost data = repository.save(publicationPost);
		PostIdResponse idResponse = new PostIdResponse();
		idResponse.setId(data.getId());
		return idResponse;
	}

	@Override
	public <T> List<PublicationPostResponse> getAll(List<T> items) {
		List<PublicationPostResponse> response = new ArrayList<PublicationPostResponse>();
		for (T item : items) {
			PublicationPostResponse tempPublication = new PublicationPostResponse();
			;
			if (item instanceof Article) {
				tempPublication.setPublicationResponse(publicationToResponse.toResponse((Article) item));
			} else if (item instanceof ChapterInBook) {
				tempPublication.setPublicationResponse(publicationToResponse.toResponse((ChapterInBook) item));
			} else if (item instanceof CompanyTestReport) {
				tempPublication.setPublicationResponse(publicationToResponse.toResponse((CompanyTestReport) item));
			} else if (item instanceof ConferencePaper) {
				tempPublication.setPublicationResponse(publicationToResponse.toResponse((ConferencePaper) item));
			} else if (item instanceof ResearchProject) {
				tempPublication.setPublicationResponse(publicationToResponse.toResponse((ResearchProject) item));
			} else if (item instanceof Thesis) {
				tempPublication.setPublicationResponse(publicationToResponse.toResponse((Thesis) item));
			}

			tempPublication.setRawDataFiles(
					dataFileToResponse.toResponse(((Publication) item).getPublicationPost().getRawDataFile()));
			response.add(tempPublication);

		}

		return response;
	}

	@Override
	public List<PublicationType> getAllPublication() {
		List<PublicationPost> publications = repository.findAllByOrderByCreationTimeDesc();
		List<PublicationType> publicationResponse = new ArrayList<>();
		for (PublicationPost publication : publications) {
			publicationResponse.add(publication.getPublication().getObject());
		}
		return publicationResponse;
	}

	@Override
	public List<GetPostResponse> getAllPost() {

		String imageUrl = "https://instagram.fesb10-3.fna.fbcdn.net/v/t51.2885-19/363492081_1011405263192366_6566209395961409989_n.jpg?stp=dst-jpg_s320x320&_nc_ht=instagram.fesb10-3.fna.fbcdn.net&_nc_cat=102&_nc_ohc=lnFQWH874UgAX927y_I&edm=AOQ1c0wBAAAA&ccb=7-5&oh=00_AfDymHKijPBRwsqJCm4epSdBkhfp_oJV5T_DsN1d_SqubA&oe=658369C1&_nc_sid=8b3546";

		List<PublicationPost> publications = repository.findAllByOrderByCreationTimeDesc();
		List<GetPostResponse> tempPosts = new ArrayList<>();
		for (PublicationPost publication : publications) {
			GetPostResponse tempPost = new GetPostResponse();
			List<AuthorResponse> tempAuthors = new ArrayList<>();
			for (PublicationAuthor author : publication.getPublication().getPublicationAuthors()) {
				AuthorResponse tempAuthor = new AuthorResponse();
				tempAuthor.setFirstname(author.getAuthor().getFirstname());
				tempAuthor.setLastname(author.getAuthor().getLastname());
				tempAuthor.setProfileImageUrl(imageUrl);

				tempAuthors.add(tempAuthor);
			}

			tempPost.setAuthors(tempAuthors);
			tempPost.setCreationTime(timeTage(publication.getCreationTime()));
			tempPost.setFullname(
					"%s %s".formatted(publication.getUser().getFirstname(), publication.getUser().getLastname()

					));
			tempPost.setUniqueName(publication.getUser().getUniqueName());
			tempPost.setUserId(publication.getUser().getId());
			tempPost.setComment(publication.getPublication().getComment());
			tempPost.setId(publication.getId());
			tempPost.setPublicationType(publicationType(publication.getPublication().getObject()));
			List<SummaryRawDataFileResponse> tempRawFiles = new ArrayList<>();
			for (RawDataFile file : publication.getRawDataFile()) {
				SummaryRawDataFileResponse tempRawDataFileResponse = new SummaryRawDataFileResponse();
				tempRawDataFileResponse.setTitle(file.getName());
				List<SummaryRawDataResponse> rawDataResponses = new ArrayList<>();
				for (RawData rawData : file.getRawDatas()) {
					SummaryRawDataResponse tempRawData = new SummaryRawDataResponse();
					tempRawData.setPreviewImageUrl(rawData.getPreviewImageName());
					tempRawData.setTitle(rawData.getName());
					tempRawData.setRawDataExtension(exSplit(rawData.getRawDataName()));
					tempRawData.setRawDataLengt(file.getRawDatas().size());
					rawDataResponses.add(tempRawData);
				}
				tempRawDataFileResponse.setRawDatas(rawDataResponses);
				tempRawDataFileResponse.setFilesLenght(publication.getRawDataFile().size());
				tempRawFiles.add(tempRawDataFileResponse);
			}
			System.out.println(tempRawFiles.isEmpty() ? null : tempRawFiles.get(0));
			tempPost.setRawdatafile(tempRawFiles.isEmpty() ? null : tempRawFiles.get(0));
			tempPost.setTitle(publication.getPublication().getTitle());
			tempPosts.add(tempPost);
		}
		return tempPosts;
	}

	private String publicationType(Object item) {
		if (item instanceof Article) {
			return "Article";
		} else if (item instanceof ChapterInBook) {
			return "Chapter In Book";
		} else if (item instanceof CompanyTestReport) {
			return "Company Test Report";
		} else if (item instanceof ConferencePaper) {
			return "Conference Paper";
		} else if (item instanceof ResearchProject) {
			return "Research Project";
		} else if (item instanceof Thesis) {
			return "Thesis";
		}
		return null;
	}

	private String timeTage(Date creationTime) {
		Date startDate = creationTime;

		Instant currentInstant = Instant.now();
		Date endDate = Date.from(currentInstant);

		LocalDateTime startDateTime = LocalDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault());
		LocalDateTime endDateTime = LocalDateTime.ofInstant(endDate.toInstant(), ZoneId.systemDefault());
		Period period = Period.between(startDateTime.toLocalDate(), endDateTime.toLocalDate());
		Duration duration = Duration.between(startDateTime, endDateTime);

		long days = period.getDays();
		long months = period.getMonths();
		long years = period.getYears();
		long hours = duration.toHours();
		long minutes = duration.toMinutes() % 60;
		long secounds = duration.getSeconds();

		if (years != 0) {
			return years + "y";
		} else if (months != 0) {
			return months + "mo ";
		} else if (days != 0) {
			return days + "d ";
		} else if (hours != 0) {
			return hours + "h ";
		} else if (minutes != 0) {
			return minutes + "m";
		} else if (secounds != 0) {
			return secounds + "s";
		}
		return "";
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
	public List<GetPostResponse> getAllPost(List<Publication> items) {
		String imageUrl = "https://instagram.fesb10-3.fna.fbcdn.net/v/t51.2885-19/363492081_1011405263192366_6566209395961409989_n.jpg?stp=dst-jpg_s320x320&_nc_ht=instagram.fesb10-3.fna.fbcdn.net&_nc_cat=102&_nc_ohc=lnFQWH874UgAX927y_I&edm=AOQ1c0wBAAAA&ccb=7-5&oh=00_AfDymHKijPBRwsqJCm4epSdBkhfp_oJV5T_DsN1d_SqubA&oe=658369C1&_nc_sid=8b3546";

		List<PublicationPost> publications = new ArrayList<>();
		for (Publication item : items) {
			publications.add(item.getPublicationPost());
		}
		List<GetPostResponse> tempPosts = new ArrayList<>();
		for (PublicationPost publication : publications) {
			GetPostResponse tempPost = new GetPostResponse();
			List<AuthorResponse> tempAuthors = new ArrayList<>();
			for (PublicationAuthor author : publication.getPublication().getPublicationAuthors()) {
				AuthorResponse tempAuthor = new AuthorResponse();
				tempAuthor.setFirstname(author.getAuthor().getFirstname());
				tempAuthor.setLastname(author.getAuthor().getLastname());
				tempAuthor.setProfileImageUrl(imageUrl);

				tempAuthors.add(tempAuthor);
			}

			tempPost.setAuthors(tempAuthors);
			tempPost.setCreationTime(timeTage(publication.getCreationTime()));
			tempPost.setFullname(
					"%s %s".formatted(publication.getUser().getFirstname(), publication.getUser().getLastname()

					));
			tempPost.setUniqueName(publication.getUser().getUniqueName());
			tempPost.setUserId(publication.getUser().getId());
			tempPost.setComment(publication.getPublication().getComment());
			tempPost.setId(publication.getId());
			tempPost.setPublicationType(publicationType(publication.getPublication().getObject()));
			List<SummaryRawDataFileResponse> tempRawFiles = new ArrayList<>();
			for (RawDataFile file : publication.getRawDataFile()) {
				SummaryRawDataFileResponse tempRawDataFileResponse = new SummaryRawDataFileResponse();
				tempRawDataFileResponse.setTitle(file.getName());
				List<SummaryRawDataResponse> rawDataResponses = new ArrayList<>();
				for (RawData rawData : file.getRawDatas()) {
					SummaryRawDataResponse tempRawData = new SummaryRawDataResponse();
					tempRawData.setPreviewImageUrl(rawData.getPreviewImageName());
					tempRawData.setTitle(rawData.getName());
					tempRawData.setRawDataExtension(exSplit(rawData.getRawDataName()));
					tempRawData.setRawDataLengt(file.getRawDatas().size());
					rawDataResponses.add(tempRawData);
				}
				tempRawDataFileResponse.setRawDatas(rawDataResponses);
				tempRawDataFileResponse.setFilesLenght(publication.getRawDataFile().size());
				tempRawFiles.add(tempRawDataFileResponse);
			}
			System.out.println(tempRawFiles.isEmpty() ? null : tempRawFiles.get(0));
			tempPost.setRawdatafile(tempRawFiles.isEmpty() ? null : tempRawFiles.get(0));
			tempPost.setTitle(publication.getPublication().getTitle());
			tempPosts.add(tempPost);
		}
		return tempPosts;
	}

	@Override
	public List<GetPostResponse> getAllByUniqueName(String uniqueName) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
