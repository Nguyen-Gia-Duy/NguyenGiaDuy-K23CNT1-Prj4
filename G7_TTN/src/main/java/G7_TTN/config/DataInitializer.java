package G7_TTN.config;

import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import G7_TTN.entity.Users;
import G7_TTN.reponsitory.UserReponsitory;

@Component
public class DataInitializer {

    private final UserReponsitory userRepo;
    private final PasswordEncoder encoder;

    public DataInitializer(UserReponsitory userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @PostConstruct
    public void initData() {

        // ===== ADMIN =====
        Users admin = userRepo.findByUsername("admin").orElse(null);

        if (admin == null) {
            admin = new Users();
            admin.setUsername("admin");
            admin.setName("Admin");
            admin.setRole("ADMIN");
            admin.setIsactive(1);
        }

        admin.setPassword(encoder.encode("123"));
        userRepo.save(admin);


        // ===== USER =====
        Users user = userRepo.findByUsername("user").orElse(null);

        if (user == null) {
            user = new Users();
            user.setUsername("user");
            user.setName("User");
            user.setRole("USER");
            user.setIsactive(1);
        }

        user.setPassword(encoder.encode("123"));
        userRepo.save(user);

        System.out.println("✅ Data initialized");
    }
}