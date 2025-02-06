package umc.pating.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.pating.service.TestService;
import umc.pating.services.TestRequestDTO;
import umc.pating.services.TestResponseDTO;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("record/test")
public class TestController {
    private final TestService testService;

    // 조회
    @GetMapping("/get")
    public ResponseEntity<TestResponseDTO> getTest(
            @RequestParam Long userId,
            @RequestParam String date
    ) {
        LocalDate localDate = LocalDate.parse(date);
        return ResponseEntity.ok(testService.getTest(userId, localDate));
    }

    // 저장 수정 - 여러 개 작성 가능한데 ..
    @PostMapping("/save")
    public ResponseEntity<TestResponseDTO> saveTest(
            @RequestBody TestRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(testService.saveTest(requestDTO.getUserId(), requestDTO));
    }
}
