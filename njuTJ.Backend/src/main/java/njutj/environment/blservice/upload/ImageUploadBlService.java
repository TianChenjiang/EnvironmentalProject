package njutj.environment.blservice.upload;

import njutj.environment.exception.viewexception.SystemException;
import njutj.environment.response.upload.UploadImageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadBlService {
    /**
     * upload image as record
     *
     * @param multipartFile
     * @return
     * @throws SystemException
     */
    UploadImageResponse uploadImage(MultipartFile multipartFile) throws SystemException;
}
