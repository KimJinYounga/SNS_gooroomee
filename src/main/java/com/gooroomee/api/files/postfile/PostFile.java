package com.gooroomee.api.files.postfile;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Builder
@Entity
@Table(name="t_board_file")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostFile {
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid", strategy = "uuid2")
    private String file_id;
    private String file_name;
    private String file_type;
    private String file_url;
    private Long postId;
}
