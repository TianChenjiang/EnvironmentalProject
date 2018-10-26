package njutj.environment.data.upload;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import njutj.environment.dataservice.upload.ImageUploadDataService;
import njutj.environment.exception.viewexception.SystemException;
import njutj.environment.util.PathUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.net.URL;
import java.util.Date;

@Service
public class ImageUploadDataServiceImpl implements ImageUploadDataService {
    private final static String TEMP_PATH = PathUtil.getTmpPath();
    private static final long EXPIRATION = new Date().getTime() + 1000 * 60 * 60 * 24 * 100;

    @Value("${oos.accessKey}")
    private String accessKey;
    @Value("${oos.secretKey}")
    private String secretKey;
    @Value("${oos.endPoint}")
    private String endPoint;
    @Value("${oos.bucketName}")
    private String bucketName;

    /**
     * upload the image to the oos cloud
     *
     * @param key   the id of the image
     * @param bytes the image content
     * @return the url of the uploaded image
     */
    @Override
    public String uploadImage(String key, byte[] bytes) throws SystemException {
        try {
            //保存到临时文件
            File file = new File(TEMP_PATH);
            FileImageOutputStream fileWriter = new FileImageOutputStream(file);
            fileWriter.write(bytes);
            fileWriter.close();

            //上传图片
            AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
            AmazonS3 oos = new AmazonS3Client(credentials);
            oos.setEndpoint(endPoint);
            oos.putObject(bucketName, key, file);

            //生成共享地址
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, key);
            generatePresignedUrlRequest.setExpiration(new Date(EXPIRATION));
            URL url = oos.generatePresignedUrl(generatePresignedUrlRequest);
            return url.toURI().toString();
        } catch (Exception e) {
            throw new SystemException();
        }
    }

    /**
     * delete the image
     *
     * @param key the id of the image
     */
    @Override
    public void deleteImage(String key) {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3 oos = new AmazonS3Client(credentials);
        oos.setEndpoint(endPoint);
        oos.deleteObject(bucketName, key);
    }
}
