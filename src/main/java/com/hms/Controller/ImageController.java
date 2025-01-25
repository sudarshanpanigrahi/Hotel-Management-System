package com.hms.Controller;

import com.hms.Entity.Images;
import com.hms.Entity.Property;
import com.hms.Payload.ImageDto;
import com.hms.Repository.ImagesRepository;
import com.hms.Repository.PropertyRepository;
import com.hms.Service.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("/Images")
public class ImageController {

    private ImageService imageService;
    private PropertyRepository propertyRepository;
    private ImagesRepository imagesRepository;
    private ModelMapper modelMapper;

    public ImageController(ImageService imageService,
                           PropertyRepository propertyRepository,
                           ImagesRepository imagesRepository,
                           ModelMapper modelMapper) {
        this.imageService = imageService;
        this.propertyRepository = propertyRepository;
        this.imagesRepository = imagesRepository;
        this.modelMapper = modelMapper;
    }

    public ImageDto mapToDto(Images images){
        ImageDto dto=modelMapper.map(images,ImageDto.class);
        return dto;
    }

    @PostMapping(path = "/upload/file/{bucketName}/property/{propertyId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadPropertyPhotos(@RequestParam MultipartFile file,
                                                  @PathVariable String bucketName,
                                                  @PathVariable long propertyId
//                                                  @AuthenticationPrincipal AppUser user
    ) {
        Property property=propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with ID: " + propertyId));
        String imageUrl = imageService.uploadFile(file, bucketName);
        Images images =new Images();
        images.setUrl(imageUrl);
        images.setProperty(property);
        Images savedImages=imagesRepository.save(images);
        ImageDto dtoImage=mapToDto(savedImages);
        return new ResponseEntity<>(dtoImage, HttpStatus.OK);
    }

    @GetMapping("/property")
    public ResponseEntity<List<ImageDto>>gettingAllImages(@RequestParam Long propertyId){
        List<ImageDto> imageDto=imageService.getAllImages(propertyId);
        return new ResponseEntity<>(imageDto, HttpStatus.OK);
    }
}

