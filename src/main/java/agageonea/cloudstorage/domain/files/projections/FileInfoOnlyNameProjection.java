package agageonea.cloudstorage.domain.files.projections;

import agageonea.cloudstorage.domain.files.FileInfo;

public class FileInfoOnlyNameProjection {

    public FileInfoOnlyNameProjection(FileInfo fileInfo){
        this.name = fileInfo.getName();
    }
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
