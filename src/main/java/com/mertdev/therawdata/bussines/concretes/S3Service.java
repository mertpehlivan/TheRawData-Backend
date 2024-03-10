package com.mertdev.therawdata.bussines.concretes;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.S3Object;

@Service
public class S3Service {
	private final S3Client s3Client;

	@Value("${s3.bucketName}")
	private String bucketName;

	public S3Service(S3Client sS3Client) {
		this.s3Client = sS3Client;
	}

	public void putObject(String key, byte[] file) {
		PutObjectRequest objectRequest = PutObjectRequest.builder().bucket(bucketName).key(key).build();

		s3Client.putObject(objectRequest, RequestBody.fromBytes(file));
	}

	public void deleteObject(String key) {
		DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(bucketName).key(key).build();

		s3Client.deleteObject(deleteObjectRequest);

	}

	public byte[] getObject(String key) {
		GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(key).build();
		ResponseInputStream<GetObjectResponse> res = s3Client.getObject(getObjectRequest);

		try {
			return res.readAllBytes();
		} catch (IOException e) {
			return null;
		}
	}

	public void listObjectsDelete(String key) {
		try {
			ListObjectsRequest listObjects = ListObjectsRequest.builder().bucket(bucketName).prefix(key).build();

			ListObjectsResponse res = s3Client.listObjects(listObjects);
			List<S3Object> objects = res.contents();

			for (S3Object object : objects) {
				s3Client.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(object.key()).build());
				System.out.println("Object deleted: " + object.key());
			}

		} catch (S3Exception e) {
			System.err.println(e.awsErrorDetails().errorMessage());
			System.exit(1);
		}
	}

	private static long calKb(Long val) {
		return val / 1024;
	}

	public ResponseInputStream<GetObjectResponse> downloadObject(String key) throws IOException {
		GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(key).build();
		ResponseInputStream<GetObjectResponse> res = s3Client.getObject(getObjectRequest);

		return res;
	}

}
