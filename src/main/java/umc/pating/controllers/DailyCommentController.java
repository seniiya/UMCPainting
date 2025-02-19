package umc.pating.controllers;


import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.pating.service.DailyCommentService;
import umc.pating.services.DailyCommentRequestDTO;
import umc.pating.services.DailyCommentResponseDTO;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/record/daily/comment")
public class DailyCommentController {
    private final DailyCommentService dailyCommentService;

    // 특정 daily 댓글 조회
    @GetMapping("/{dailyId}")
    public ResponseEntity<List<DailyCommentResponseDTO>> getDailyComments(@PathVariable Long dailyId) {
        return ResponseEntity.ok(dailyCommentService.getDailyComments(dailyId));
    }

    // 특정 daily 댓글 저장
    @PostMapping("/{dailyId}/save")
    public ResponseEntity<DailyCommentResponseDTO> saveDailyComment(
            @PathVariable Long dailyId,
            @RequestBody DailyCommentRequestDTO request
    ) {
        return ResponseEntity.ok(dailyCommentService.saveDailyComment(dailyId,request));
    }
}
