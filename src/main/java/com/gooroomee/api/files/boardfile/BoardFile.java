package com.gooroomee.api.files.boardfile;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="files")
@Getter
@Setter
public class BoardFile {
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid", strategy = "uuid2")
    private String id;
    private String fileName;
    private String fileType;
    @Lob
    private byte[] data;
    public BoardFile(){}
    public BoardFile(String fileName, String fileType, byte[] data){
        this.fileName=fileName;
        this.fileType=fileType;
        this.data=data;
    }

}
