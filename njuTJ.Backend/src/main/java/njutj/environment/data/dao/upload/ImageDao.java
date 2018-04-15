package njutj.environment.data.dao.upload;

import njutj.environment.entity.record.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageDao extends JpaRepository<Image, Long> {
}
