package com.gooroomee.api.files.profileImage;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ProfileImageDto {
    private String file_name;
    private String file_type;
    private String file_url;

}
