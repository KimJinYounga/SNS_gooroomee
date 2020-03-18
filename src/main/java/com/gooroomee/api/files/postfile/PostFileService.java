package com.gooroomee.api.files.postfile;

import com.gooroomee.api.files.exception.FileUploadException;
import com.gooroomee.api.files.exception.MyFileNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostFileService {

    @Autowired
    private PostFileRepository postFileRepository;

    @Transactional
    public PostFile storeFile(MultipartFile file, Long post_id) {
        String test_file_path="C:\\Users\\gooroomee\\uploads\\"+post_id+"\\";
        File f=new File(test_file_path);
        if(!f.exists()) f.mkdirs();

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if(fileName.contains("..")) {
            throw new FileUploadException("파일 이름에 이상값이 들어있습니다. " + fileName);
        }
        PostFile postFile = PostFile.builder()
                .file_name(file.getOriginalFilename())
                .file_type(file.getContentType())
                .file_url(test_file_path+file.getOriginalFilename())
                .build();
        try (
                FileOutputStream fos = new FileOutputStream(test_file_path + fileName);
                InputStream is = file.getInputStream();) {

            int readCount = 0;
            byte[] buffer = new byte[1024];

            while ((readCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, readCount);
            }
        } catch (Exception ex) {
            throw new RuntimeException("file Save Error");
        }

        return postFileRepository.save(postFile);

    }

    @Transactional
    public PostFile getFile(String fileId){
        return postFileRepository.findById(fileId).orElseThrow(()->new MyFileNotFoundException(fileId +" 파일을 찾을 수 없습니다."));
    }

}
