package G7_TTN.service;

import G7_TTN.entity.Users;
import G7_TTN.reponsitory.UserReponsitory;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserReponsitory userRepo;
    private final PasswordEncoder encoder;

    public UserService(UserReponsitory userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    // ================= LOGIN =================
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tồn tại"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(() -> "ROLE_" + user.getRole())
        );
    }

    // ================= REGISTER =================
    public String register(Users user) {

        if (userRepo.existsByUsername(user.getUsername())) {
            return "Username đã tồn tại";
        }

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("USER");
        user.setIsactive(1);
        user.setIsdelete(0);

        user.setCreateddate(new Date());
        user.setUpdateddate(new Date());

        userRepo.save(user);

        return "OK";
    }

    // ================= FIND =================
    public Users findByUsername(String username) {
        return userRepo.findByUsername(username).orElse(null);
    }

    // ================= CREATE ADMIN AUTO =================
    @PostConstruct
    public void createAdmin() {

        if (userRepo.findByUsername("admin").isEmpty()) {

            Users admin = new Users();
            admin.setUsername("admin");
            admin.setPassword(encoder.encode("123456"));
            admin.setName("Admin");
            admin.setRole("ADMIN");
            admin.setIsactive(1);
            admin.setIsdelete(0);
            admin.setCreateddate(new Date());
            admin.setUpdateddate(new Date());

            userRepo.save(admin);

            System.out.println(">>> Admin created");
        }
    }
}