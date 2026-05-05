package G7_TTN.reponsitory;


import G7_TTN.entity.Payment_method;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository  extends JpaRepository<Payment_method, Long> {
}
