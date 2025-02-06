package umc.pating.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.pating.service.DailyService;
import umc.pating.services.DailyRequestDTO;
import umc.pating.services.DailyResponseDTO;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/record/daily") // api 겹쳐서 수정 어디가 / record/daily 겹치지
public class DailyController {
    private final DailyService dailyService;

    // 조회
    @GetMapping("/get")
    public ResponseEntity<DailyResponseDTO> getDaily(
            @RequestParam Long userId,
            @RequestParam String date
    ) {
        LocalDate localDate = LocalDate.parse(date);
        return ResponseEntity.ok(dailyService.getDaily(userId, localDate));
    }

    // 작성
    @PostMapping("/save")
    public ResponseEntity<DailyResponseDTO> saveDaily(
            @RequestBody DailyRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(dailyService.saveDaily(requestDTO.getUserId(), requestDTO));
    }



}
