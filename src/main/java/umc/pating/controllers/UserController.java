package umc.pating.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.pating.service.UserService;
import umc.pating.services.UserRequestDTO;
import umc.pating.services.UserResponseDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    // 닉네임 수정
    @PatchMapping("/{userId}/nickname")
    public ResponseEntity<UserResponseDTO> updateNickname(
            @PathVariable Long userId,
            @RequestBody UserRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(userService.updateNickname(userId, requestDTO.getNickname()));
    }

    // 출생년도 수정
    @PatchMapping("/{userId}/birthYear")
    public ResponseEntity<UserResponseDTO> updateBirthYear(
            @PathVariable Long userId,
            @RequestBody UserRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(userService.updateBirthYear(userId, requestDTO.getBirthYear()));
    }

    // 신분 수정
    @PatchMapping("/{userId}/status")
    public ResponseEntity<UserResponseDTO> updateStatus(
            @PathVariable Long userId,
            @RequestBody UserRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(userService.updateStatus(userId, requestDTO.getStatus()));
    }

    // 입시유형 수정
    @PatchMapping("/{userId}/category")
    public ResponseEntity<UserResponseDTO> updateCategory(
            @PathVariable Long userId,
            @RequestBody UserRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(userService.updateCategory(userId, requestDTO.getCategory()));
    }
}