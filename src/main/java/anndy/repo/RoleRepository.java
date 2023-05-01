package anndy.repo;

import anndy.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query(
            value = "select * from \"Role\" where id = ?",
            nativeQuery = true
    )
    Role getById(Long id);

    @Query(
            value = "select * from \"Role\" where name = ?",
            nativeQuery = true
    )
    Role getByName(String name);

    @Query(
            value = "select * from \"Role\" r where r.name in (?)",
            nativeQuery = true
    )
    Optional<Role> findByNames(String name);
}