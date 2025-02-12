package umc.pating.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.pating.service.TestCommentService;
import umc.pating.services.TestCommentRequestDTO;
import umc.pating.services.TestCommentResponseDTO;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/record/test/comment")
public class TestCommentController {
    private final TestCommentService testCommentService;

    // 특정 daily 댓글 조회
    @GetMapping("/{testId}")
    public ResponseEntity<List<TestCommentResponseDTO>> getTestComment(@PathVariable Long testId) {
        return ResponseEntity.ok(testCommentService.getTestComment(testId));
    }

    // 특정 daily 댓글 저장
    @PostMapping("/{testId}/save")
    public ResponseEntity<TestCommentResponseDTO> saveTestComment(
            @PathVariable Long testId,
            @RequestBody TestCommentRequestDTO request
    ) {
        return ResponseEntity.ok(testCommentService.saveTestComment(testId, request));
    }
}
