package G7_TTN.service;

import G7_TTN.entity.Users;
import G7_TTN.reponsitory.UserReponsitory;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.*;

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
    public UserDetails loadUserByUsername(String username) {

        Users user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tồn tại tài khoản"));

        if (user.getIsdelete() != null && user.getIsdelete() == 1) {
            throw new UsernameNotFoundException("Tài khoản đã bị xóa");
        }

        if (user.getIsactive() != null && user.getIsactive() == 0) {
            throw new UsernameNotFoundException("Tài khoản đã bị khóa");
        }

        String role = (user.getRole() == null) ? "USER" : user.getRole();

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                true, true, true, true,
                List.of(() -> "ROLE_" + role)
        );
    }

    // ================= REGISTER =================
    public String register(Users user) {

        // VALIDATION
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            return "Username không được để trống";
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            return "Password không được để trống";
        }

        if (userRepo.existsByUsername(user.getUsername())) {
            return "Username đã tồn tại";
        }

        if (user.getEmail() != null && !user.getEmail().isBlank()
                && userRepo.existsByEmail(user.getEmail())) {
            return "Email đã tồn tại";
        }

        // SET DATA
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("USER");
        user.setIsactive(1);
        user.setIsdelete(0);
        user.setCreateddate(new Date());
        user.setUpdateddate(new Date());

        userRepo.save(user); // ⭐ INSERT DB

        return "OK";
    }

    // ================= ADMIN SAVE =================
    public void save(Users user) {

        if (user.getId() == null) {

            user.setPassword(encoder.encode(user.getPassword()));
            user.setCreateddate(new Date());
            user.setIsactive(1);
            user.setIsdelete(0);

        } else {

            Users old = userRepo.findById(user.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            user.setCreateddate(old.getCreateddate());

            if (user.getPassword() == null || user.getPassword().isBlank()) {
                user.setPassword(old.getPassword());
            } else {
                user.setPassword(encoder.encode(user.getPassword()));
            }

            if (user.getIsactive() == null) user.setIsactive(old.getIsactive());
            if (user.getIsdelete() == null) user.setIsdelete(old.getIsdelete());
        }

        user.setUpdateddate(new Date());
        userRepo.save(user);
    }

    public void delete(Long id) {
        userRepo.findById(id).ifPresent(u -> {
            u.setIsdelete(1);
            u.setUpdateddate(new Date());
            userRepo.save(u);
        });
    }

    public void lock(Long id) {
        userRepo.findById(id).ifPresent(u -> {
            u.setIsactive(0);
            u.setUpdateddate(new Date());
            userRepo.save(u);
        });
    }

    public void unlock(Long id) {
        userRepo.findById(id).ifPresent(u -> {
            u.setIsactive(1);
            u.setUpdateddate(new Date());
            userRepo.save(u);
        });
    }
}