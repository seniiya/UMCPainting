package umc.pating.services;

import lombok.Getter;
import umc.pating.entity.DailyComment;

@Getter
public class DailyCommentResponseDTO {
    private final Long id;
    private final String title;
    private final String content;
    private final int x;
    private final int y;

    public DailyCommentResponseDTO(DailyComment dailyComment) {
        this.id = dailyComment.getId();
        this.title = dailyComment.getTitle();
        this.content = dailyComment.getContent();
        this.x = dailyComment.getX();
        this.y = dailyComment.getY();
    }
}
