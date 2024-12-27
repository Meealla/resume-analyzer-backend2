package webapp.resumegenerator.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Ildar Khuzin
 * @version 1.0.0
 *  Настройки для отображения каждого блока (стили, текстовые данные).
 */
@Data
@NoArgsConstructor
@Document(collection = "section_element_props")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SectionElementProps {
    @Id
    private UUID id;

    @JsonProperty("key")
    private String key;

    @JsonProperty("text")
    private String text;

    @JsonProperty("wrapperStyle")
    private String wrapperStyle;

    @JsonProperty("textStyle")
    private String textStyle;

    @JsonProperty("inputStyle")
    private String inputStyle;

    @JsonProperty("url")
    private String url;

    @JsonProperty("style")
    private String style;

    public SectionElementProps(UUID id, String key, String text, String wrapperStyle, String textStyle,
                               String inputStyle, String url, String style) {
        this.id = id;
        this.key = key;
        this.text = text;
        this.wrapperStyle = wrapperStyle;
        this.textStyle = textStyle;
        this.inputStyle = inputStyle;
        this.url = url;
        this.style = style;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SectionElementProps that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}