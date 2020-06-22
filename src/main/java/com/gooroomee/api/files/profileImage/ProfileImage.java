package com.gooroomee.api.files.profileImage;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Entity
@Table(name="t_profile_image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileImage {
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid", strategy = "uuid2")
    private String file_id;
    private String file_name;
    private String file_type;
    private String file_url;
    private String email;

    public ProfileImage updateProfileImage(String test_file_path, MultipartFile file, ProfileImage profileImage) {
        profileImage.setFile_name(file.getOriginalFilename());
        profileImage.setFile_type(file.getContentType());
        profileImage.setFile_url(test_file_path + file.getOriginalFilename());
        return profileImage;
    }


}
