package com.gooroomee.api.files.boardfile;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class BoardFileController {

    private final BoardFileService boardFileService;

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file){
        BoardFile boardFile=boardFileService.storeFile(file);
        String fileDownloadUri= ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(boardFile.getId())
                .toUriString();
        return new UploadFileResponse(boardFile.getFileName(), fileDownloadUri, file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files){
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId, HttpServletResponse response){
        BoardFile boardFile=boardFileService.getFile(fileId);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(boardFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment: filename=\""+boardFile.getFileName()+"\"")
                .body(new ByteArrayResource(boardFile.getData()));

    }

}
