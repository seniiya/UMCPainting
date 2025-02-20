package umc.pating.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import umc.pating.auth.PrincipalDetails;
import umc.pating.service.MonthlyService;
import umc.pating.services.MonthlyResponseDTO;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/record/monthly")
public class MonthlyController {
    private final MonthlyService monthlyService;

//    @GetMapping("")
//    public ResponseEntity<List<MonthlyResponseDTO>> getMonthly (
//            @AuthenticationPrincipal PrincipalDetails principalDetails,
//            @RequestParam Integer year,
//            @RequestParam Integer month
//    ) {
//        if (principalDetails == null) {
//            return ResponseEntity.status(401).build();
//        }
//
//        Long userId = principalDetails.getUser().getId();
//        return ResponseEntity.ok(monthlyService.getMonthly(userId, year, month));
//
//    }

    @GetMapping("")
    public ResponseEntity<List<MonthlyResponseDTO>> getMonthly(
            @RequestParam Long userId, // ✅ userId 직접 받기
            @RequestParam Integer year,
            @RequestParam Integer month
    ) {
        System.out.println("✅ [getMonthly] API 호출됨 - userId: " + userId + ", year: " + year + ", month: " + month);

        return ResponseEntity.ok(monthlyService.getMonthly(userId, year, month));
    }

}
