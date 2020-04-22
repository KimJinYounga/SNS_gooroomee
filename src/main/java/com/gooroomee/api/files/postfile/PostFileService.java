package com.gooroomee.api.files.postfile;

import com.gooroomee.api.files.exception.FileUploadException;
import com.gooroomee.api.files.exception.MyFileNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostFileService {

    @Autowired
    private PostFileRepository postFileRepository;

    @Autowired
    ModelMapper modelMapper;

    @Transactional
    public PostFile storeFile(MultipartFile file) {
        String test_file_path = "C:\\Users\\gooroomee\\uploads\\";
        File f = new File(test_file_path);
        if (!f.exists()) f.mkdirs();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            throw new FileUploadException("파일 이름에 이상값이 들어있습니다. " + fileName);
        }
        PostFile postFile = PostFile.builder()
                .file_name(file.getOriginalFilename())
                .file_type(file.getContentType())
                .file_url(test_file_path)
                .build();
        PostFile savedFile = postFileRepository.save(postFile);
        String ext = FilenameUtils.getExtension(savedFile.getFile_name());
        try (
                FileOutputStream fos = new FileOutputStream(test_file_path + savedFile.getFile_id() +"."+ ext);
                InputStream is = file.getInputStream();) {

            int readCount = 0;
            byte[] buffer = new byte[1024];

            while ((readCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, readCount);
            }
        } catch (Exception ex) {
            throw new RuntimeException("file Save Error");
        }

        return savedFile;
//        return postFileRepository.save(postFile);

    }

    @Transactional
    public PostFile getFile(String fileId) {
        return postFileRepository.findById(fileId).orElseThrow(() -> new MyFileNotFoundException(fileId + " 파일을 찾을 수 없습니다."));
    }

    @Transactional
    public PostFileUrlDto getFileUrl(Long postId) {
        PostFile postFile = this.postFileRepository.findAllByPostId(postId).orElseThrow(() -> new MyFileNotFoundException("파일을 찾을 수 없습니다."));
        return this.modelMapper.map(postFile, PostFileUrlDto.class);
    }

    @Transactional
    public void deleteFile(String file_id) {
        PostFile postFile = this.postFileRepository.findById(file_id).orElseThrow(() -> new MyFileNotFoundException(file_id + " 파일을 찾을 수 없습니다."));
        String path = postFile.getFile_url();
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        this.postFileRepository.delete(postFile);
    }


}
