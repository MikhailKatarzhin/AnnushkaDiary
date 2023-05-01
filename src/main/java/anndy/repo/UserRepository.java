package anndy.repo;

import anndy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);

    @Query(
            value = "select count(*) from \"User\" where email = ?",
            nativeQuery = true
    )
    Long countByEmail(String email);

    @Query(
            value = "select count(*) from \"User_Role\" UR " +
                    "join \"Role\" R on R.id = UR.role_id " +
                    "join \"User\" U on U.id = UR.user_id " +
                    "where role_id = ?",
            nativeQuery = true
    )
    Long countByRoleId(Long roleId);

    @Query(
            value = "SELECT * FROM \"User_Role\" UR "
                    + "join \"Role\" R on R.id = UR.role_id "
                    + "join \"User\" U on U.id = UR.user_id "
                    + " WHERE role_id = ?1"
                    + " ORDER BY U.name"
                    + " LIMIT ?2 OFFSET ?3"
            , nativeQuery = true
    )
    List<User> selectByRoleIdAndLimitOffset(Long roleId, Long limit, Long offset);
}