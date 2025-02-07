package umc.pating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.pating.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    // 이메일 기반 유저 조회
//    Optional<User> findByEmail(@Param("email") String email);
    Optional<User> findById(Long id);
    public User findByUserName(String userName);
}
