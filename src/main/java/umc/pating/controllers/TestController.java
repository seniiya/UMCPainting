package umc.pating.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import umc.pating.auth.PrincipalDetails;
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
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestParam String date
    ) {
//        if (principalDetails == null) {
//            return ResponseEntity.status(401).build();
//        }
        LocalDate localDate = LocalDate.parse(date);
        Long userId = principalDetails.getUser().getId();

        return ResponseEntity.ok(testService.getTest(userId, localDate));

    }

    @PostMapping("/save")
    public ResponseEntity<TestResponseDTO> saveTest(
            @RequestBody TestRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(testService.saveTest(requestDTO.getUserId(), requestDTO));
    }
}
