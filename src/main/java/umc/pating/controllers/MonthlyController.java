package umc.pating.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
//            @RequestParam Long userId,
//            @RequestParam Integer year,
//            @RequestParam Integer month
//    )
//    {
//        ResponseEntity.ok(monthlyService.getMonthly(userId, year, month));
//    }

}
