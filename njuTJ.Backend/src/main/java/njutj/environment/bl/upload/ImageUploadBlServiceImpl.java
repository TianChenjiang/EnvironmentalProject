package njutj.environment.bl.upload;

import njutj.environment.blservice.upload.ImageUploadBlService;
import njutj.environment.dataservice.upload.ImageDataService;
import njutj.environment.dataservice.upload.ImageUploadDataService;
import njutj.environment.entity.record.Image;
import njutj.environment.exception.viewexception.SystemException;
import njutj.environment.response.upload.UploadImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class ImageUploadBlServiceImpl implements ImageUploadBlService {
    private final ImageDataService imageDataService;
    private final ImageUploadDataService imageUploadDataService;

    @Autowired
    public ImageUploadBlServiceImpl(ImageDataService imageDataService, ImageUploadDataService imageUploadDataService) {
        this.imageDataService = imageDataService;
        this.imageUploadDataService = imageUploadDataService;
    }

    /**
     * upload image to cloud
     *
     * @param multipartFile the image to be uploaded
     * @return the upload image response
     */
    @Override
    public UploadImageResponse uploadImage(MultipartFile multipartFile) throws SystemException, IOException {
        Image image = imageDataService.saveImage(new Image(""));
        if (image != null) {
            String url = imageUploadDataService.uploadImage(String.valueOf(image.getId()), multipartFile.getBytes());
            image.setUrl(url);
            imageDataService.saveImage(image);
            return new UploadImageResponse(url);
        } else {
            throw new SystemException();
        }
    }
}
