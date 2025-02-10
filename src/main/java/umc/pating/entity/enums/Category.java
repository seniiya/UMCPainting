package umc.pating.entity.enums;

public enum Category {
    DESIGN("기초디자인"),
    WATERCOLOR("수채화"),
    CHANGE("사고의 전환"),
    WESTERN("서양화"),
    ORIENTAL("동양화"),
    MOCK("조소"),
    EXPRESSION("발상과 표현"),
    BASIC("기초소양"),
    DRAWING("소묘"),
    ANIMATION("애니메이션"),
    PORTFOLIO("포트폴리오"),
    INTERVIEW("면접");

    private final String description;

    Category(String description) {
        this.description = description;
    }


    public String getDescription() {
        return description;
    }
}
