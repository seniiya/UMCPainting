package umc.pating.entity.enums;

public enum Status {
    ELEMENT("초등학생"),
    MIDDLE("중학생"),
    HIGH("고등학생"),
    UNIVERSITY("대학생"),
    ADULT("일반인"),
    GAPYEAR("휴학생"),
    QUALIFICATION("검정고시");

    private final String description;

    Status(String description) {
        this.description = description;
    }


    public String getDescription() {
        return description;
    }
}
