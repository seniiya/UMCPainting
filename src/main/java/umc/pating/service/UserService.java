package umc.pating.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.pating.entity.User;
import umc.pating.entity.enums.Category;
import umc.pating.entity.enums.Status;
import umc.pating.repository.UserRepository;
import umc.pating.services.UserRequestDTO;
import umc.pating.services.UserResponseDTO;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    // 현재 로그인한 유저 가져오기
    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("인증된 유저 정보를 찾을 수 없습니다."));
    }

//    // 닉네임 수정
//    public UserResponseDTO updateNickname(Long userId, String nickname) {
//        User user = getCurrentUser(userId);
//        user.setNickname(nickname);
//        userRepository.save(user);
//        return new UserResponseDTO(user);
//    }
//
//    // 출생년도 수정
//    public UserResponseDTO updateBirthYear(Long userId, Integer birthYear) {
//        User user = getCurrentUser(userId);
//        user.setBirthYear(birthYear);
//        userRepository.save(user);
//        return new UserResponseDTO(user);
//    }
//
//    // 신분 수정
//    public UserResponseDTO updateStatus(Long userId, Status status) {
//        User user = getCurrentUser(userId);
//        user.setStatus(status);
//        userRepository.save(user);
//        return new UserResponseDTO(user);
//    }
//
//    // 입시유형 수정
//    public UserResponseDTO updateCategory(Long userId, Category category) {
//        User user = getCurrentUser(userId);
//        user.setCategory(category);
//        userRepository.save(user);
//        return new UserResponseDTO(user);
//    }

    // 닉네임 수정
    public UserResponseDTO updateNickname(Long userId, String nickname) {
        User user = getUserById(userId);
        user.setNickname(nickname);
        return new UserResponseDTO(userRepository.save(user));
    }

    // 출생년도 수정
    public UserResponseDTO updateBirthYear(Long userId, Integer birthYear) {
        User user = getUserById(userId);
        user.setBirthYear(birthYear);
        return new UserResponseDTO(userRepository.save(user));
    }

    // 신분 수정
    public UserResponseDTO updateStatus(Long userId, Status status) {
        User user = getUserById(userId);
        user.setStatus(status);
        return new UserResponseDTO(userRepository.save(user));
    }

    // 입시유형 수정
    public UserResponseDTO updateCategory(Long userId, Category category) {
        User user = getUserById(userId);
        user.setCategory(category);
        return new UserResponseDTO(userRepository.save(user));

    }
}
