package umc.pating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.pating.entity.DailyComment;
import umc.pating.entity.TestComment;

import java.util.List;

public interface TestCommentRepository extends JpaRepository<TestComment, Long> {
    List<TestComment> findByTestId(Long testId);

}
