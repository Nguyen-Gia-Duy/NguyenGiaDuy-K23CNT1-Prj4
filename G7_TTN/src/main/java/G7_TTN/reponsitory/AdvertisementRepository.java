package G7_TTN.reponsitory;

import G7_TTN.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvertisementRepository
        extends JpaRepository<Advertisement, Long> {

    // lấy quảng cáo đang active
    List<Advertisement> findByIsactive(Integer isactive);

    // sắp xếp mới nhất
    List<Advertisement> findAllByOrderByIdDesc();

    // lấy quảng cáo theo vị trí
    List<Advertisement> findByPositionAndIsactive(
            String position,
            Integer isactive
    );
}