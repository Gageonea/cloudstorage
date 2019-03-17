package agageonea.cloudstorage.repositories;

import agageonea.cloudstorage.domain.authentication.User;
import agageonea.cloudstorage.domain.files.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {
    List<FileInfo> findByUser(User user);
    FileInfo findByUserAndName(User user, String name);

    @Query("SELECT fi FROM FileInfo fi WHERE fi.user = :user AND fi.name LIKE :name")
    List<FileInfo> findByUserAndMatchingName(@Param("user") User user, @Param("name") String name);
}
