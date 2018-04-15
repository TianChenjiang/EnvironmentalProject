package njutj.environment.blservice.upload;

import njutj.environment.exception.viewexception.SystemException;
import njutj.environment.response.upload.UploadImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageUploadBlService {
    UploadImageResponse uploadImage(MultipartFile multipartFile) throws IOException, SystemException;
}
