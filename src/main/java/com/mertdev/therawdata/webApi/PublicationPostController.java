package com.mertdev.therawdata.webApi;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mertdev.therawdata.bussines.abstracts.PublicationPostService;
import com.mertdev.therawdata.bussines.responses.GetPostResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/publicationPost")
@AllArgsConstructor
public class PublicationPostController {

    private final PublicationPostService publicationPostService;

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
    public List<GetPostResponse> getAllByUniqueName(@PathVariable String uniqueName) {
        return publicationPostService.getAllByUniqueName(uniqueName);
    }
    @GetMapping("/{publicationPostId}")
    public GetPostResponse getPost(@PathVariable UUID publicationPostId) {
    	return publicationPostService.getPost(publicationPostId);
    }
}
