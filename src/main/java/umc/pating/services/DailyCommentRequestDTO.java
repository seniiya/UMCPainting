package umc.pating.services;

import lombok.*;

@Getter
@Setter
public class DailyCommentRequestDTO {
    private String title;
    private String content;
    private int x;
    private int y;
}
