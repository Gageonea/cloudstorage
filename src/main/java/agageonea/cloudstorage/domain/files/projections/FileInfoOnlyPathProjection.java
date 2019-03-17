package agageonea.cloudstorage.domain.files.projections;

import agageonea.cloudstorage.domain.files.FileInfo;

public class FileInfoOnlyPathProjection {

    public FileInfoOnlyPathProjection(FileInfo fileInfo){
        this.path = fileInfo.getPath();
    }

    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
