package umc.pating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import umc.pating.entity.DailyComment;

import java.util.List;
import java.util.Optional;

public interface DailyCommentRepository extends JpaRepository<DailyComment, Long> {


    // 특정 daily에 연결된 댓글 조회
    List<DailyComment> findByDailyId(Long dailyId);

    // 특정 dailyId와 좌표(x, y)가 일치하는 코멘트 조회
    Optional<DailyComment> findByDailyIdAndXAndY(Long dailyId, int x, int y);
}
