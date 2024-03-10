package com.mertdev.therawdata.webApi;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mertdev.therawdata.bussines.abstracts.PublicationAuthorService;
import com.mertdev.therawdata.bussines.abstracts.PublicationPostService;
import com.mertdev.therawdata.bussines.responses.GetPostResponse;
import com.mertdev.therawdata.bussines.responses.GetSearchPostResponse;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/publicationPost")
@AllArgsConstructor
public class PublicationPostController {

    private final PublicationPostService publicationPostService;
    private final PublicationAuthorService publicationAuthorService;

    @GetMapping("/getAll")
    public ResponseEntity<List<GetPostResponse>> getAllPost(Pageable pageable) {
    	System.out.println(pageable);
        List<GetPostResponse> list = publicationPostService.getAllPost(pageable);
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{uniqueName}/getAll")
    public List<GetPostResponse> getAllByUniqueName(@PathVariable String uniqueName,Pageable pageable) {
    	System.out.println("Download page :" + pageable.getPageSize());
        return publicationPostService.getAllByUniqueName(uniqueName,null,pageable);
    }
    @GetMapping("/{publicationPostId}")
    public GetPostResponse getPost(@PathVariable UUID publicationPostId) {
    	return publicationPostService.getPost(publicationPostId);
    }
    @GetMapping("/addAuthorPost/{publicationPostId}/{invitationId}")
    public ResponseEntity<String> addAuthorPost(@PathVariable String publicationPostId,@PathVariable Long invitationId) {
        System.out.println(publicationPostId);
        
        if (publicationPostId == null || publicationPostId.isEmpty()) {
   
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Publication post ID cannot be empty or null.");
        }

        try {
            UUID publicationPostUUID = UUID.fromString(publicationPostId);
            publicationAuthorService.addAuthorPost(publicationPostUUID,invitationId);
            System.out.println(publicationPostUUID);

            return ResponseEntity.ok("Author post added successfully.");
        } catch (IllegalArgumentException e) {
        
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid UUID format: " + e.getMessage());
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
    @GetMapping("/getFollowerPost")
    public List<GetPostResponse> getFollowerPost(Pageable pageable){
    	return publicationPostService.getFollowingUserPost(pageable);
    }
    @GetMapping("/searchByPublicationTitle")
    public ResponseEntity<List<GetSearchPostResponse>> getSearchPost(
            @RequestParam String title,
            Pageable pageable
    ) {
    	System.out.println(title);
        try {
            List<GetSearchPostResponse> responses = publicationPostService.getSearchPost(title, pageable);
            System.out.println(responses);
            return ResponseEntity.ok(responses);
        } catch (IllegalArgumentException e) {
            // Handle invalid input parameters
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(500).body(null);
        }
    }
    

}
