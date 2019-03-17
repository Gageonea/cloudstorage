package agageonea.cloudstorage.domain.files.projections;

import agageonea.cloudstorage.domain.files.FileInfo;

public class FileInfoNameAndPathProjection {

    public FileInfoNameAndPathProjection(FileInfo fileInfo){
        this.name = fileInfo.getName();
        this.path = fileInfo.getPath();
    }

    private String name;
    private String path;

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
}
