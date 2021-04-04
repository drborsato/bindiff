package br.com.drborsato.bindiff.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class BinFile {

    @EmbeddedId
    private FileId fileId;

    private String data;

    public BinFile(FileId fileId, String data) {
        this.fileId = fileId;
        this.data = data;
    }

    public FileId getFileId() {
        return fileId;
    }

    public void setFileId(FileId fileId) {
        this.fileId = fileId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}