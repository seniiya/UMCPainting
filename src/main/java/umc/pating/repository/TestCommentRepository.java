package umc.pating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.pating.entity.DailyComment;
import umc.pating.entity.TestComment;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestCommentRepository extends JpaRepository<TestComment, Long> {
    // 특정 test 내용 조회
    List<TestComment> findByTestId(Long testId);

    Optional<TestComment> findByTestIdAndXAndY(Long testId, int x, int y);

}
