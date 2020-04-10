package com.gooroomee.api.files.postfile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadFileResponse{
    private String fileId;
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;

    public UploadFileResponse(String fileId, String fileName, String fileDownloadUri, String contentType, long size) {
        this.fileId = fileId;
        this.fileName=fileName;
        this.fileDownloadUri=fileDownloadUri;
        this.fileType=contentType;
        this.size=size;
    }
}
