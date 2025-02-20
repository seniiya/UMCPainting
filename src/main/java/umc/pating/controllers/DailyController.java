package umc.pating.controllers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.pating.auth.PrincipalDetails;
import umc.pating.aws.s3.AmazonS3Manager;
import umc.pating.service.DailyService;
import umc.pating.services.DailyRequestDTO;
import umc.pating.services.DailyResponseDTO;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/record/daily")
public class DailyController {

    @Autowired
    private final DailyService dailyService;
    private final AmazonS3Manager amazonS3Manager;

    // 조회
    @GetMapping("/get")
    public ResponseEntity<DailyResponseDTO> getDaily(
            @RequestParam Long userId,  // ✅ userId 직접 받음
            @RequestParam String date
    ) {
        System.out.println("✅ [getDaily] API 호출됨 - userId: " + userId + ", date: " + date);
        System.out.println("get 함수 실행");
//        if (principalDetails == null) {
//            System.out.println("❌ AuthenticationPrincipal is NULL (JWT 인증 실패)");
//            return ResponseEntity.status(401).build();
//        }
//        LocalDate localDate = LocalDate.parse(date);
//        Long userId = principalDetails.getUser().getId();

//        DailyResponseDTO dailyResponseDTO = dailyService.getDaily(userId, localDate);

        LocalDate localDate = LocalDate.parse(date);
        DailyResponseDTO dailyResponseDTO = dailyService.getDaily(userId, localDate);

        // ✅ 이미지 URL 확인 로그
        System.out.println("✅ 이미지 URL: " + dailyResponseDTO.getDrawing());

        return ResponseEntity.ok(dailyService.getDaily(userId, localDate));
    }

    @PostMapping(value = "/savefile", consumes = {"multipart/form-data"})
    public ResponseEntity<String> uploadDrawing(
            @RequestPart(value = "file", required = true) MultipartFile file // ✅ 안드로이드에서 보낸 파일
    ) throws IOException {
        System.out.println("✅ [uploadDrawing] API 호출됨");
        try {
            String fileUrl = amazonS3Manager.uploadFile(file); // ✅ S3 업로드 후 URL 반환
            return ResponseEntity.ok(fileUrl); // ✅ 업로드된 이미지 URL을 클라이언트에 반환
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드 실패");
        }
//        System.out.println("✅ [uploadDrawing] API 호출됨");
//
//        // ✅ S3에 업로드하여 URL 반환
//        String drawingUrl = amazonS3Manager.uploadFile(drawing);
//
//        System.out.println("📌 업로드된 이미지 URL: " + drawingUrl);
//
//        return ResponseEntity.ok(drawingUrl); // ✅ S3 URL을 반환
    }


    // 작성 (이미지 포함)
    @PostMapping(value = "/save", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<DailyResponseDTO> saveDaily(
            @RequestPart(value = "request", required = false) DailyRequestDTO request // JSON 데이터
    ) throws IOException {
        System.out.println("✅ [saveDaily] API 호출됨");
        System.out.println("📌 받은 JSON 데이터: " + request);

        // JSON 데이터를 객체로 변환
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule()); // LocalDate 지원 추가
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        DailyRequestDTO requestDTO = objectMapper.readValue(requestData, DailyRequestDTO.class);

//        request.setDrawing(drawing); // DTO에 파일 설정

        return ResponseEntity.ok(dailyService.saveDaily(request.getUserId(), request));
    }
}