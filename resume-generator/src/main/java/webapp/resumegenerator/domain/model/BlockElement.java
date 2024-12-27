package webapp.resumegenerator.domain.model;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * @author Ildar Khuzin
 * @version 1.0.0
 * Основной класс, который описывает блоки в резюме.
 */
@SuppressWarnings("checkstyle:SummaryJavadoc")
@Document(collection = "block_elements")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public class BlockElement {
    @Id
    private UUID id;

    @JsonProperty("name")
    @NotNull(message = "Имя не может быть пустым.")
    private String name;

    @JsonProperty("title")
    @NotNull(message = "Заголовок не может быть пустым.")
    private String title;

    @JsonProperty("type")
    @NotNull(message = "Тип не может быть пустым.")
    private String type;

    @JsonProperty("source")
    @NotNull
    private String source;

    @JsonProperty("columns")
    @NotNull
    private Integer columns;

    private SectionElementProps props;

    private List<BlockElement> children;

    private Layout layout;

    public BlockElement(UUID id, String name, String title, String type, String source, Integer columns,
                        SectionElementProps props, List<BlockElement> children, Layout layout) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.type = type;
        this.source = source;
        this.columns = columns;
        this.props = props;
        this.children = children;
        this.layout = layout;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlockElement that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}