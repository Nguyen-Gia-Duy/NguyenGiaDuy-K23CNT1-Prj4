package G7_TTN.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import G7_TTN.entity.Users;
import G7_TTN.reponsitory.UserReponsitory;

import java.util.Date;

@Component
public class DataInitializer {

    private final UserReponsitory userRepo;
    private final PasswordEncoder encoder;

    public DataInitializer(UserReponsitory userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @PostConstruct
    public void initAdmin() {
        if (userRepo.findByUsername("admin") == null) {
            Users admin = new Users();
            admin.setUsername("admin");
            admin.setPassword(encoder.encode("123"));
            admin.setName("Admin");
            admin.setRole("ADMIN");
            admin.setIsactive(1);

            userRepo.save(admin);
            System.out.println("✅ Admin created");
        }
    }
}