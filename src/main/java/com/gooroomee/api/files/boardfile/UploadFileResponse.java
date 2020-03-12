package com.gooroomee.api.files.boardfile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadFileResponse{
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;

    public UploadFileResponse(String fileName, String fileDownloadUri, String contentType, long size) {
        this.fileName=fileName;
        this.fileDownloadUri=fileDownloadUri;
        this.fileType=contentType;
        this.size=size;
    }
}
