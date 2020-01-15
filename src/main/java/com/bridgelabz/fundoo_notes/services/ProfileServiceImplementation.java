package com.bridgelabz.fundoo_notes.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.bridgelabz.fundoo_notes.Entity.Profile;
import com.bridgelabz.fundoo_notes.Entity.UserInformation;
import com.bridgelabz.fundoo_notes.repository.IUserRepository;
import com.bridgelabz.fundoo_notes.repository.ProfilePicRepository;
import com.bridgelabz.fundoo_notes.util.JwtGenerator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProfileServiceImplementation implements ProfileService {

	@Autowired
	private ProfilePicRepository profilePicRepository;

	@Autowired
	private JwtGenerator tokenGenerator;

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private RedisTemplate<String, Object> redis;

	
	private String bucketName =System.getenv("bucketName");

	@Autowired
	private AmazonS3 amazonS3Client;

	public Profile storeObjectInS3(MultipartFile file, String fileName, String contentType, String token) {
		try {
			Long userId = getRedisCecheId(token);
			UserInformation user = userRepository.getUserById(userId);
			if (user != null) {
				Profile profile = new Profile(fileName, user);
				ObjectMetadata objectMetadata = new ObjectMetadata();
				objectMetadata.setContentType(contentType);
				objectMetadata.setContentLength(file.getSize());

				amazonS3Client.putObject(bucketName, fileName, file.getInputStream(), objectMetadata);
				profilePicRepository.save(profile);
				return profile;
			}
		} catch (AmazonClientException | IOException exception) {
			throw new RuntimeException("Error while uploading file.");
		}
		return null;

	}

	public S3Object fetchObject(String awsFileName) {
		S3Object s3Object;
		try {
			s3Object = amazonS3Client.getObject(new GetObjectRequest(bucketName, awsFileName));
		} catch (AmazonServiceException serviceException) {
			serviceException.printStackTrace();

			throw new RuntimeException("Error while streaming File.");
		} catch (AmazonClientException exception) {
			exception.printStackTrace();
			throw new RuntimeException("Error while streaming File.");
		}
		return s3Object;
	}

	public void deleteObject(String key) {
		try {
			amazonS3Client.deleteObject(bucketName, key);
		} catch (AmazonServiceException serviceException) {
			log.error(serviceException.getErrorMessage());
		} catch (AmazonClientException exception) {
			log.error("Error while deleting File.");
		}
	}

	private Long getRedisCecheId(String token) throws JWTVerificationException, IllegalArgumentException, UnsupportedEncodingException {
		String[] splitedToken = token.split("\\.");
		String redisTokenKey = splitedToken[1] + splitedToken[2];
		if (redis.opsForValue().get(redisTokenKey) == null) {
			Long idForRedis = tokenGenerator.parseJWT(token);
			log.info("idForRedis is :" + idForRedis);
			redis.opsForValue().set(redisTokenKey, idForRedis, 2 * 60, TimeUnit.SECONDS);
		}
		Long userId = (Long) redis.opsForValue().get(redisTokenKey);
		return userId;
	}

	@Override
	public Profile updateObejctInS3(MultipartFile file, String originalFilename, String contentType, String token) {
		try {
			Long userId = getRedisCecheId(token);
			UserInformation user = userRepository.getUserById(userId);
			Profile profile = profilePicRepository.findByUserId(userId);
			if (user != null && profile != null) {

				deleteObject(profile.getProfilePicName());
				profilePicRepository.delete(profile);
				ObjectMetadata objectMetadata = new ObjectMetadata();
				objectMetadata.setContentType(contentType);
				objectMetadata.setContentLength(file.getSize());

				amazonS3Client.putObject(bucketName, originalFilename, file.getInputStream(), objectMetadata);
				profilePicRepository.save(profile);
				return profile;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public S3Object getProfilePic(String token) {

		try {
			Long userId = getRedisCecheId(token);
			UserInformation user = userRepository.getUserById(userId);
			if (user != null) {
				Profile profile = profilePicRepository.findByUserId(userId);
				if (profile != null) {
					return fetchObject(profile.getProfilePicName());
				} else {
					return null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}