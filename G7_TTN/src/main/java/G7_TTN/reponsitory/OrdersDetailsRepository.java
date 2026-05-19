package G7_TTN.reponsitory;

import G7_TTN.entity.Orders_details;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersDetailsRepository extends JpaRepository<Orders_details, Long> {

    List<Orders_details> findByOrders_Id(Long id);
}