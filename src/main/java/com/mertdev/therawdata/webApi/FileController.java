package com.mertdev.therawdata.webApi;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mertdev.therawdata.bussines.abstracts.UserService;
import com.mertdev.therawdata.bussines.concretes.S3Service;
import com.mertdev.therawdata.bussines.requests.ProfileImageRequest;
import com.mertdev.therawdata.dataAccess.abstracts.UserRepository;
import com.mertdev.therawdata.entities.concretes.User;

import io.jsonwebtoken.io.IOException;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/files")
@AllArgsConstructor
public class FileController {
	private final S3Service s3Service;
	private UserService userService;
	private UserRepository userRepository;

	@PostMapping("/profileImage")
	@ResponseBody
	public ResponseEntity<String> handleFileProfileImageUpload(ProfileImageRequest file) {
		System.out.println(file.getAvatar().getOriginalFilename());
		try {
			UUID randomName = UUID.randomUUID();
			User user = userService.getCurrentUser();
			
			if(user.getProfileImageName() != null) {
				s3Service.deleteObject("profileImage/%s".formatted(user.getProfileImageName()));
			}
			
			
			String imageName = "%s.%s".
					formatted(randomName, exSplit(file.getAvatar().getOriginalFilename()));
			
			try {
				
				s3Service.putObject("profileImage/%s"
						.formatted(imageName), file.getAvatar().getBytes());
				
				user.setProfileImageName(imageName);
				userRepository.save(user);
				
			} catch (java.io.IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ResponseEntity.ok("File uploaded successfully: " + imageName);
		} catch (IOException e) {
			return ResponseEntity.status(500).body("Error uploading the file.");
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

}
