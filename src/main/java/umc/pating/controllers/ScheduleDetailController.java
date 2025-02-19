package umc.pating.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.pating.service.ScheduleDetailService;
import umc.pating.services.ScheduleDetailRequestDTO;
import umc.pating.services.ScheduleDetailResponseDTO;
import umc.pating.services.ScheduleResponseDTO;


@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule-details")
public class ScheduleDetailController {

    private final ScheduleDetailService scheduleDetailService;

    @GetMapping("/{detailId}") // 세부 스케줄 조회
    public ResponseEntity<ScheduleDetailResponseDTO> getSchedule(@PathVariable Long detailId) {
        return ResponseEntity.ok(scheduleDetailService.getScheduleDetail(detailId));
    }

    @PostMapping // 스케줄 생성
    public ResponseEntity<ScheduleDetailResponseDTO> createScheduleDetail(@RequestBody ScheduleDetailRequestDTO requestDTO) {
        ScheduleDetailResponseDTO response = scheduleDetailService.createScheduleDetail(requestDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{detailId}")
    public ResponseEntity<ScheduleDetailResponseDTO> updateScheduleDetail(
            @PathVariable Long detailId, @RequestBody ScheduleDetailRequestDTO requestDTO) {
        ScheduleDetailResponseDTO response = scheduleDetailService.updateScheduleDetail(detailId, requestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{detailId}") // 스케줄 삭제
    public ResponseEntity<String> deleteScheduleDetail(@PathVariable Long detailId) {
        scheduleDetailService.deleteScheduleDetail(detailId);
        return ResponseEntity.ok("삭제 완료");
    }
}
