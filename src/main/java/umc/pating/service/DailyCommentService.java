package umc.pating.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.pating.entity.Daily;
import umc.pating.entity.DailyComment;
import umc.pating.repository.DailyCommentRepository;
import umc.pating.repository.DailyRepository;
import umc.pating.services.DailyCommentRequestDTO;
import umc.pating.services.DailyCommentResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DailyCommentService {
    private final DailyCommentRepository dailyCommentRepository;
    private final DailyRepository dailyRepository;

    // 특정 daily 댓글 조회
    public List<DailyCommentResponseDTO> getDailyComments(Long dailyId) {
        List<DailyComment> comments = dailyCommentRepository.findByDailyId(dailyId);
        return comments.stream().map(DailyCommentResponseDTO::new).collect(Collectors.toList());
    }

    // 특정 daily 댓글 저장
    public DailyCommentResponseDTO saveDailyComment(Long dailyId, DailyCommentRequestDTO request) {
        Daily daily = dailyRepository.findById(dailyId)
                .orElseThrow(() -> new RuntimeException("해당 Daily 기록 존재하지 않음"));

        // 기존의 동일한 좌표(x, y)의 코멘트가 있는지 확인
        DailyComment dailyComment = dailyCommentRepository.findByDailyIdAndXAndY(dailyId, request.getX(), request.getY())
                .orElseGet(() -> DailyComment.builder()
                        .daily(daily)
                        .x(request.getX())
                        .y(request.getY())
                        .build());

        // null 값이 전달되지 않은 경우에만 업데이트
        if (request.getTitle() != null) {
            dailyComment.setTitle(request.getTitle());
        }
        if (request.getContent() != null) {
            dailyComment.setContent(request.getContent());
        }


        dailyCommentRepository.save(dailyComment);
        return new DailyCommentResponseDTO(dailyComment);
    }
}
