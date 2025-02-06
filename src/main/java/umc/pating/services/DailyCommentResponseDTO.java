package umc.pating.services;

import lombok.Getter;
import umc.pating.entity.DailyComment;

@Getter
public class DailyCommentResponseDTO {
    private Long id;
    private String title;
    private String content;
    private int x;
    private int y;

    public DailyCommentResponseDTO(DailyComment dailyComment) {
        this.id = dailyComment.getId();
        this.title = dailyComment.getTitle();
        this.content = dailyComment.getContent();
        this.x = dailyComment.getX();
        this.y = dailyComment.getY();
    }
}
