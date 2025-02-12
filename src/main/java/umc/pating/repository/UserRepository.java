package umc.pating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.pating.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {


    Optional<User> findById(Long id);
    public User findByUserName(String userName);

    Optional<User> findByEmail(String email);
    Optional<User> findByRefreshToken(String refreshToken); // ✅ Refresh Token으로 사용자 조회
}
