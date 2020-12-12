package com.episen.workerconverter.services;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
@Service
public class AmazonService {

    private AmazonS3 s3client;

    @Autowired
    ResourceLoader resourceLoader;

    //@Value("${amazonProperties.endpointUrl}")
    private static String endpointUrl="https://s3.eu-west-2.amazonaws.com";
    //@Value("${amazonProperties.bucketName}")
    private String bucketName="kadiaguillaume-s3";
    //@Value("${amazonProperties.accessKey}")
    private String accessKey = "AKIAJXWNESBNOQ737X5A";
    //@Value("${amazonProperties.secretKey}")
    private String secretKey= "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";

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

    public void putObject(File img, String id){
        this.s3client.putObject(
                bucketName,
                id+".jpg",
                img
        );
    }

    /*public String saveFile (BufferedImage multipartFile) {
        String objectKey = new StringBuilder ()
                .append (bucketName)
                .toString ();
        WritableResource writableResource = (WritableResource) resourceLoader.getResource (objectKey);
        try (InputStream inputStream = multipartFile.getInputStream();
             OutputStream outputStream = writableResource.getOutputStream ()) {
            IOUtils.copy (inputStream, outputStream);
        } catch (IOException e) {
            e.printStackTrace ();
        }
        return objectKey;
    }*/

}
