package com.mertdev.therawdata.bussines.concretes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.mertdev.therawdata.bussines.abstracts.PublicationAuthorService;
import com.mertdev.therawdata.dataAccess.abstracts.PublicationAuthorRepository;
import com.mertdev.therawdata.dataAccess.abstracts.UserRepository;
import com.mertdev.therawdata.entities.abstracts.Publication;
import com.mertdev.therawdata.entities.concretes.PublicationAuthor;
import com.mertdev.therawdata.entities.concretes.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PublicationAuthorServiceImpl implements PublicationAuthorService {
	private final PublicationAuthorRepository publicationAuthorRepository;
	private final UserRepository userRepository;
	@Override
	public void createAuthor(List<String> authorIds, Publication publication) {
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

}
