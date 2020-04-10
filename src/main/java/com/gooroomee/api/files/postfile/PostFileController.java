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
//@CrossOrigin(origins = "*")
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.GET}, allowedHeaders = {"Content-Type", "X-Requested-With", "Accept", "Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers"}, exposedHeaders = {"Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"})
public class PostFileController {

    private final PostFileService postFileService;

    @PostMapping("/fileUpload")
    public UploadFileResponse upload(@RequestParam MultipartFile file) {
        System.out.println("File => "+ file);
        PostFile postFile = this.postFileService.storeFile(file);
        return new UploadFileResponse(postFile.getFile_id(), postFile.getFile_name(), "/fileDownload/"+postFile.getFile_id(), file.getContentType(), file.getSize());
    }


    @GetMapping("/fileDownload/{file_id}")
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
