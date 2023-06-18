package anndy.profile;

import anndy.model.User;
import anndy.phrase.IPhraseService;
import anndy.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/profile")
@Controller
public class ProfilesController {
    private final UserService userService;
    private final IPhraseService phraseService;

    @Autowired
    public ProfilesController(UserService userService, IPhraseService phraseService) {
        this.userService = userService;
        this.phraseService = phraseService;
    }

    @GetMapping
    public String myProfile() {
        Long userId = userService.getRemoteUserId();
        return "redirect:/profile/" + userId;
    }

    @GetMapping("/{id}")
    @PreAuthorize("(@userService.getRemoteUser().getId() == #id) or hasAuthority('АДМИНИСТРАТОР')")
    public String viewProfileById(@PathVariable Long id, ModelMap model) {
        User user = userService.getById(id);
        if (user == null)
            return myProfile();
        model.addAttribute("user", user);
        return "profile/profile";
    }

    @GetMapping("/change_email")
    public String changeEmail(ModelMap model) {
        model.addAttribute("currentEmail", userService.getRemoteUserEmail());
        return "profile/change_email";
    }

    @PostMapping("/change_email")
    public String changeEmail(@RequestParam("email") String email, ModelMap model) {
        if (userService.emailExists(email)
                || !email.matches("[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$")) {
            model.addAttribute("currentEmail", userService.getRemoteUserEmail());
            model.addAttribute("emailInputError", "Email уже существует или введён неверно");
            return "profile/change_email";
        }
        if (userService.emailExists(email)) {
            model.addAttribute("emailExistsError", "Email already exists");
            return "profile/change_email";
        }
        userService.saveEmail(email);
        return "redirect:/profile";
    }

    @GetMapping("/change_password")
    public String changePassword() {
        return "profile/change_password";
    }

    @PostMapping("/change_password")
    public String changePassword(
            @RequestParam("newPassword") String newPassword
            , @RequestParam("currentPassword") String currentPassword
            , @RequestParam("confirmPassword") String confirmPassword
            , ModelMap model
    ) {
        if (!userService.checkRemoteUserPassword(currentPassword)) {
            model.addAttribute("InvalidPasswordError", "Установленный пароль неверено введен");
            return "profile/change_password";
        }
        if (newPassword.isBlank() || !newPassword.matches("[A-Za-z0-9#$&/%-._]{8,60}$")) {
            model.addAttribute("passwordIsBlankError", "Новый пароль введен некорректно");
            return "profile/change_password";
        }
        if (!confirmPassword.equals(newPassword)) {
            model.addAttribute("currentPassword", currentPassword);
            model.addAttribute("passwordIsDifferentError", "Пароли различаются");
            return "profile/change_password";
        }
        userService.savePassword(newPassword);
        return "redirect:/profile";
    }
}
