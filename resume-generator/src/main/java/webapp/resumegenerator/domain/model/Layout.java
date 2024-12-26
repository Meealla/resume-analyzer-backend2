package webapp.resumegenerator.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Ildar Khuzin
 * @version 1.0.0
 * Данные для позиционирования блоков на странице.
 */
@Data
@NoArgsConstructor
@Document(collection = "layouts")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Layout {
    @Id
    private UUID id;

    @JsonProperty("i")
    private String i;

    @JsonProperty("x")
    private int x;

    @JsonProperty("y")
    private int y;

    @JsonProperty("w")
    private int w;

    @JsonProperty("h")
    private int h;

    @JsonProperty("minW")
    private Integer minW;

    @JsonProperty("maxW")
    private Integer maxW;

    @JsonProperty("minH")
    private Integer minH;

    @JsonProperty("maxH")
    private Integer maxH;

    @JsonProperty("moved")
    private Boolean moved;

    @JsonProperty("isStatic")
    private Boolean isStatic;

    @JsonProperty("isDraggable")
    private Boolean isDraggable;

    @JsonProperty("isResizable")
    private Boolean isResizable;

    @JsonProperty("resizeHandles")
    private List<String> resizeHandles;

    @JsonProperty("isBounded")
    private Boolean isBounded;

    public Layout(UUID id, String i, int x, int y, int w, int h, Integer minW, Integer maxW, Integer minH,
                  Integer maxH, Boolean moved, Boolean isStatic, Boolean isDraggable, Boolean isResizable,
            List<String> resizeHandles, Boolean isBounded) {
        this.id = id;
        this.i = i;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.minW = minW;
        this.maxW = maxW;
        this.minH = minH;
        this.maxH = maxH;
        this.moved = moved;
        this.isStatic = isStatic;
        this.isDraggable = isDraggable;
        this.isResizable = isResizable;
        this.resizeHandles = resizeHandles;
        this.isBounded = isBounded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Layout layout)) return false;
        return Objects.equals(getId(), layout.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
