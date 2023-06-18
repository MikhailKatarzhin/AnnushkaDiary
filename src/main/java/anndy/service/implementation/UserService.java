package anndy.service.implementation;

import anndy.config.WebSecurityConfig;
import anndy.diary.Diary;
import anndy.diary.DiaryRepository;
import anndy.model.Role;
import anndy.model.User;
import anndy.repo.RoleRepository;
import anndy.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserService implements anndy.service.interfaces.UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final HttpServletRequest httpServletRequest;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final DiaryRepository diaryRepository;

    @Autowired
    public UserService(UserRepository userRepository, HttpServletRequest httpServletRequest
            , BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository, DiaryRepository diaryRepository) {
        this.userRepository = userRepository;
        this.httpServletRequest = httpServletRequest;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.diaryRepository = diaryRepository;
    }

    @Override
    public User getRemoteUser() {
        return userRepository.findByName(httpServletRequest.getRemoteUser());
    }

    @Override
    public Long getRemoteUserId() {
        return getRemoteUser().getId();
    }

    @Override
    public String getRemoteUserEmail() {
        return getRemoteUser().getEmail();
    }

    @Override
    public boolean checkRemoteUserPassword(String password) {
        return WebSecurityConfig.encoder().matches(password, getRemoteUser().getPassword());
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public User signUpUser(User user){
        return  signUp(user, 1L);
    }

    @Override
    public User signUp(User user, long roleId) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roleSet = new LinkedHashSet<>();
        roleRepository.findByNames(String.join(", ", new String[]{"ПОЛЬЗОВАТЕЛЬ"})).ifPresent(roleSet::add);
        user.setRoles(roleSet);
        user = userRepository.save(user);
        Diary diary =new Diary();
        diary.setUser(user);
        diaryRepository.save(diary);
        logger.info("Signed up new user [id:{}] with roles: {}", user.getId()
                , user.getRoles().stream().map(Role::getName).collect(Collectors.joining(", ")));
        return user;
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.countByEmail(email) != 0;
    }

    @Override
    public User saveEmail(String email) {
        User user = getRemoteUser();
        String oldEmail = user.getEmail();
        user.setEmail(email);
        user =  userRepository.save(user);
        logger.info("User {}[id:{}] change email {} to {}", user.getName(), user.getId(), oldEmail, user.getEmail());
        return user;
    }

    @Override
    public User savePassword(String password) {
        User user = getRemoteUser();
        user.setPassword(WebSecurityConfig.encoder().encode(password));
        user = userRepository.save(user);
        logger.info("User {}[id:{}] change password to {}", user.getName(), user.getId(), password);
        return user;
    }
}