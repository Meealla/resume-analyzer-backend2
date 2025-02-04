package webapp.resumegenerator.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import jakarta.validation.constraints.Size;
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
    @JsonSerialize(using = ToStringSerializer.class)
    private UUID id;

    /**
     * Название шаблона.
     * Не должно быть пустым.
     */
    @NotNull(message = "Имя шаблона не может быть пустым.")
    @Size(min = 3, message = "Имя шаблона должно содержать 3 и более символов.")
    private String name;

    /**
     * Описание шаблона.
     * Не должно быть пустым.
     */
    @NotNull(message = "Описание шаблона не может быть пустым.")
    @Size(max = 500, message = "Содержание должно содержать не более 500 символов.")
    private String description;

    /**
     * Контент шаблона.
     * Не должен быть пустым.
     */
    @NotNull(message = "Контент шаблона не может быть пустым")
    private String content;

    /**
     * Дата и время создания шаблона.
     * Устанавливается автоматически в момент создания шаблона.
     */
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    /**
     * Версия шаблона.
     * Устанавливается автоматически в момент создания шаблона.
     */
    @NotNull()
    private Integer version;

    private Department department;

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
        this.version = 1;
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