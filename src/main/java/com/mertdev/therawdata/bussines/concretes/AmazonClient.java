package com.mertdev.therawdata.bussines.concretes;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.appstream.model.User;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.mertdev.therawdata.bussines.abstracts.UserService;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class AmazonClient {
	private final UserService userService;
	 private AmazonS3 s3client;
	 
	    @Value("${s3.endpointUrl}")
	    private String endpointUrl;

	    @Value("${s3.bucketName}")
	    private String bucketName;

	    @Value("${s3.accessKeyId}")
	    private String accessKeyId;

	    @Value("${s3.secretKey}")
	    private String secretKey;

	    @Value("${s3.region}")
	    private String region;

	    @PostConstruct
	    private void initializeAmazon() {
	        AWSCredentials credentials = new BasicAWSCredentials(this.accessKeyId, this.secretKey);
	        this.s3client = AmazonS3ClientBuilder
	                .standard()
	                .withRegion(region)
	                .withCredentials(new AWSStaticCredentialsProvider(credentials))
	                .build();
	    }

	    public String uploadFile(MultipartFile multipartFile,String rawDataFileName, String rawDataName,String fileNameis) throws Exception {
	        try {
	        	
	            String fileUrl = "";
	            Path filePath = convertMultiPartToFile(multipartFile);
	            String fileName = generateFileName(rawDataFileName,rawDataName,fileNameis,multipartFile);
	            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
	            uploadFileToS3Bucket(fileName, filePath);
	            // Delete the temporary file
	            deleteTempFile(filePath);
	            return fileUrl;
	        } catch (Exception e) {
	            // Handle or log the exception
	            throw new Exception("Failed to upload file: " + e.getMessage());
	        }
	    }

	    public String deleteFileFromS3Bucket(String fileUrl) {
	        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
	        s3client.deleteObject(bucketName, fileName);
	        return "Successfully deleted";
	    }

	    
	    
	    
	    
	    private void uploadFileToS3Bucket(String fileName, Path filePath) {
	        s3client.putObject(bucketName, fileName, filePath.toFile());
	    }

	    private Path convertMultiPartToFile(MultipartFile file) throws IOException {
	        Path filePath = FileSystems.getDefault().getPath(file.getOriginalFilename());
	        try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
	            fos.write(file.getBytes());
	        }
	        return filePath;
	    }

	    private void deleteTempFile(Path filePath) {
	        try {
	            Files.delete(filePath);
	        } catch (IOException e) {
	            // Handle or log the exception
	            e.printStackTrace();
	        }
	    }

	    private String generateFileName(String rawDataFileName, String rawDataId,String fileName,MultipartFile multipartFile) {
	    	String email = userService.getCurrentUsername();
	        return email + "/" + rawDataFileName +"/"+ rawDataId + "/" + fileName + "-" + multipartFile.getOriginalFilename().replace(" ", "_");
	    }
}
