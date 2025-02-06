package umc.pating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import umc.pating.entity.DailyComment;

import java.util.List;

public interface DailyCommentRepository extends JpaRepository<DailyComment, Long> {

    // 특정 daily에 연결된 댓글 조회
    List<DailyComment> findByDailyId(Long dailyId);
}
