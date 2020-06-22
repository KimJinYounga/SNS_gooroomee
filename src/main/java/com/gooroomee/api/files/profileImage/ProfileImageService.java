package com.gooroomee.api.files.profileImage;

import com.gooroomee.api.files.exception.FileUploadException;
import com.gooroomee.api.files.exception.MyFileNotFoundException;
import com.gooroomee.api.files.postfile.PostFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileImageService {
    @Autowired
    ProfileImageRepository profileImageRepository;

    @Transactional
    public ProfileImage storeProfileImage(String email, MultipartFile file) {
//        File.separator
        String test_file_path = "C:\\Users"+File.separator+"gooroomee\\uploads\\profile\\";
        File f = new File(test_file_path);
        if (!f.exists()) f.mkdirs();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            throw new FileUploadException("파일 이름에 이상값이 들어있습니다. " + fileName);
        }
        ProfileImage profileImage = ProfileImage.builder()
                .file_name(file.getOriginalFilename())
                .file_type(file.getContentType())
                .file_url(test_file_path)
                .email(email)
                .build();
//        ProfileImage profileImage;
        Optional<ProfileImage> optional = this.profileImageRepository.findAllByEmail(email);
        if(optional.isPresent()){
            this.profileImageRepository.delete(optional.get());
        }
        ProfileImage savedImage = profileImageRepository.save(profileImage);
        String ext = FilenameUtils.getExtension(fileName);
//        file.transferTo();
        try (
                FileOutputStream fos = new FileOutputStream(test_file_path + savedImage.getFile_id() +"."+ext);
                InputStream is = file.getInputStream();) {

            int readCount = 0;
            byte[] buffer = new byte[1024];

            while ((readCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, readCount);
            }
        } catch (Exception ex) {
            throw new RuntimeException("file Save Error");
        }



        return savedImage;
    }

    @Transactional
    public ProfileImage getFileByFileId(String file_id) {
        return profileImageRepository.findById(file_id).orElseThrow(() -> new MyFileNotFoundException(file_id + " 파일을 찾을 수 없습니다."));
    }

    @Transactional
    public ProfileImage getFileByEmail(String email) {
        return profileImageRepository.findAllByEmail(email).orElseThrow(() -> new MyFileNotFoundException(email + " 파일을 찾을 수 없습니다."));
    }
}
