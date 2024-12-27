package webapp.resumegenerator.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Ildar Khuzin
 * @version 1.0.0
 * Данные для позиционирования блоков на странице.
 */
@Document(collection = "layouts") // Указывает, что класс представляет коллекцию в MongoDB
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Layout {

    @Id
    private UUID id; // MongoDB использует это поле как уникальный идентификатор

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

    @ElementCollection
    @JsonProperty("resizeHandles")
    private List<String> resizeHandles;

    @JsonProperty("isBounded")
    private Boolean isBounded;

    public Layout() {}

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public Integer getMinW() {
        return minW;
    }

    public void setMinW(Integer minW) {
        this.minW = minW;
    }

    public Integer getMaxW() {
        return maxW;
    }

    public void setMaxW(Integer maxW) {
        this.maxW = maxW;
    }

    public Integer getMinH() {
        return minH;
    }

    public void setMinH(Integer minH) {
        this.minH = minH;
    }

    public Integer getMaxH() {
        return maxH;
    }

    public void setMaxH(Integer maxH) {
        this.maxH = maxH;
    }

    public Boolean getMoved() {
        return moved;
    }

    public void setMoved(Boolean moved) {
        this.moved = moved;
    }

    public Boolean getStatic() {
        return isStatic;
    }

    public void setStatic(Boolean aStatic) {
        isStatic = aStatic;
    }

    public Boolean getDraggable() {
        return isDraggable;
    }

    public void setDraggable(Boolean draggable) {
        isDraggable = draggable;
    }

    public Boolean getResizable() {
        return isResizable;
    }

    public void setResizable(Boolean resizable) {
        isResizable = resizable;
    }

    public List<String> getResizeHandles() {
        return resizeHandles;
    }

    public void setResizeHandles(List<String> resizeHandles) {
        this.resizeHandles = resizeHandles;
    }

    public Boolean getBounded() {
        return isBounded;
    }

    public void setBounded(Boolean bounded) {
        isBounded = bounded;
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
