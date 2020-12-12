package com.episen.frontconverter.services;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

@Slf4j
@Service
public class AmazonService {

    private AmazonS3 s3client;

    //@Value("${amazonProperties.endpointUrl}")
    private static String endpointUrl="https://s3.eu-west-2.amazonaws.com";
    //@Value("${amazonProperties.bucketName}")
    private String bucketName="kadiaguillaume-s3";
    //@Value("${amazonProperties.accessKey}")

    //access key and secretKey deleted because are repository is public
    private String accessKey = "";
    //@Value("${amazonProperties.secretKey}")
    private String secretKey= "";

    private AmazonService() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_WEST_2)
                .build();


        if(s3client.doesBucketExist(bucketName)) {
            log.info("Bucket name is not available."
                    + " Try again with a different Bucket name.");
            return;
        }
        s3client.createBucket(bucketName);
    }

    public BufferedImage getImageFromBucketById(String id) {
        //list files
       /*
        ObjectListing objectListing = s3client.listObjects(bucketName);
        for(S3ObjectSummary os : objectListing.getObjectSummaries()) {
            log.info(os.getKey());
        }

        */
        BufferedImage image = null;
        if (isImageInBucket(id)) {

            try {
                //Download
                S3Object s3object = s3client.getObject(bucketName, id+".png");
                S3ObjectInputStream inputStream = s3object.getObjectContent();

                //FileUtils.copyInputStreamToFile(inputStream, new File(".././imgs/1.png"));
                image = ImageIO.read(inputStream);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        return image;
    }

    public boolean isImageInBucket(String id){

        try {
            //Download
            S3Object s3object = s3client.getObject(bucketName, id+".png");
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }
}
