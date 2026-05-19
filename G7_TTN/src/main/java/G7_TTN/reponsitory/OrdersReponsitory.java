package G7_TTN.reponsitory;

import G7_TTN.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface OrdersReponsitory extends JpaRepository<Orders, Long> {

    List<Orders> findByIsdelete(Integer isdelete);

    List<Orders> findByUserId(Long userId);

    long countByIsdelete(Integer isdelete);

    @Query("""
        SELECT COALESCE(SUM(o.totalmoney),0)
        FROM Orders o
        WHERE o.isdelete = 0
    """)
    BigDecimal totalRevenue();

    @Query("""
        SELECT COALESCE(SUM(o.totalmoney),0)
        FROM Orders o
        WHERE MONTH(o.ordersdate) = ?1
        AND o.isdelete = 0
    """)
    BigDecimal revenueByMonth(int month);
}