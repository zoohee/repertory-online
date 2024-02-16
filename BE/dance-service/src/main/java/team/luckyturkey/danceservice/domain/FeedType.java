package team.luckyturkey.danceservice.domain;

import lombok.Getter;

@Getter
public enum FeedType {
    SOURCE(0), REPERTORY(1);

    private final int value;

    FeedType(int value) {
        this.value = value;
    }

    public static FeedType fromValue(int value) {
        for (FeedType type : FeedType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid FeedType value: " + value);
    }
}