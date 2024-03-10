package com.mertdev.therawdata.bussines.concretes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.amazonaws.services.alexaforbusiness.model.NotFoundException;
import com.mertdev.therawdata.bussines.abstracts.PublicationAuthorService;
import com.mertdev.therawdata.bussines.abstracts.UserService;
import com.mertdev.therawdata.dataAccess.abstracts.InvitationRepository;
import com.mertdev.therawdata.dataAccess.abstracts.PublicationAuthorRepository;
import com.mertdev.therawdata.dataAccess.abstracts.PublicationPostRepository;
import com.mertdev.therawdata.dataAccess.abstracts.ShareRepository;
import com.mertdev.therawdata.dataAccess.abstracts.UserRepository;
import com.mertdev.therawdata.entities.abstracts.Publication;
import com.mertdev.therawdata.entities.concretes.PublicationAuthor;
import com.mertdev.therawdata.entities.concretes.PublicationPost;
import com.mertdev.therawdata.entities.concretes.Share;
import com.mertdev.therawdata.entities.concretes.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PublicationAuthorServiceImpl implements PublicationAuthorService {
	private final UserService userService;
	private final PublicationAuthorRepository publicationAuthorRepository;
	private final UserRepository userRepository;
	private final PublicationPostRepository publicationPostRepository;
	private final ShareRepository shareRepository;
	private final InvitationRepository invitationRepository;
	@Override public void createAuthor(List<String> authorIds, Publication publication) {
		List<UUID> uuids = convertToUUID(authorIds);
		System.out.println(uuids);

		List<User> users = userRepository.findAllById(uuids);

		List<PublicationAuthor> publicationAuthors = new ArrayList<PublicationAuthor>();

		for (User user : users) {
			PublicationAuthor tempAuthor = new PublicationAuthor();
			tempAuthor.setPublication(publication);
			tempAuthor.setAuthor(user);
			publicationAuthors.add(tempAuthor);

		}

		publicationAuthorRepository.saveAll(publicationAuthors);
	}

	private List<UUID> convertToUUID(List<String> strings) {
		return strings.stream().map(UUID::fromString).collect(Collectors.toList());
	}

	@Override
	public void addAuthorPost(UUID publicationPostId,Long id) {
	    try {
	        if (publicationPostId == null) {
	            throw new IllegalArgumentException("PublicationPostId cannot be null.");
	        }

	        User user = userService.getCurrentUser();
	        PublicationPost existingPost = searchValue(user.getPublicationAuthor(), publicationPostId);
	        if(existingPost != null) {
	        	Share share = new Share();
	    		share.setPublicationPost(existingPost);
	    		share.setUser(user);

	        	shareRepository.save(share);
	        	
	        	invitationRepository.deleteById(id);
	        }
	        
	    } catch (IllegalArgumentException e) {
	        handleException("IllegalArgumentException occurred:", e);
	    } catch (NotFoundException e) {
	        handleException("NotFoundException occurred:", e);
	    } catch (Exception e) {
	        handleException("An unexpected error occurred:", e);
	    }
	}



	private PublicationPost searchValue(List<PublicationAuthor> authors, UUID targetPostId) {
	    try {
	        Optional<PublicationPost> optionalPost = authors.stream()
	                .filter(value -> value.getPublication().getPublicationPost().getId().equals(targetPostId))
	                .findFirst()
	                .map(author -> author.getPublication().getPublicationPost());

	        return optionalPost.orElse(null);
	    } catch (Exception e) {
	        handleException("Error while searching for PublicationPost:", e);
	        return null;
	    }
	}

	private void handleException(String message, Exception e) {
	    // Loglama veya uygun bir işlem gerçekleştirilebilir
	    e.printStackTrace();
	    // Ayrıca, isteğe bağlı olarak loglama çerçevelerini (örneğin, SLF4J veya Log4j) kullanabilirsiniz.
	    // logger.error(message, e);
	}




}
