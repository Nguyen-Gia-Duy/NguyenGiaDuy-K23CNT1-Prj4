package G7_TTN.reponsitory;

import G7_TTN.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserReponsitory extends JpaRepository<Users, Long> {

    List<Users> findByIsdelete(Integer isdelete);

    Optional<Users> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}