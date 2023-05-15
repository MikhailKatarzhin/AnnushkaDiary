package anndy.controller;

import anndy.model.Phrase;
import anndy.model.User;
import anndy.service.interfaces.PhraseService;
import anndy.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SignUpController {

    private final UserService userService;
    private final PhraseService phraseService;
    private final HttpServletRequest httpServletRequest;

    @Autowired
    public SignUpController(UserService userService, PhraseService phraseService, HttpServletRequest httpServletRequest) {
        this.userService = userService;
        this.phraseService = phraseService;
        this.httpServletRequest = httpServletRequest;
    }

    @GetMapping("/sign_up")
    public String signUp(ModelMap model) {
        insertRandomPhraseIntoFooterString(model);
        if (userService.getRemoteUser() != null)
            return "redirect:/profile";
        model.addAttribute("user", new User());
        return "sign_up";
    }

    @Transactional
    @PostMapping("/sign_up")
    public String addUser(@RequestParam String confirmPassword, User user, ModelMap model) {
        insertRandomPhraseIntoFooterString(model);
        model = checkRegistrationData(confirmPassword, user, model);
        if (model.size() > 4) {
            return "sign_up";
        }
        userService.signUpUser(user);
        return "redirect:/sign_in";
    }

    public ModelMap checkRegistrationData(@RequestParam String confirmPassword, @ModelAttribute User user, ModelMap model) {
        if (userService.getByName(user.getName()) != null || !user.getName().matches("[A-Za-z0-9 А-Яа-яЁё_-]{2,50}")) {
            model.addAttribute("usernameExistsError", "Username input error");
        }
        if (user.getPassword().isBlank() || !user.getPassword().matches("[A-Za-z0-9#$&/%-._]{8,60}$")) {
            model.addAttribute("passwordIsBlankError", "Password input error!");
        }
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("passwordsAreDifferent", "Passwords are different");
        }

        if (userService.emailExists(user.getEmail()) || !user.getEmail().matches("[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$")) {
            model.addAttribute("emailExistsError", "Email input error");
        }
        return model;
    }

    private void insertRandomPhraseIntoFooterString(ModelMap modelMap){
        Phrase phrase = phraseService.findRandomPhrase();
        if (phrase != null)
            modelMap.addAttribute("footerString", phrase.getContent());
    }

    @GetMapping("/sign_in")
    public String signIn(ModelMap model){
        if (httpServletRequest.getRemoteUser() != null)
            return "redirect:/profile";
        insertRandomPhraseIntoFooterString(model);
        return "sign_in";
    }
}
