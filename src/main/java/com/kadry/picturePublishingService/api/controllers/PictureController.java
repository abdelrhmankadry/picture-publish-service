package com.kadry.picturePublishingService.api.controllers;

import com.kadry.picturePublishingService.api.models.reponses.picture.CreatePictureResponse;
import com.kadry.picturePublishingService.api.models.reponses.picture.PictureResponse;
import com.kadry.picturePublishingService.api.models.reponses.picture.PicturesResponse;
import com.kadry.picturePublishingService.api.models.requests.PictureRequest;
import com.kadry.picturePublishingService.core.exceptions.PictureNotFoundException;
import com.kadry.picturePublishingService.core.security.AuthenticatedUser;

import com.kadry.picturePublishingService.domain.user.Role;
import com.kadry.picturePublishingService.services.PictureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Picture", description = "APIs for managing pictures")
public class PictureController {

    private final PictureService pictureService;
    @GetMapping("/pictures")
    @Operation(summary = "Get accepted pictures", description = "Retrieves all accepted pictures.")
    public PicturesResponse getAcceptedPicture(){
        return pictureService.getAllAcceptedPictures();
    }

    @GetMapping(value = "/picture/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE )
    @Operation(summary = "Get accepted picture by ID", description = "Retrieves an accepted picture by its ID.")
    public byte[] getPictureById(@PathVariable String id) throws PictureNotFoundException {
            return pictureService.getAcceptedPictureById(id);
    }

    @GetMapping(value = "/pending-picture/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE )
    @Operation(summary = "Get pending picture by ID", description = "Retrieves a pending picture by its ID.")
    public byte[] getPendingPictureById(@PathVariable String id) throws PictureNotFoundException {
        return pictureService.getPendingPictureById(id);
    }

    @PostMapping("/picture")
    @Operation(summary = "Upload a picture", description = "Uploads a picture.")
    @ResponseStatus(HttpStatus.CREATED)
    public CreatePictureResponse uploadPicture(@RequestBody PictureRequest request){
       return pictureService.createPicture(request);
    }

    @GetMapping("/pending-pictures")
    @Operation(summary = "Get all pending pictures", description = "Retrieves all pending pictures.")
    public PicturesResponse getAllPendingPictures(){
        return  pictureService.getAllPendingPictures();
    }

    @PatchMapping("/accept-picture/{id}")
    @Operation(summary = "Accept a picture", description = "Accepts a pending picture.")
    public ResponseEntity<String> acceptPicture(@PathVariable String id) throws PictureNotFoundException {
        pictureService.acceptPicture(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/reject-picture/{id}")
    @Operation(summary = "Reject a picture", description = "Rejects a pending picture.")
    public ResponseEntity<String> rejectPicture(@PathVariable String id) throws PictureNotFoundException {
        pictureService.rejectPicture(id);
        return ResponseEntity.ok().build();
    }
}
