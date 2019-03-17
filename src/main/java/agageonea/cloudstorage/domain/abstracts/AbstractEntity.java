package agageonea.cloudstorage.domain.abstracts;

import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, length = 11)
    private Long id;

    @Column(name = "refid", updatable = false, nullable = false, length = 64)
    private String refid;

    @PrePersist
    public void prePersist(){
        if (this.refid == null || StringUtils.isEmpty(this.refid)){
            this.refid = UUID.randomUUID().toString().toUpperCase();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRefid() {
        return refid;
    }

    public void setRefid(String refid) {
        this.refid = refid;
    }
}
