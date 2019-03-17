package agageonea.cloudstorage.controller.files;

import agageonea.cloudstorage.domain.authentication.Token;
import agageonea.cloudstorage.domain.authentication.User;
import agageonea.cloudstorage.domain.files.FileInfo;
import agageonea.cloudstorage.domain.files.projections.FileInfoNameAndPathProjection;
import agageonea.cloudstorage.domain.files.projections.FileInfoOnlyNameProjection;
import agageonea.cloudstorage.domain.files.projections.FileInfoOnlyPathProjection;
import agageonea.cloudstorage.domain.files.projections.SimpleFileInfoProjection;
import agageonea.cloudstorage.repositories.FileInfoRepository;
import agageonea.cloudstorage.repositories.TokenRepository;
import agageonea.cloudstorage.service.TokenService;
import agageonea.cloudstorage.util.enums.StatusEnum;
import agageonea.cloudstorage.util.responses.ErrorResponse;
import agageonea.cloudstorage.util.responses.FindResponse;
import agageonea.cloudstorage.util.responses.ObjectResponse;
import agageonea.cloudstorage.util.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.ws.rs.Consumes;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FileController {

    @Autowired
    private TokenRepository tokenRepo;
    @Autowired
    private TokenService tokenSrv;

    @Autowired
    private FileInfoRepository fileInfoRepo;

    private @Value("${app.filepath.prefix}") String pathPrefix;

    @PostMapping("/upload")
    @Consumes("multipart/*")
    public @ResponseBody Response upload(@RequestHeader("AuthorizationToken") String authorizationToken, MultipartRequest request) throws IOException {
        if(!this.tokenSrv.validate(authorizationToken)){
            return new ErrorResponse("Invalid token");
        }
        Token token = tokenRepo.findByToken(authorizationToken);
        User user = token.getUser();
        Iterator<String> iterator = request.getFileNames();
        while(iterator.hasNext()) {
            String fileName = iterator.next();
            MultipartFile mpFile = request.getFile(fileName);
            String filePath = user.getRefid() + File.separator + this.pathPrefix + File.separator + fileName;

            //create user directory if it does not exist (first upload)
            File dir = new File(user.getRefid()+ File.separator + this.pathPrefix);
            if(!dir.exists()){
                if(!dir.mkdirs()){
                    return new ErrorResponse("User directory does not exist and could not be created");
                }
            }

            //write the file to disk
            File diskFile = new File(filePath);
            if(diskFile.exists()){
                return new ErrorResponse("File already exists");
            }
            if(!diskFile.createNewFile()){
                return new ErrorResponse("Could not create file on disk");
            }
            FileOutputStream fos = new FileOutputStream(diskFile);
            fos.write(mpFile.getBytes());
            fos.close();

            //save file info in db
            FileInfo fileInfo = new FileInfo();
            fileInfo.setName(fileName);
            fileInfo.setPath(filePath);
            fileInfo.setContentType(mpFile.getContentType());
            fileInfo.setUser(user);
            fileInfo.setSize(mpFile.getSize());
            fileInfoRepo.save(fileInfo);

            return new ObjectResponse(new FileInfoOnlyNameProjection(fileInfo));

        }

        return new ErrorResponse("No files found in upload request");
    }

    @GetMapping("/list")
    public Response list(@RequestHeader("AuthorizationToken") String authorizationToken){
        if(!this.tokenSrv.validate(authorizationToken)){
            return new ErrorResponse("Invalid token");
        }
        Token token = tokenRepo.findByToken(authorizationToken);
        User user = token.getUser();

        List<FileInfo> fileInfoList = fileInfoRepo.findByUser(user);

        List<FileInfoNameAndPathProjection> projectionList = fileInfoList.stream()
                .map(e -> new FileInfoNameAndPathProjection(e))
                .collect(Collectors.toList());

        return new ObjectResponse(projectionList);
    }


    @GetMapping("/{objectName}")
    public Response objectMetadata(@RequestHeader("AuthorizationToken") String authorizationToken,
                                   @PathVariable(name = "objectName") String objectName){
        if(!this.tokenSrv.validate(authorizationToken)){
            return new ErrorResponse("Invalid token");
        }
        Token token = tokenRepo.findByToken(authorizationToken);
        User user = token.getUser();
        FileInfo fileInfo = fileInfoRepo.findByUserAndName(user, objectName);
        return new ObjectResponse(new SimpleFileInfoProjection(fileInfo));
    }

    @GetMapping("/{objectName}?")
    public HttpEntity<byte[]> objectMetadata(@RequestHeader("AuthorizationToken") String authorizationToken,
                                     @PathVariable(name = "objectName") String objectName,
                                     @RequestParam(name = "alt") String alt) throws IOException {
        if(!this.tokenSrv.validate(authorizationToken)){
            return null;
        }
        Token token = tokenRepo.findByToken(authorizationToken);
        User user = token.getUser();
        FileInfo fileInfo = fileInfoRepo.findByUserAndName(user, objectName);

        ContentDisposition.Builder dispBuilder = ContentDisposition.builder("attachment");

        ContentDisposition contentDisposition = dispBuilder.filename(fileInfo.getName()).build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(contentDisposition);
        headers.setContentType(MediaType.parseMediaType(fileInfo.getContentType()));

        return new HttpEntity<>(Files.readAllBytes(Paths.get(fileInfo.getPath())), headers);
    }

    @DeleteMapping("{objectName}")
    public Response deleteFile(@RequestHeader("AuthorizationToken") String authorizationToken,
                               @PathVariable(name = "objectName") String objectName){
        if(!this.tokenSrv.validate(authorizationToken)){
            return new ErrorResponse("Invalid token");
        }
        Token token = tokenRepo.findByToken(authorizationToken);
        User user = token.getUser();
        FileInfo fileInfo = fileInfoRepo.findByUserAndName(user, objectName);

        File file = new File(fileInfo.getPath());
        if(!file.delete()){
            return new ErrorResponse("Could not delete file from disk");
        }
        this.fileInfoRepo.delete(fileInfo);

        Response response = new Response();
        response.setStatus(StatusEnum.SUCCESS);

        return response;
    }

    @PostMapping("find")
    public Response find(@RequestHeader("AuthorizationToken") String authorizationToken,
                         @RequestParam(name = "name") String name){
        if(!this.tokenSrv.validate(authorizationToken)){
            return new ErrorResponse("Invalid token");
        }
        Token token = tokenRepo.findByToken(authorizationToken);
        User user = token.getUser();
        List<FileInfo> fileInfos = fileInfoRepo.findByUserAndMatchingName(user,name);

        List<FileInfoOnlyPathProjection> projections = fileInfos.stream()
                .map(e -> new FileInfoOnlyPathProjection(e))
                .collect(Collectors.toList());

        return new FindResponse(name, projections);
    }
}
