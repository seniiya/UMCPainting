package umc.pating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.pating.entity.ScheduleDetail;

import java.util.List;

@Repository
public interface ScheduleDetailRepository extends JpaRepository<ScheduleDetail, Long> {
    List<ScheduleDetail> findByScheduleId(Long scheduleId);
}

