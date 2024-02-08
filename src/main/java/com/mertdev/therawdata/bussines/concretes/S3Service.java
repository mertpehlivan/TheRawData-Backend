package com.mertdev.therawdata.bussines.concretes;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class S3Service {
	private final S3Client s3Client;
	
	 @Value("${s3.bucketName}")
	 private String bucketName;
	 
	
	public S3Service(S3Client sS3Client) {
		this.s3Client = sS3Client;
	}
	
	public void putObject(String key, byte[] file) {
		PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
		
		s3Client.putObject(objectRequest, RequestBody.fromBytes(file));
	}
	public void deleteObject(String key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.deleteObject(deleteObjectRequest);

	}
	public byte[] getObject(String key) {
		GetObjectRequest getObjectRequest = GetObjectRequest.builder()
				.bucket(bucketName)
				.key(key)
				.build();
			ResponseInputStream<GetObjectResponse> res = s3Client.getObject(getObjectRequest);
		try {
			return res.readAllBytes();
		} catch (IOException e) {
			return null;
		}
	}
	
	
}
