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

import java.util.List;

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
    public UserResponseDTO updateCategory(Long userId, List<Category> category) {
        User user = getUserById(userId);
        user.setCategory(category);

        userRepository.save(user);  // ✅ 변경된 값 저장

        System.out.println("✅ 저장된 카테고리: " + user.getCategory());  // 🔥 디버깅 로그 추가
        return new UserResponseDTO(userRepository.save(user));

    }

    // 회원가입 시 joinyear 자동설정
    public User registerUser(String userName, String email, Integer birthYear, String role) {
        User user = User.createUser(userName, email, birthYear, role);
        return userRepository.save(user);
    }
}
