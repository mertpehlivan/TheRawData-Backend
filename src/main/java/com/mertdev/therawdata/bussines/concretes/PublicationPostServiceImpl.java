package com.mertdev.therawdata.bussines.concretes;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mertdev.therawdata.bussines.abstracts.PublicationPostService;
import com.mertdev.therawdata.bussines.abstracts.UserService;
import com.mertdev.therawdata.bussines.requests.CreateArticleRequest;
import com.mertdev.therawdata.bussines.requests.CreateChapterInABookRequest;
import com.mertdev.therawdata.bussines.requests.CreateCompanyTestReportRequest;
import com.mertdev.therawdata.bussines.requests.CreateConferencePaperRequest;
import com.mertdev.therawdata.bussines.requests.CreateReasearchProjectRequest;
import com.mertdev.therawdata.bussines.requests.CreateThesisRequest;
import com.mertdev.therawdata.bussines.responses.AuthorResponse;
import com.mertdev.therawdata.bussines.responses.GetPostResponse;
import com.mertdev.therawdata.bussines.responses.GetSearchPostResponse;
import com.mertdev.therawdata.bussines.responses.InvitationsResponse;
import com.mertdev.therawdata.bussines.responses.NotificationResponse;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;
import com.mertdev.therawdata.bussines.responses.PublicationPostResponse;
import com.mertdev.therawdata.bussines.responses.SummaryRawDataFileResponse;
import com.mertdev.therawdata.bussines.responses.SummaryRawDataResponse;
import com.mertdev.therawdata.core.utilities.mappers.abstracts.PublicationAuthorToResponse;
import com.mertdev.therawdata.core.utilities.mappers.abstracts.PublicationToResponse;
import com.mertdev.therawdata.core.utilities.mappers.abstracts.RawDataFileToResponse;
import com.mertdev.therawdata.dataAccess.abstracts.InvitationRepository;
import com.mertdev.therawdata.dataAccess.abstracts.NotificationRepository;
import com.mertdev.therawdata.dataAccess.abstracts.PublicationPostRepository;
import com.mertdev.therawdata.dataAccess.abstracts.ShareRepository;
import com.mertdev.therawdata.dataAccess.abstracts.UserRepository;
import com.mertdev.therawdata.entities.abstracts.Publication;
import com.mertdev.therawdata.entities.abstracts.PublicationType;
import com.mertdev.therawdata.entities.concretes.Article;
import com.mertdev.therawdata.entities.concretes.ChapterInBook;
import com.mertdev.therawdata.entities.concretes.CompanyTestReport;
import com.mertdev.therawdata.entities.concretes.ConferencePaper;
import com.mertdev.therawdata.entities.concretes.Invitations;
import com.mertdev.therawdata.entities.concretes.Notification;
import com.mertdev.therawdata.entities.concretes.PdfFile;
import com.mertdev.therawdata.entities.concretes.PublicationAuthor;
import com.mertdev.therawdata.entities.concretes.PublicationPost;
import com.mertdev.therawdata.entities.concretes.RawData;
import com.mertdev.therawdata.entities.concretes.RawDataFile;
import com.mertdev.therawdata.entities.concretes.ResearchProject;
import com.mertdev.therawdata.entities.concretes.Share;
import com.mertdev.therawdata.entities.concretes.Thesis;
import com.mertdev.therawdata.entities.concretes.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublicationPostServiceImpl implements PublicationPostService {
	private final PublicationPostRepository repository;
	private final UserRepository userRepository;
	private final UserService userService;
	private final PublicationToResponse publicationToResponse;
	private final RawDataFileToResponse dataFileToResponse;
	private final PublicationAuthorToResponse authorToResponse;
	private final NotificationRepository notificationRepository;
	private final SimpMessagingTemplate messagingTemplate;
	private final InvitationRepository invitationRepository;
	private final ShareRepository shareRepository;
	private final S3Service s3Service;

	@Override
	public PublicationPost findPost(UUID id) {
		return repository.getById(id);
	}

	private void pdfUploud(PublicationPost data, MultipartFile file) {
		try {
			if (file != null) {
				s3Service.putObject(
						"%s/%s/pdf/%s".formatted(data.getUser().getId(), data.getId(), file.getOriginalFilename()),
						file.getBytes());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public PostIdResponse createPublication(Article article, CreateArticleRequest createArticleRequest) {
		String email = userService.getCurrentUsername();
		User user = userRepository.findAllByEmail(email).orElseThrow();
		PublicationPost publicationPost = new PublicationPost();
		publicationPost.setPublication(article);
		publicationPost.setUser(user);
		publicationPost.setPublicationType("Article");
		PdfFile pdfFile = new PdfFile();
		if (createArticleRequest.getPdfStatus()) {
			pdfFile.setAddOnly(createArticleRequest.getAddOnly());
			pdfFile.setPdfExtension(createArticleRequest.getFileEx());
			pdfFile.setPdfFileName(createArticleRequest.getPdfFile().getOriginalFilename());
			pdfFile.setPdfStatus(createArticleRequest.getPdfStatus());
		} else {
			pdfFile.setPdfStatus(createArticleRequest.getPdfStatus());
		}
		publicationPost.setPdfFile(pdfFile);

		PublicationPost data = repository.save(publicationPost);
		if (createArticleRequest.getPdfStatus()) {
			pdfUploud(data, createArticleRequest.getPdfFile());
		}

		Share share = new Share();
		share.setPublicationPost(data);
		share.setUser(user);
		shareRepository.save(share);
		sendNotificationToUser(user, data.getPublication().getTitle(),
				"%s %s".formatted(user.getFirstname(), user.getLastname()), data.getId());
		sendToInvitations(data, createArticleRequest.getAuthors());
		PostIdResponse idResponse = new PostIdResponse();
		idResponse.setId(data.getId());
		return idResponse;

	}

	@Override
	public PostIdResponse createPublication(ChapterInBook chapterInBook,
			CreateChapterInABookRequest chapterInABookRequest) {
		String email = userService.getCurrentUsername();
		User user = userRepository.findAllByEmail(email).orElseThrow();
		PublicationPost publicationPost = new PublicationPost();
		publicationPost.setPublication(chapterInBook);
		publicationPost.setUser(user);
		publicationPost.setPublicationType("Chapter in Book");
		PdfFile pdfFile = new PdfFile();
		if (chapterInABookRequest.getPdfStatus()) {
			pdfFile.setAddOnly(chapterInABookRequest.getAddOnly());
			pdfFile.setPdfExtension(chapterInABookRequest.getFileEx());
			pdfFile.setPdfFileName(chapterInABookRequest.getPdfFile().getOriginalFilename());
			pdfFile.setPdfStatus(chapterInABookRequest.getPdfStatus());
		} else {
			pdfFile.setPdfStatus(chapterInABookRequest.getPdfStatus());
		}
		publicationPost.setPdfFile(pdfFile);
		PublicationPost data = repository.save(publicationPost);
		if (chapterInABookRequest.getPdfStatus()) {
			pdfUploud(data, chapterInABookRequest.getPdfFile());
		}

		Share share = new Share();
		share.setPublicationPost(data);
		share.setUser(user);
		shareRepository.save(share);
		sendNotificationToUser(user, data.getPublication().getTitle(),
				"%s %s".formatted(user.getFirstname(), user.getLastname()), data.getId());
		sendToInvitations(data, chapterInABookRequest.getAuthors());
		PostIdResponse idResponse = new PostIdResponse();
		idResponse.setId(data.getId());
		return idResponse;

	}

	@Override
	public PostIdResponse createPublication(ConferencePaper conferencePaper,
			CreateConferencePaperRequest conferencePaperRequest) {
		String email = userService.getCurrentUsername();
		User user = userRepository.findAllByEmail(email).orElseThrow();
		PublicationPost publicationPost = new PublicationPost();
		publicationPost.setPublication(conferencePaper);
		publicationPost.setUser(user);
		publicationPost.setPublicationType("Conference Paper");
		PdfFile pdfFile = new PdfFile();
		if (conferencePaperRequest.getPdfStatus()) {
			pdfFile.setAddOnly(conferencePaperRequest.getAddOnly());
			pdfFile.setPdfExtension(conferencePaperRequest.getFileEx());
			pdfFile.setPdfFileName(conferencePaperRequest.getPdfFile().getOriginalFilename());
			pdfFile.setPdfStatus(conferencePaperRequest.getPdfStatus());
		} else {
			pdfFile.setPdfStatus(conferencePaperRequest.getPdfStatus());
		}
		publicationPost.setPdfFile(pdfFile);
		PublicationPost data = repository.save(publicationPost);
		if (conferencePaperRequest.getPdfStatus()) {
			pdfUploud(data, conferencePaperRequest.getPdfFile());
		}

		Share share = new Share();
		share.setPublicationPost(data);
		share.setUser(user);
		shareRepository.save(share);
		sendNotificationToUser(user, data.getPublication().getTitle(),
				"%s %s".formatted(user.getFirstname(), user.getLastname()), data.getId());

		sendToInvitations(data, conferencePaperRequest.getAuthors());
		PostIdResponse idResponse = new PostIdResponse();
		idResponse.setId(data.getId());
		return idResponse;
	}

	@Override
	public PostIdResponse createPublication(Thesis thesis, CreateThesisRequest createThesisRequest) {
		String email = userService.getCurrentUsername();
		User user = userRepository.findAllByEmail(email).orElseThrow();
		PublicationPost publicationPost = new PublicationPost();
		publicationPost.setPublication(thesis);
		publicationPost.setUser(user);
		publicationPost.setPublicationType("Thesis");
		PdfFile pdfFile = new PdfFile();
		if (createThesisRequest.getPdfStatus()) {
			pdfFile.setAddOnly(createThesisRequest.getAddOnly());
			pdfFile.setPdfExtension(createThesisRequest.getFileEx());
			pdfFile.setPdfFileName(createThesisRequest.getPdfFile().getOriginalFilename());
			pdfFile.setPdfStatus(createThesisRequest.getPdfStatus());
		} else {
			pdfFile.setPdfStatus(createThesisRequest.getPdfStatus());
		}
		publicationPost.setPdfFile(pdfFile);
		PublicationPost data = repository.save(publicationPost);
		if (createThesisRequest.getPdfStatus()) {
			pdfUploud(data, createThesisRequest.getPdfFile());
		}

		Share share = new Share();
		share.setPublicationPost(data);
		share.setUser(user);
		shareRepository.save(share);
		sendNotificationToUser(user, data.getPublication().getTitle(),
				"%s %s".formatted(user.getFirstname(), user.getLastname()), data.getId());

		sendToInvitations(data, createThesisRequest.getAuthors());

		PostIdResponse idResponse = new PostIdResponse();
		idResponse.setId(data.getId());
		return idResponse;
	}

	@Override
	public PostIdResponse createPublication(ResearchProject reasearchProject,
			CreateReasearchProjectRequest createReasearchProjectRequest) {
		String email = userService.getCurrentUsername();
		User user = userRepository.findAllByEmail(email).orElseThrow();
		PublicationPost publicationPost = new PublicationPost();
		publicationPost.setPublication(reasearchProject);
		publicationPost.setUser(user);
		publicationPost.setPublicationType("Research Project");
		PdfFile pdfFile = new PdfFile();
		if (createReasearchProjectRequest.getPdfStatus()) {
			pdfFile.setAddOnly(createReasearchProjectRequest.getAddOnly());
			pdfFile.setPdfExtension(createReasearchProjectRequest.getFileEx());
			pdfFile.setPdfFileName(createReasearchProjectRequest.getPdfFile().getOriginalFilename());
			pdfFile.setPdfStatus(createReasearchProjectRequest.getPdfStatus());
		} else {
			pdfFile.setPdfStatus(createReasearchProjectRequest.getPdfStatus());
		}
		publicationPost.setPdfFile(pdfFile);
		PublicationPost data = repository.save(publicationPost);
		if (createReasearchProjectRequest.getPdfStatus()) {
			pdfUploud(data, createReasearchProjectRequest.getPdfFile());
		}

		Share share = new Share();
		share.setPublicationPost(data);
		share.setUser(user);
		shareRepository.save(share);
		sendNotificationToUser(user, data.getPublication().getTitle(),
				"%s %s".formatted(user.getFirstname(), user.getLastname()), data.getId());
		sendToInvitations(data, createReasearchProjectRequest.getAuthors());
		PostIdResponse idResponse = new PostIdResponse();
		idResponse.setId(data.getId());
		return idResponse;
	}

	@Override
	public PostIdResponse createPublication(CompanyTestReport companyTestReport,
			CreateCompanyTestReportRequest createCompanyTestReportRequest) {
		String email = userService.getCurrentUsername();
		User user = userRepository.findAllByEmail(email).orElseThrow();
		PublicationPost publicationPost = new PublicationPost();
		publicationPost.setPublication(companyTestReport);
		publicationPost.setUser(user);
		publicationPost.setPublicationType("Company Test Report");
		PdfFile pdfFile = new PdfFile();
		if (createCompanyTestReportRequest.getPdfStatus()) {
			pdfFile.setAddOnly(createCompanyTestReportRequest.getAddOnly());
			pdfFile.setPdfExtension(createCompanyTestReportRequest.getFileEx());
			pdfFile.setPdfFileName(createCompanyTestReportRequest.getPdfFile().getOriginalFilename());
			pdfFile.setPdfStatus(createCompanyTestReportRequest.getPdfStatus());
		} else {
			pdfFile.setPdfStatus(createCompanyTestReportRequest.getPdfStatus());
		}
		publicationPost.setPdfFile(pdfFile);
		PublicationPost data = repository.save(publicationPost);
		if (createCompanyTestReportRequest.getPdfStatus()) {
			pdfUploud(data, createCompanyTestReportRequest.getPdfFile());
		}

		Share share = new Share();
		share.setPublicationPost(data);
		share.setUser(user);
		shareRepository.save(share);
		sendNotificationToUser(user, data.getPublication().getTitle(),
				"%s %s".formatted(user.getFirstname(), user.getLastname()), data.getId());
		sendToInvitations(data, createCompanyTestReportRequest.getAuthors());
		PostIdResponse idResponse = new PostIdResponse();
		idResponse.setId(data.getId());
		return idResponse;
	}

	private void sendNotificationToUser(User user, String title, String fullName, UUID publicationId) {
		for (User tempUser : user.getFollowers()) {

			String destination = "/topic/%s/notifications".formatted(tempUser.getId());
			NotificationResponse notificationResponse = new NotificationResponse();
			notificationResponse.setContent("The author you follow has published a thesis titled ");
			notificationResponse.setFullName(fullName);
			notificationResponse.setPublicationLink("/publications/%s".formatted(publicationId));
			notificationResponse.setPublicationTitle(title);
			notificationResponse.setUserLink("/%s".formatted(user.getUniqueName()));
			notificationResponse.setType("publication");
			notificationResponse.setStatus(false);
			messagingTemplate.convertAndSend(destination, notificationResponse);
			Notification notification = new Notification();
			notification.setContent("The author you follow has published a thesis titled ");
			notification.setFullName(fullName);
			notification.setPublicationLink("/publications/%s".formatted(publicationId));
			notification.setPublicationTitle(title);
			notification.setUserLink("/%s".formatted(user.getUniqueName()));
			notification.setType("publication");
			notification.setUser(tempUser);
			notification.setStatus(false);

			notificationRepository.save(notification);
		}

	}

	private void sendToInvitations(PublicationPost data, List<String> authors) {
		for (String author : authors) {
			Optional<User> user = userRepository.findById(UUID.fromString(author));
			InvitationsResponse invitationsResponse = new InvitationsResponse();

			Invitations invitation = new Invitations();
			invitation.setAuthorId(null);
			invitation.setFullName("%s %s".formatted(data.getUser().getFirstname(), data.getUser().getLastname()));
			invitation.setPublicationUrl("/publications/%s".formatted(data.getId()));
			invitation.setStatus(null);
			invitation.setTitle(data.getPublication().getTitle());
			invitation.setUser(user.get());
			invitation.setUserUrl("/%s".formatted(data.getUser().getUniqueName()));
			invitation.setContant(
					"Mehmet Akın Added you as the author in his publication titled 'Walking Car Acceleration.' Would you like to share this on your page?");
			invitation.setPublicationId(data.getId());
			Invitations invitationSaved = invitationRepository.save(invitation);

			String destination = "/topic/%s/invitations".formatted(author);
			invitationsResponse.setId(invitationSaved.getId());
			invitationsResponse.setTitle(data.getPublication().getTitle());
			invitationsResponse
					.setFullName("%s %s".formatted(data.getUser().getFirstname(), data.getUser().getLastname()));
			invitationsResponse.setUserId(UUID.fromString(author));
			invitationsResponse.setPublicationUrl("/publications/%s".formatted(data.getId()));
			invitationsResponse.setContant(
					"Mehmet Akın Added you as the author in his publication titled 'Walking Car Acceleration.' Would you like to share this on your page?");
			invitationsResponse.setUserUrl("/%s".formatted(data.getUser().getUniqueName()));
			invitationsResponse.setPublicationId(data.getId());

			messagingTemplate.convertAndSend(destination, invitationsResponse);

		}
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
	public List<GetPostResponse> getAllPost(Pageable pageable) {
		List<Share> shares = shareRepository.findAllByOrderByCreationTimeDesc(pageable);
		List<GetPostResponse> tempPosts = new ArrayList<>();
		for (Share publication : shares) {
			GetPostResponse tempPost = new GetPostResponse();
			List<AuthorResponse> tempAuthors = new ArrayList<>();
			if (publication.getUser().getId() == publication.getPublicationPost().getUser().getId()) {
				tempPost.setShareFullName(null);
				tempPost.setShareUniqueName(null);
				tempPost.setShareUserId(null);
				tempPost.setShareProfileImage(null);
				tempPost.setAddOnly(publication.getPublicationPost().getPdfFile().getAddOnly());
				tempPost.setPdfStatus(publication.getPublicationPost().getPdfFile().getPdfStatus());
				tempPost.setPdfFileName(publication.getPublicationPost().getPdfFile().getPdfFileName());
			} else {
				tempPost.setShareFullName(
						"%s %s".formatted(publication.getUser().getFirstname(), publication.getUser().getLastname()));
				tempPost.setShareUniqueName(publication.getUser().getUniqueName());
				tempPost.setShareUserId(publication.getUser().getId());
				tempPost.setShareProfileImage(publication.getUser().getProfileImageName());

			}
			for (PublicationAuthor author : publication.getPublicationPost().getPublication().getPublicationAuthors()) {
				tempAuthors.add(authorToResponse.toResponse(author));
			}
			tempPost.setAuthors(tempAuthors);
			tempPost.setCreationTime(timeTage(publication.getCreationTime()));
			tempPost.setFullname("%s %s".formatted(publication.getPublicationPost().getUser().getFirstname(),
					publication.getPublicationPost().getUser().getLastname()

			));
			tempPost.setProfileImage(publication.getPublicationPost().getUser().getProfileImageName());
			tempPost.setUniqueName(publication.getPublicationPost().getUser().getUniqueName());
			tempPost.setUserId(publication.getPublicationPost().getUser().getId());
			tempPost.setComment(publication.getPublicationPost().getPublication().getComment());
			tempPost.setId(publication.getPublicationPost().getId());
			tempPost.setPublicationType(publicationType(publication.getPublicationPost().getPublication().getObject()));
			List<SummaryRawDataFileResponse> tempRawFiles = new ArrayList<>();
			for (RawDataFile file : publication.getPublicationPost().getRawDataFile()) {
				SummaryRawDataFileResponse tempRawDataFileResponse = new SummaryRawDataFileResponse();
				tempRawDataFileResponse.setTitle(file.getName());
				tempRawDataFileResponse.setId(file.getId());
				List<SummaryRawDataResponse> rawDataResponses = new ArrayList<>();
				for (RawData rawData : file.getRawDatas()) {
					SummaryRawDataResponse tempRawData = new SummaryRawDataResponse();
					System.out.println(rawData.getId());
					tempRawData.setId(rawData.getId());
					tempRawData.setPreviewImageUrl(rawData.getPreviewImageName());
					tempRawData.setTitle(rawData.getName());
					tempRawData.setRawDataExtension(exSplit(rawData.getRawDataName()));
					tempRawData.setRawDataLengt(file.getRawDatas().size());
					tempRawData.setComment(rawData.getComment());
					tempRawData.setPrice(rawData.getPrice());
					rawDataResponses.add(tempRawData);
				}
				tempRawDataFileResponse.setRawDatas(rawDataResponses);
				tempRawDataFileResponse.setFilesLenght(publication.getPublicationPost().getRawDataFile().size());
				tempRawFiles.add(tempRawDataFileResponse);
			}
			System.out.println(tempRawFiles.isEmpty() ? null : tempRawFiles.get(0));
			tempPost.setRawdatafiles(tempRawFiles);
			tempPost.setTitle(publication.getPublicationPost().getPublication().getTitle());
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
	public GetPostResponse getPost(UUID publicationId) {
		String imageUrl = "";
		PublicationPost publication = repository.getById(publicationId);

		GetPostResponse tempPost = new GetPostResponse();
		List<AuthorResponse> tempAuthors = new ArrayList<>();
		for (PublicationAuthor author : publication.getPublication().getPublicationAuthors()) {
			tempAuthors.add(authorToResponse.toResponse(author));
		}
		tempPost.setAuthors(tempAuthors);
		tempPost.setCreationTime(timeTage(publication.getCreationTime()));
		tempPost.setFullname("%s %s".formatted(publication.getUser().getFirstname(), publication.getUser().getLastname()

		));
		tempPost.setProfileImage(publication.getUser().getProfileImageName());
		tempPost.setSummary(publication.getPublication().getSummary());
		tempPost.setUniqueName(publication.getUser().getUniqueName());
		tempPost.setUserId(publication.getUser().getId());
		tempPost.setComment(publication.getPublication().getComment());
		tempPost.setId(publication.getId());
		tempPost.setPublicationType(publicationType(publication.getPublication().getObject()));
		tempPost.setAddOnly(publication.getPdfFile().getAddOnly());
		tempPost.setPdfStatus(publication.getPdfFile().getPdfStatus());
		tempPost.setPdfFileName(publication.getPdfFile().getPdfFileName());
		List<SummaryRawDataFileResponse> tempRawFiles = new ArrayList<>();
		for (RawDataFile file : publication.getRawDataFile()) {
			SummaryRawDataFileResponse tempRawDataFileResponse = new SummaryRawDataFileResponse();
			tempRawDataFileResponse.setTitle(file.getName());
			tempRawDataFileResponse.setId(file.getId());
			List<SummaryRawDataResponse> rawDataResponses = new ArrayList<>();
			for (RawData rawData : file.getRawDatas()) {
				SummaryRawDataResponse tempRawData = new SummaryRawDataResponse();
				tempRawData.setId(rawData.getId());
				tempRawData.setPreviewImageUrl(rawData.getPreviewImageName());
				tempRawData.setTitle(rawData.getName());
				tempRawData.setRawDataExtension(exSplit(rawData.getRawDataName()));
				tempRawData.setRawDataLengt(file.getRawDatas().size());
				tempRawData.setComment(rawData.getComment());
				tempRawData.setPrice(rawData.getPrice());
				rawDataResponses.add(tempRawData);
			}
			tempRawDataFileResponse.setRawDatas(rawDataResponses);
			tempRawDataFileResponse.setFilesLenght(publication.getRawDataFile().size());
			tempRawFiles.add(tempRawDataFileResponse);
		}
		System.out.println(tempRawFiles.isEmpty() ? null : tempRawFiles.get(0));
		tempPost.setRawdatafiles(tempRawFiles);
		tempPost.setTitle(publication.getPublication().getTitle());

		return tempPost;
	}

	@Override
	public List<GetPostResponse> getAllByUniqueName(String uniqueName, String publicationType, Pageable pageable) {
		Page<Share> shares = new PageImpl<>(List.of(), PageRequest.of(0, 10), 0);
		if (publicationType == null) {
			shares = shareRepository.findByUserUniqueNameOrderByCreationTime(uniqueName, pageable);
		} else {
			shares = shareRepository.findByPublicationTypeAndUserUniqueNameOrderByCreationTime(publicationType,
					uniqueName, pageable);
		}

		List<GetPostResponse> tempPosts = new ArrayList<>();

		for (Share publication : shares) {
			GetPostResponse tempPost = new GetPostResponse();
			List<AuthorResponse> tempAuthors = new ArrayList<>();
			if (publication.getUser().getId() == publication.getPublicationPost().getUser().getId()) {
				tempPost.setShareFullName(null);
				tempPost.setShareUniqueName(null);
				tempPost.setShareUserId(null);
				tempPost.setShareProfileImage(null);
			} else {
				tempPost.setShareFullName(
						"%s %s".formatted(publication.getUser().getFirstname(), publication.getUser().getLastname()));
				tempPost.setShareUniqueName(publication.getUser().getUniqueName());
				tempPost.setShareUserId(publication.getUser().getId());
				tempPost.setShareProfileImage(publication.getUser().getProfileImageName());
			}
			for (PublicationAuthor author : publication.getPublicationPost().getPublication().getPublicationAuthors()) {
				tempAuthors.add(authorToResponse.toResponse(author));
			}
			tempPost.setAuthors(tempAuthors);
			tempPost.setCreationTime(timeTage(publication.getCreationTime()));
			tempPost.setFullname("%s %s".formatted(publication.getPublicationPost().getUser().getFirstname(),
					publication.getPublicationPost().getUser().getLastname()

			));
			tempPost.setProfileImage(publication.getPublicationPost().getUser().getProfileImageName());
			tempPost.setUniqueName(publication.getPublicationPost().getUser().getUniqueName());
			tempPost.setUserId(publication.getPublicationPost().getUser().getId());
			tempPost.setComment(publication.getPublicationPost().getPublication().getComment());
			tempPost.setId(publication.getPublicationPost().getId());
			tempPost.setPublicationType(publicationType(publication.getPublicationPost().getPublication().getObject()));
			tempPost.setAddOnly(publication.getPublicationPost().getPdfFile().getAddOnly());
			tempPost.setPdfStatus(publication.getPublicationPost().getPdfFile().getPdfStatus());
			tempPost.setPdfFileName(publication.getPublicationPost().getPdfFile().getPdfFileName());
			List<SummaryRawDataFileResponse> tempRawFiles = new ArrayList<>();
			for (RawDataFile file : publication.getPublicationPost().getRawDataFile()) {
				SummaryRawDataFileResponse tempRawDataFileResponse = new SummaryRawDataFileResponse();
				tempRawDataFileResponse.setTitle(file.getName());
				tempRawDataFileResponse.setId(file.getId());
				List<SummaryRawDataResponse> rawDataResponses = new ArrayList<>();
				for (RawData rawData : file.getRawDatas()) {
					SummaryRawDataResponse tempRawData = new SummaryRawDataResponse();
					System.out.println(rawData.getId());
					tempRawData.setId(rawData.getId());
					tempRawData.setPreviewImageUrl(rawData.getPreviewImageName());
					tempRawData.setTitle(rawData.getName());
					tempRawData.setRawDataExtension(exSplit(rawData.getRawDataName()));
					tempRawData.setRawDataLengt(file.getRawDatas().size());
					tempRawData.setComment(rawData.getComment());
					tempRawData.setPrice(rawData.getPrice());
					rawDataResponses.add(tempRawData);
				}
				tempRawDataFileResponse.setRawDatas(rawDataResponses);
				tempRawDataFileResponse.setFilesLenght(publication.getPublicationPost().getRawDataFile().size());
				tempRawFiles.add(tempRawDataFileResponse);
			}
			System.out.println(tempRawFiles.isEmpty() ? null : tempRawFiles.get(0));
			tempPost.setRawdatafiles(tempRawFiles);
			tempPost.setTitle(publication.getPublicationPost().getPublication().getTitle());
			tempPosts.add(tempPost);
		}
		return tempPosts;
	}

	@Override
	public List<GetSearchPostResponse> getSearchPost(String title, Pageable pageable) {
		Page<PublicationPost> posts = repository.findByPublicationTitleContainingIgnoreCase(title, pageable);
		List<GetSearchPostResponse> responses = new ArrayList<>();
		for (PublicationPost post : posts) {
			GetSearchPostResponse response = new GetSearchPostResponse();
			response.setId(post.getId());
			response.setShareUserFullName(
					"%s %s".formatted(post.getUser().getFirstname(), post.getUser().getLastname()));
			response.setShareUserUniqueName(post.getUser().getUniqueName());
			response.setTitle(post.getPublication().getTitle());
			response.setType(publicationType(post.getPublication()));
			responses.add(response);
		}
		return responses;
	}
	
	@Override
	public List<GetPostResponse> getFollowingUserPost(Pageable pageable) {
		User user = userService.getCurrentUser();
		Page<Share> shares = shareRepository.findByUserOrderByCreationTime(user.getFollowing(), pageable);
		List<GetPostResponse> tempPosts = new ArrayList<>();

		for (Share publication : shares) {
			GetPostResponse tempPost = new GetPostResponse();
			List<AuthorResponse> tempAuthors = new ArrayList<>();
			if (publication.getUser().getId() == publication.getPublicationPost().getUser().getId()) {
				tempPost.setShareFullName(null);
				tempPost.setShareUniqueName(null);
				tempPost.setShareUserId(null);
				tempPost.setShareProfileImage(null);
			} else {
				tempPost.setShareFullName(
						"%s %s".formatted(publication.getUser().getFirstname(), publication.getUser().getLastname()));
				tempPost.setShareUniqueName(publication.getUser().getUniqueName());
				tempPost.setShareUserId(publication.getUser().getId());
				tempPost.setShareProfileImage(publication.getUser().getProfileImageName());
			}
			for (PublicationAuthor author : publication.getPublicationPost().getPublication().getPublicationAuthors()) {
				tempAuthors.add(authorToResponse.toResponse(author));
			}
			tempPost.setAuthors(tempAuthors);
			tempPost.setCreationTime(timeTage(publication.getCreationTime()));
			tempPost.setFullname("%s %s".formatted(publication.getPublicationPost().getUser().getFirstname(),
					publication.getPublicationPost().getUser().getLastname()

			));
			tempPost.setProfileImage(publication.getPublicationPost().getUser().getProfileImageName());
			tempPost.setUniqueName(publication.getPublicationPost().getUser().getUniqueName());
			tempPost.setUserId(publication.getPublicationPost().getUser().getId());
			tempPost.setComment(publication.getPublicationPost().getPublication().getComment());
			tempPost.setId(publication.getPublicationPost().getId());
			tempPost.setPublicationType(publicationType(publication.getPublicationPost().getPublication().getObject()));
			List<SummaryRawDataFileResponse> tempRawFiles = new ArrayList<>();
			for (RawDataFile file : publication.getPublicationPost().getRawDataFile()) {
				SummaryRawDataFileResponse tempRawDataFileResponse = new SummaryRawDataFileResponse();
				tempRawDataFileResponse.setTitle(file.getName());
				tempRawDataFileResponse.setId(file.getId());
				List<SummaryRawDataResponse> rawDataResponses = new ArrayList<>();
				for (RawData rawData : file.getRawDatas()) {
					SummaryRawDataResponse tempRawData = new SummaryRawDataResponse();
					System.out.println(rawData.getId());
					tempRawData.setId(rawData.getId());
					tempRawData.setPreviewImageUrl(rawData.getPreviewImageName());
					tempRawData.setTitle(rawData.getName());
					tempRawData.setRawDataExtension(exSplit(rawData.getRawDataName()));
					tempRawData.setRawDataLengt(file.getRawDatas().size());
					tempRawData.setComment(rawData.getComment());
					tempRawData.setPrice(rawData.getPrice());
					rawDataResponses.add(tempRawData);
				}
				tempRawDataFileResponse.setRawDatas(rawDataResponses);
				tempRawDataFileResponse.setFilesLenght(publication.getPublicationPost().getRawDataFile().size());
				tempRawFiles.add(tempRawDataFileResponse);
			}
			System.out.println(tempRawFiles.isEmpty() ? null : tempRawFiles.get(0));
			tempPost.setRawdatafiles(tempRawFiles);
			tempPost.setTitle(publication.getPublicationPost().getPublication().getTitle());
			tempPosts.add(tempPost);
		}
		return tempPosts;

	}

	@Override
	public List<GetPostResponse> getAllPost(List<Publication> items) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GetPostResponse> getAllByUniqueName(String uniqueName) {
		// TODO Auto-generated method stub
		return null;
	}

}
