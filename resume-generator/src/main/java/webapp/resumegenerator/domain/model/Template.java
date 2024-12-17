package webapp.resumegenerator.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * Класс, представляющий шаблон резюме.
 * Данный класс хранит информацию о шаблоне,такую как название шаблона, описания,
 * контент и дату создания.
 */

@Document(collection = "templates")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Template {
    /**
     * Уникальный идентификатор, генерируется при создании нового шаблона.
     */
    @Id
    @JsonDeserialize(using = UUIDDeserializer.class)
    private UUID id;

    /**
     * Название шаблона.
     * Не должно быть пустым.
     */
    @NotBlank(message = "Название шаблона не может быть пустым")
    private String name;

    /**
     * Описание шаблона.
     * Не должно быть пустым.
     */
    @NotBlank(message = "Описание шаблона не может быть пустым")
    private String description;

    /**
     * Контент шаблона.
     * Не должен быть пустым.
     */
    @NotBlank(message = "Контент шаблона не может быть пустым")
    private String content;

    /**
     * Дата и время создания шаблона.
     * Устанавливается автоматически в момент создания шаблона.
     */
    @NotBlank()
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    /**
     * Конструктор с параметрами для создания шаблона.
     *
     * @param name Название шаблона.
     * @param description Описание шаблона.
     * @param content Контент шаблона.
     */
    public Template(String name, String description, String content) {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.name = name;
        this.description = description;
        this.content = content;
    }
  
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Template template = (Template) o;
        return Objects.equals(id, template.id) &&
          Objects.equals(name, template.name);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id, name);
    }
}
