package com.gooroomee.api.files.profileImage;

import com.gooroomee.api.files.postfile.PostFile;
import com.gooroomee.api.files.postfile.PostFileUrlDto;
import com.gooroomee.api.files.postfile.UploadFileResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.GET}, allowedHeaders = {"Content-Type", "X-Requested-With", "Accept", "Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers"}, exposedHeaders = {"Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"})

public class ProfileImageController {

    private final ProfileImageService profileImageService;

    @PostMapping("/fileUpload/{email}")
    public UploadFileResponse uploadProfileImage(@PathVariable String email, @RequestParam MultipartFile file) {
        System.out.println("File => "+ file);
        ProfileImage profileImage = this.profileImageService.storeProfileImage(email, file);
        return new UploadFileResponse(profileImage.getFile_id(), profileImage.getFile_name(), "/profileImage/"+profileImage.getFile_id(), file.getContentType(), file.getSize());
    }

    @GetMapping("/profileImage/{file_id}")
    public ResponseEntity<InputStreamResource> testDownload(@PathVariable String file_id) throws IOException {
        ProfileImage profileImage = this.profileImageService.getFileByFileId(file_id);
        String ext = FilenameUtils.getExtension(profileImage.getFile_name());
        String Path = profileImage.getFile_url()+"\\"+profileImage.getFile_id()+"."+ext;
        File file = new File(Path);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename="+file.getName())
                .contentType(MediaType.parseMediaType(profileImage.getFile_type())).contentLength(file.length())
                .body(resource);
    }

    @GetMapping("/getProfileUrl/{email}")
    public UploadFileResponse getFilesUrl(@PathVariable String email) {
        ProfileImage profileImage = this.profileImageService.getFileByEmail(email);
        return new UploadFileResponse(profileImage.getFile_id(), profileImage.getFile_name(), "/profileImage/"+profileImage.getFile_id());
    }
}
