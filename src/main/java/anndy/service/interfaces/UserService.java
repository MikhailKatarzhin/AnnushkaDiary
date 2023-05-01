package anndy.service.interfaces;

import anndy.model.User;

public interface UserService {
    User getRemoteUser();

    Long getRemoteUserId();

    String getRemoteUserEmail();

    boolean checkRemoteUserPassword(String password);

    User getById(Long id);

    User getByName(String username);

    User signUpUser(User user);

    User signUp(User user, long roleId);

    boolean emailExists(String email);

    User saveEmail(String email);

    User savePassword(String password);
}
