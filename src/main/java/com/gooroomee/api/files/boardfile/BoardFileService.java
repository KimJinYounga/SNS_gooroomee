package com.gooroomee.api.files.boardfile;

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
public class BoardFileService {

    @Autowired
    private BoardFileRepository boardFileRepository;

    @Transactional
    public BoardFile storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if(fileName.contains("..")) {
                throw new FileUploadException("파일 이름에 이상값이 들어있습니다. " + fileName);
            }
            BoardFile boardFile=new BoardFile(fileName,file.getContentType(), file.getBytes());
            return boardFileRepository.save(boardFile);

        } catch (IOException ex) {
            throw new FileUploadException(fileName + " 파일을 저장할 수 없습니다. 다시 시도해주세요!", ex);
        }

    }

    @Transactional
    public BoardFile getFile(String fileId){
        return boardFileRepository.findById(fileId).orElseThrow(()->new MyFileNotFoundException(fileId +" 파일을 찾을 수 없습니다."));
    }

}
