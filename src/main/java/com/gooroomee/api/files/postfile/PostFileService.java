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
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostFileService {

    @Autowired
    private PostFileRepository postFileRepository;

    @Transactional
    public PostFile storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if(fileName.contains("..")) {
                throw new FileUploadException("파일 이름에 이상값이 들어있습니다. " + fileName);
            }
            PostFile postFile =new PostFile(fileName,file.getContentType(), file.getBytes());
            return postFileRepository.save(postFile);

        } catch (IOException ex) {
            throw new FileUploadException(fileName + " 파일을 저장할 수 없습니다. 다시 시도해주세요!", ex);
        }

    }

    @Transactional
    public PostFile getFile(String fileId){
        return postFileRepository.findById(fileId).orElseThrow(()->new MyFileNotFoundException(fileId +" 파일을 찾을 수 없습니다."));
    }

}
