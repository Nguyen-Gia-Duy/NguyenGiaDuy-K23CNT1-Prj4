package G7_TTN.controller.user;

import G7_TTN.entity.Users;
import G7_TTN.reponsitory.UserReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.UUID;

@Controller
public class ProfileController {

    @Autowired
    private UserReponsitory userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ================= PROFILE =================
    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {

        Users user = userRepo.findByUsername(principal.getName())
                .orElseThrow();

        model.addAttribute("user", user);
        return "user/profile";
    }

    // ================= UPDATE PROFILE =================
    @PostMapping("/profile/update")
    public String updateProfile(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String address,
            @RequestParam MultipartFile avatarFile,
            Principal principal
    ) throws IOException {

        Users user = userRepo.findByUsername(principal.getName())
                .orElseThrow();

        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(address);

        // update time
        user.setUpdateddate(new Date());

        // upload avatar
        if (!avatarFile.isEmpty()) {

            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String fileName = UUID.randomUUID() + "_" + avatarFile.getOriginalFilename();

            avatarFile.transferTo(new File(uploadDir + fileName));

            user.setAvatar(fileName);
        }

        userRepo.save(user);

        return "redirect:/profile?success";
    }

    // ================= CHANGE PASSWORD PAGE =================
    @GetMapping("/change-password")
    public String changePasswordPage() {
        return "user/changepw";
    }

    // ================= CHANGE PASSWORD =================
    @PostMapping("/change-password")
    public String changePassword(
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            Principal principal
    ) {

        Users user = userRepo.findByUsername(principal.getName())
                .orElseThrow();

        // check old password (BCrypt)
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return "redirect:/change-password?errorOld";
        }

        // confirm password
        if (!newPassword.equals(confirmPassword)) {
            return "redirect:/change-password?errorConfirm";
        }

        // update password
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdateddate(new Date());

        userRepo.save(user);

        return "redirect:/change-password?success";
    }
}