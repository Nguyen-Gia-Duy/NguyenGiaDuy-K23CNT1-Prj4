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

        if (userRepo.findByUsername("admin").isEmpty()) {
            Users admin = new Users();
            admin.setUsername("admin");
            admin.setName("Admin");
            admin.setRole("ADMIN");
            admin.setIsactive(1);
            admin.setIsdelete(0);
            admin.setPassword(encoder.encode("123"));
            userRepo.save(admin);
        }

        if (userRepo.findByUsername("user").isEmpty()) {
            Users user = new Users();
            user.setUsername("user");
            user.setName("User");
            user.setRole("USER");
            user.setIsactive(1);
            user.setIsdelete(0);
            user.setPassword(encoder.encode("123"));
            userRepo.save(user);
        }
    }}