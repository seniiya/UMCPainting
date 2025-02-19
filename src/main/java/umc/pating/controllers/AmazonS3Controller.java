//package umc.pating.controllers;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import umc.pating.service.AwsS3Service;
//
//import java.util.List;
//
//// 다중 파일 업로드 시
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/file")
//public class AmazonS3Controller {
//
//    private final AwsS3Service awsS3Service;
//
//    @PostMapping
//    public ResponseEntity<List<String>> uploadFile(List<MultipartFile> multipartFiles){
//        return ResponseEntity.ok(awsS3Service.uploadFile(multipartFiles));
//    }
//
//    @DeleteMapping
//    public ResponseEntity<String> deleteFile(@RequestParam String fileName){
//        awsS3Service.deleteFile(fileName);
//        return ResponseEntity.ok(fileName);
//    }
//}
