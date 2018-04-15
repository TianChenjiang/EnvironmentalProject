package njutj.environment.bl.upload;

import njutj.environment.blservice.upload.ImageUploadBlService;
import njutj.environment.dataservice.record.RecordDataService;
import njutj.environment.dataservice.upload.ImageUploadDataService;
import njutj.environment.entity.record.PlantRecord;
import njutj.environment.exception.viewexception.SystemException;
import njutj.environment.publicdatas.record.RecordState;
import njutj.environment.response.upload.UploadImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class ImageUploadBlServiceImpl implements ImageUploadBlService {
    private final ImageUploadDataService imageUploadDataService;
    private final RecordDataService recordDataService;

    @Autowired
    public ImageUploadBlServiceImpl(ImageUploadDataService imageUploadDataService, RecordDataService recordDataService) {
        this.imageUploadDataService = imageUploadDataService;
        this.recordDataService = recordDataService;
    }

    /**
     * upload image to cloud
     *
     * @param multipartFile the image to be uploaded
     * @return the upload image response
     */
    @Override
    public UploadImageResponse uploadImage(MultipartFile multipartFile) throws SystemException {
        PlantRecord plantRecord = recordDataService.saveRecord(new PlantRecord(RecordState.PENDING, "", null));
        String url;
        try {
            url = imageUploadDataService.uploadImage(String.valueOf(plantRecord.getId()), multipartFile.getBytes());
        } catch (IOException e) {
            throw new SystemException();
        }
        plantRecord.setImageUrl(url);
        recordDataService.saveRecord(plantRecord);
        return new UploadImageResponse(url);
    }
}
