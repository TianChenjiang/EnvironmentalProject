package njutj.environment.data.dao.record;

import njutj.environment.entity.record.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageDao extends JpaRepository<Image, Long> {
}
