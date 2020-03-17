package com.gooroomee.api.files.postfile;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="t_board_file")
@Getter
@Setter
public class PostFile {
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid", strategy = "uuid2")
    private String file_id;
    private String file_name;
    private String file_type;
    @Lob // mysql -> LONGBLOB
    private byte[] file_data;
    public PostFile(){}
    public PostFile(String file_name, String file_type, byte[] file_data){
        this.file_name=file_name;
        this.file_type=file_type;
        this.file_data=file_data;
    }

}
