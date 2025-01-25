package com.hms.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.hms.Entity.Images;
import com.hms.Entity.Property;
import com.hms.Payload.ImageDto;
import com.hms.Repository.ImagesRepository;
import com.hms.Repository.PropertyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageService{

    @Autowired
    private AmazonS3 amazonS3;
    private PropertyRepository propertyRepository;
    private ImagesRepository imagesRepository;
    private ModelMapper modelMapper;

    public ImageService(PropertyRepository propertyRepository, ImagesRepository imagesRepository, ModelMapper modelMapper) {
        this.propertyRepository = propertyRepository;
        this.imagesRepository = imagesRepository;
        this.modelMapper = modelMapper;
    }
    public ImageDto mapToDto(Images images){
        ImageDto dto=modelMapper.map(images,ImageDto.class);
        return dto;
    }

    public String uploadFile(MultipartFile file, String bucketName) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }
        try {
            File convFile = new File(System.getProperty("java.io.tmpdir")
                    + "/"
                    + file.getOriginalFilename());
            file.transferTo(convFile);
            try {
                amazonS3.putObject(bucketName, convFile.getName(), convFile);
                return amazonS3.getUrl(bucketName, file.getOriginalFilename()).toString();
            } catch (AmazonS3Exception s3Exception) {
                return "Unable to upload file :" + s3Exception.getMessage();
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to upload file", e);
        }

    }

    public List<ImageDto> getAllImages(Long propertyId) {
        Property property=propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with ID: " + propertyId));
        List<Images> images= imagesRepository.findByProperty(property);
        List<ImageDto> imageDtos=images.stream().map(this::mapToDto).collect(Collectors.toList());
        return imageDtos;
    }
}

