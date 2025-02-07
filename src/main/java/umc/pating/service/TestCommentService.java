package umc.pating.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.pating.entity.Test;
import umc.pating.entity.TestComment;
import umc.pating.repository.TestCommentRepository;
import umc.pating.repository.TestRepository;
import umc.pating.services.TestCommentRequestDTO;
import umc.pating.services.TestCommentResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TestCommentService {
    private final TestCommentRepository testCommentRepository;
    @Getter
    private final TestRepository testRepository;

    //test 댓글 조회
    public List<TestCommentResponseDTO> getTestComment(Long testId) {
        List<TestComment> comments = testCommentRepository.findByTestId(testId);
        return comments.stream().map(TestCommentResponseDTO::new).collect(Collectors.toList());
    }

    // 특정 daily 댓글 저장
    public TestCommentResponseDTO saveTestComment(Long testId, TestCommentRequestDTO request) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("해당 Daily 기록 존재하지 않음"));

        /// 기존의 동일한 좌표(x, y)의 코멘트가 있는지 확인
    TestComment testComment = testCommentRepository.findByTestIdAndXAndY(testId, request.getX(), request.getY())
            .orElseGet(() -> TestComment.builder()
                    .test(test)
                    .x(request.getX())
                    .y(request.getY())
                    .build());

    // null 값이 전달되지 않은 경우에만 업데이트
        if (request.getTitle() != null) {
        testComment.setTitle(request.getTitle());
    }
        if (request.getContent() != null) {
        testComment.setContent(request.getContent());
    }

        testCommentRepository.save(testComment);
        return new TestCommentResponseDTO(testComment);
    }
}
