package com.gooroomee.api.files.postfile;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PostFileUrlDto {
    private String file_id;
    private String file_name;
}
