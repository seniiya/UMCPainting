package umc.pating.services;

import lombok.*;
import umc.pating.entity.DailyComment;
import umc.pating.entity.TestComment;

@Getter
public class TestCommentResponseDTO {
    private final Long id;
    private final String title;
    private final String content;
    private final int x;
    private final int y;

    public TestCommentResponseDTO(TestComment testComment) {
        this.id = testComment.getId();
        this.title = testComment.getTitle();
        this.content = testComment.getContent();
        this.x = testComment.getX();
        this.y = testComment.getY();
    }
}
