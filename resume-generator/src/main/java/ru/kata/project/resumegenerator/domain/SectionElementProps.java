package ru.kata.project.resumegenerator.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Ildar Khuzin
 * @version 1.0.0
 *  Настройки для отображения каждого блока (стили, текстовые данные).
 */
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SectionElementProps {
    @Id
    @GeneratedValue
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

    public SectionElementProps() { }

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getWrapperStyle() {
        return wrapperStyle;
    }

    public void setWrapperStyle(String wrapperStyle) {
        this.wrapperStyle = wrapperStyle;
    }

    public String getTextStyle() {
        return textStyle;
    }

    public void setTextStyle(String textStyle) {
        this.textStyle = textStyle;
    }

    public String getInputStyle() {
        return inputStyle;
    }

    public void setInputStyle(String inputStyle) {
        this.inputStyle = inputStyle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
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