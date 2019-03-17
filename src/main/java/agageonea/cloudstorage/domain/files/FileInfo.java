package agageonea.cloudstorage.domain.files;

import agageonea.cloudstorage.domain.abstracts.AbstractEntity;
import agageonea.cloudstorage.domain.authentication.User;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = FileInfo.TABLE_NAME)
public class FileInfo extends AbstractEntity {

    public final static String TABLE_NAME = "files";

    public FileInfo(){}

    @Column(name = "name")
    private String name;

    @Column(name = "path")
    private String path;

    @Column(name = "contenttype")
    private String contentType;

    @Column(name = "size")
    private Long size;

    @Column(name = "timecreated")
    private Date timeCreated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    private void prePersits(){
        if(this.timeCreated == null){
            this.timeCreated = new Date();
        }
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
