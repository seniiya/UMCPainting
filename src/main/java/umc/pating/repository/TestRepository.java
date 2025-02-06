package umc.pating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import umc.pating.entity.Test;

import java.time.LocalDate;
import java.util.Optional;

@Repository

public interface TestRepository extends JpaRepository<Test, Long> {
    // 특정 날짜 daily 조회
    @Query("SELECT d FROM Test d WHERE d.user.id = :userId AND d.testDayRecording = :date")
    Optional<Test> findByUserIdAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);

    Optional<Test> findByUserIdAndTestDayRecording(Long userId, LocalDate dailyDayRecording);
}
