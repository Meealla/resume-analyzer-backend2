package domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Ildar Khuzin
 * @version 1.0.0
 * Основной класс, который описывает блоки в резюме.
 */
@SuppressWarnings("checkstyle:SummaryJavadoc")
@Entity
@Table(name = "block_element")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlockElement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private UUID id;

    @JsonProperty("name")
    @Column
    private String name;

    @JsonProperty("title")
    @Column
    private String title;

    @JsonProperty("type")
    @Column
    private String type;

    @JsonProperty("source")
    @Column
    private String source;

    @JsonProperty("columns")
    @Column
    private Integer columns;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "section_element_props_id")
    private SectionElementProps props;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BlockElement> children;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "layout_id")
    private Layout layout;

    public BlockElement() {}

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


