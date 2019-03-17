package agageonea.cloudstorage.domain.files.projections;

import agageonea.cloudstorage.domain.files.FileInfo;

import java.util.Date;

public class SimpleFileInfoProjection {

    public SimpleFileInfoProjection(FileInfo fileInfo){
        this.name = fileInfo.getName();
        this.path = fileInfo.getPath();
        this.contentType = fileInfo.getContentType();
        this.timeCreated = fileInfo.getTimeCreated();
        this.size = fileInfo.getSize();
    }

    private String name;
    private String path;
    private String contentType;
    private Date timeCreated;
    private Long size;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
