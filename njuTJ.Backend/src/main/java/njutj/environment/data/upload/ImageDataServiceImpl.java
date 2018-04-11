package njutj.environment.data.upload;

import njutj.environment.data.dao.record.ImageDao;
import njutj.environment.dataservice.upload.ImageDataService;
import njutj.environment.entity.record.Image;
import org.springframework.beans.factory.annotation.Autowired;

public class ImageDataServiceImpl implements ImageDataService {
    private final ImageDao imageDao;

    @Autowired
    public ImageDataServiceImpl(ImageDao imageDao) {
        this.imageDao = imageDao;
    }

    @Override
    public Image saveImage(Image image) {
        return imageDao.save(image);
    }
}
