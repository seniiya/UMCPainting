package umc.pating.controllers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.pating.auth.PrincipalDetails;
import umc.pating.service.DailyService;
import umc.pating.services.DailyRequestDTO;
import umc.pating.services.DailyResponseDTO;

import java.io.IOException;
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
        System.out.println("get 함수 실행");
        if (principalDetails == null) {
            System.out.println("❌ AuthenticationPrincipal is NULL (JWT 인증 실패)");
            return ResponseEntity.status(401).build();
        }
        LocalDate localDate = LocalDate.parse(date);
        Long userId = principalDetails.getUser().getId();

        DailyResponseDTO dailyResponseDTO = dailyService.getDaily(userId, localDate);

        // ✅ 이미지 URL 확인 로그
        System.out.println("✅ 이미지 URL: " + dailyResponseDTO.getDrawing());

        return ResponseEntity.ok(dailyService.getDaily(userId, localDate));
    }

    // 작성 (이미지 포함)
    @PostMapping(value = "/save", consumes = {"multipart/form-data"})
    public ResponseEntity<DailyResponseDTO> saveDaily(
            @RequestPart(value = "data", required = false) DailyRequestDTO requestData, // JSON 데이터
            @RequestPart(value = "drawing", required = false) MultipartFile drawing // 파일 (선택적)
    ) throws IOException {
        System.out.println("✅ [saveDaily] API 호출됨");
        System.out.println("📌 받은 JSON 데이터: " + requestData);

        // JSON 데이터를 객체로 변환
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule()); // LocalDate 지원 추가
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        DailyRequestDTO requestDTO = objectMapper.readValue(requestData, DailyRequestDTO.class);

        requestData.setDrawing(drawing); // DTO에 파일 설정

        return ResponseEntity.ok(dailyService.saveDaily(requestData.getUserId(), requestData));
    }
}