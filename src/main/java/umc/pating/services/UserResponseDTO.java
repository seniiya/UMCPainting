package umc.pating.services;

import lombok.Getter;
import lombok.Setter;
import umc.pating.entity.User;
import umc.pating.entity.enums.Category;
import umc.pating.entity.enums.Status;

@Getter
@Setter
public class UserResponseDTO {
    private String nickname;
    private Integer birthYear;
    private Status status;
    private Category category;

    public UserResponseDTO(User user) {
        this.nickname = user.getNickname();
        this.birthYear = user.getBirthYear();
        this.status = user.getStatus();
        this.category = user.getCategory();
    }
}