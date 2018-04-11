package njutj.environment.dataservice.upload;

import njutj.environment.entity.record.Image;

public interface ImageDataService {
    /**
     * save image
     *
     * @param image the image to de saved
     */
    Image saveImage(Image image);
}
