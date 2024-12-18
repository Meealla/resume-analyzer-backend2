package webapp.resumebuilder.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Ildar Khuzin
 * @version 1.0.0
 * Основной класс, который описывает блоки в резюме.
 */
@Document(collection = "block_elements")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlockElement {

    @Id
    private UUID id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("title")
    private String title;

    @JsonProperty("type")
    private String type;

    @JsonProperty("source")
    private String source;

    @JsonProperty("columns")
    private Integer columns;

    @DBRef
    @JsonProperty("props")
    private SectionElementProps props;

    @DBRef
    @JsonProperty("children")
    private List<BlockElement> children;

    @DBRef
    @JsonProperty("layout")
    private Layout layout;

    public BlockElement() {
        this.id = UUID.randomUUID();
    }

    public BlockElement(String name, String title, String type, String source, Integer columns,
                        SectionElementProps props, List<BlockElement> children, Layout layout) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.title = title;
        this.type = type;
        this.source = source;
        this.columns = columns;
        this.props = props;
        this.children = children;
        this.layout = layout;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getColumns() {
        return columns;
    }

    public void setColumns(Integer columns) {
        this.columns = columns;
    }

    public SectionElementProps getProps() {
        return props;
    }

    public void setProps(SectionElementProps props) {
        this.props = props;
    }

    public List<BlockElement> getChildren() {
        return children;
    }

    public void setChildren(List<BlockElement> children) {
        this.children = children;
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
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
