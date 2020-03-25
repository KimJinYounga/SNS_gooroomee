package com.gooroomee.api.files.postfile;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PostFileController {

    private final PostFileService postFileService;

    @PostMapping("/testUpload/{post_id}")
    public UploadFileResponse upload(@RequestParam("file") MultipartFile file,
                                     @PathVariable Long post_id) {
        PostFile postFile = this.postFileService.storeFile(file, post_id);
        return new UploadFileResponse(postFile.getFile_name(), "/testDownload/"+postFile.getFile_id(), file.getContentType(), file.getSize());
    }


    @GetMapping("/testDownload/{file_id}")
    public ResponseEntity<InputStreamResource> testDownload(@PathVariable String file_id) throws IOException{
        PostFile postFile = this.postFileService.getFile(file_id);
        String Path = postFile.getFile_url();
        File file = new File(Path);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename="+file.getName())
                .contentType(MediaType.parseMediaType(postFile.getFile_type())).contentLength(file.length())
                .body(resource);
    }

}
