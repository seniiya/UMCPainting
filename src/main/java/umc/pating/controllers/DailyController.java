package umc.pating.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import umc.pating.auth.PrincipalDetails;
import umc.pating.service.DailyService;
import umc.pating.services.DailyRequestDTO;
import umc.pating.services.DailyResponseDTO;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/record/daily")
public class DailyController {
    private final DailyService dailyService;

    // 조회
    @GetMapping("/get")
    public ResponseEntity<DailyResponseDTO> getDaily(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestParam String date
    ) {
        if (principalDetails == null) {
            System.out.println("❌ AuthenticationPrincipal is NULL (JWT 인증 실패)");
            return ResponseEntity.status(401).build();
        }
        LocalDate localDate = LocalDate.parse(date);
        Long userId = principalDetails.getUser().getId();

        return ResponseEntity.ok(dailyService.getDaily(userId, localDate));

    }


    // 작성
    @PostMapping("/save")
//    @PostMapping(value = "/save", consumes = {"multipart/form-data"})
    public ResponseEntity<DailyResponseDTO> saveDaily(
            @RequestBody DailyRequestDTO requestDTO
//            @RequestPart("data") DailyRequestDTO requestDTO, // JSON 데이터
//            @RequestPart(value = "drawing", required = false) MultipartFile drawing // 파일 (선택적)

    ) {
//        requestDTO.setDrawing(drawing); // DTO에 파일 설정
        return ResponseEntity.ok(dailyService.saveDaily(requestDTO.getUserId(), requestDTO));
    }



}
