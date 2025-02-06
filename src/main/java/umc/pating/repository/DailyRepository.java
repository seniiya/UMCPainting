package umc.pating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import umc.pating.entity.Daily;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface DailyRepository extends JpaRepository<Daily, Long> {

    // 특정 날짜 daily 조회
    @Query("SELECT d FROM Daily d WHERE d.user.id = :userId AND d.dailyDayRecording = :date")
    Optional<Daily> findByUserIdAndDailyDayRecording(@Param("userId") Long userId, @Param("date") LocalDate date);


}
