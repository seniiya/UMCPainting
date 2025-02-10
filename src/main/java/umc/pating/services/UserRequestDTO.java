package umc.pating.services;

import lombok.Getter;
import lombok.Setter;
import umc.pating.entity.enums.Category;
import umc.pating.entity.enums.Status;

@Getter
@Setter
public class UserRequestDTO {
    private String nickname;
    private Integer birthYear;
    private Status status;
    private Category category;
}