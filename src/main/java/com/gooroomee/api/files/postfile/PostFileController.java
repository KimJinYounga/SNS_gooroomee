package com.gooroomee.api.files.postfile;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
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
        String ext = FilenameUtils.getExtension(postFile.getFile_name());
        String Path = postFile.getFile_url()+"\\"+postFile.getFile_id() +"."+ext;
        File file = new File(Path);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename="+postFile.getFile_name())
                .contentType(MediaType.parseMediaType(postFile.getFile_type())).contentLength(file.length())
                .body(resource);
    }

    @GetMapping("getFilesUrl/{post_id}")
    public UploadFileResponse getFilesUrl(@PathVariable Long post_id) {
        PostFileUrlDto postFile = this.postFileService.getFileUrl(post_id);
        return new UploadFileResponse(postFile.getFile_id(), postFile.getFile_name(), "/fileDownload/"+postFile.getFile_id());
    }

    @DeleteMapping("/deleteFile/{file_id}")
    public ResponseEntity deleteFile(@PathVariable String file_id) {
        this.postFileService.deleteFile(file_id);
        return ResponseEntity.ok().build();
    }

}
