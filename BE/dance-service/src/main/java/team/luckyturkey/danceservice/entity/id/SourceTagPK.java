package team.luckyturkey.danceservice.entity.id;

import java.io.Serializable;
import java.util.Objects;

public class SourceTagPK implements Serializable {
    private Long source;
    private Long tag;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SourceTagPK that = (SourceTagPK) o;
        return Objects.equals(source, that.source) &&
                Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, tag);
    }
}
