package com.mertdev.therawdata.webApi;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mertdev.therawdata.bussines.concretes.S3Service;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth/test")
@AllArgsConstructor
public class TestContoller {
	private final S3Service s3Service;
	@PostMapping(
				value="{customerId}/profile-image",
				consumes = MediaType.MULTIPART_FORM_DATA_VALUE
			)
	public void uploadCustomerProfileImage(
				@PathVariable("customerId") Integer customerId,
				@RequestParam("file") MultipartFile file
	) {
		try {
			s3Service.putObject(
					"profile-images/%s/%s.png".formatted(customerId,UUID.randomUUID().toString()), 
					file.getBytes()
			);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@GetMapping(value = "/pdf/previewPdfImage.png")
	public ResponseEntity<byte[]> getFirstPageOfPdf() {
	    try {
	        byte[] pdfData = s3Service.getObject("hasan@gmail.com/9696885b-e74a-40ca-9222-20877875b11e/c9a6b109-50b7-4b1d-a666-11d6480e8332/rawData/be42323e-1006-4e15-8c5d-ec9fa292cb5e.pdf");

	        // Convert InputStream to PDDocument
	        try (PDDocument document = PDDocument.load(pdfData)) {
	            // Create PDFRenderer object
	            PDFRenderer pdfRenderer = new PDFRenderer(document);

	            // Render the first page to BufferedImage
	            BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(0, 300);

	            // Convert BufferedImage to byte array
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();
	            ImageIO.write(bufferedImage, "png", baos);
	            byte[] imageBytes = baos.toByteArray();

	            // Set response content type as image/png
	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(MediaType.IMAGE_PNG);

	            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
	        }
	    } catch (IOException e) {
	        e.printStackTrace(); // Log the exception details for debugging
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

}