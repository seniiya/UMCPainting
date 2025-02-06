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

    // 특정 daily 댓글 조회 - 나 근데 여기 코드 이해가 전혀 안됨
    public List<DailyCommentResponseDTO> getDailyComments(Long dailyId) {
        List<DailyComment> comments = dailyCommentRepository.findByDailyId(dailyId);
        return comments.stream().map(DailyCommentResponseDTO::new).collect(Collectors.toList());
    }

    // 특정 daily 댓글 저장
    public DailyCommentResponseDTO saveDailyComment(Long dailyId, DailyCommentRequestDTO request) {
        Daily daily = dailyRepository.findById(dailyId)
                .orElseThrow(() -> new RuntimeException("해당 Daily 기록 존재하지 않음"));

        DailyComment dailyComment = DailyComment.builder()
                .daily(daily)
                .title(request.getTitle())
                .content(request.getContent())
                .x(request.getX())
                .y(request.getY())
                .build();

        dailyCommentRepository.save(dailyComment);
        return new DailyCommentResponseDTO(dailyComment);
    }
}
