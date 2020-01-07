//package com.bridgelabz.fundoo_notes.configurations;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.amazonaws.auth.AWSCredentialsProvider;
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.regions.Region;
//import com.amazonaws.regions.Regions;
//
//
//
//
//@Configuration
//public class AwsConfig {
//	
//	    private String awsKeyId = System.getenv("awsKeyId");
//
//	    private String awsKeySecret = System.getenv("awsKeySecret");
//
//	    private String awsRegion = System.getenv("awsRegion");
//
//	    private String awsS3Bucket = System.getenv("awsS3Bucket");
//
//	    @Bean(name = "awsRegion")
//	    public Region getAWSPollyRegion() {
//	        return Region.getRegion(Regions.fromName(awsRegion));
//	    }
//
//	    @Bean(name = "awsCredentialsProvider")
//	    public AWSCredentialsProvider getAWSCredentials() {
//	        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(this.awsKeyId, this.awsKeySecret);
//	        return new AWSStaticCredentialsProvider(awsCredentials);
//	    }
//
//	    @Bean(name = "awsS3Bucket")
//	    public String getAWSS3AudioBucket() {
//	        return awsS3Bucket;
//	    }
//
//}
