package umc.pating.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.pating.service.ScheduleService;
import umc.pating.services.ScheduleResponseDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

//    @GetMapping("/{userId}/{scheduleId}")
//    public ResponseEntity<ScheduleResponseDTO> getUserSchedule(
//            @PathVariable Long userId,
//            @PathVariable Long scheduleId
//    ) {
//        return ResponseEntity.ok(scheduleService.getUserSchedule(userId, scheduleId));
//    }

    @GetMapping("/{userId}/{year}")
    public ResponseEntity<ScheduleResponseDTO> getOrCreateSchedule(
            @PathVariable Long userId, @PathVariable int year) {
        return ResponseEntity.ok(scheduleService.getOrCreateSchedule(userId, year));
    }

}
