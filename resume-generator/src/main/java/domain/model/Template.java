package domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "templates") @JsonIgnoreProperties(ignoreUnknown = true)
public class Template {
	@Id @JsonDeserialize(using = UUIDDeserializer.class) private UUID id;

	@NotBlank(message = "Название шаблона не может быть пустым") private String name;

	@NotBlank(message = "Описание шаблона не может быть пустым") private String description;

	@NotBlank(message = "Контент шаблона не может быть пустым") private String content;

	@NotBlank() @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") private LocalDateTime createdAt;

	public Template() {

	}

	public Template(String name, String description, String content) {
		this.id = UUID.randomUUID();
		this.createdAt = LocalDateTime.now();
		this.name = name;
		this.description = description;
		this.content = content;
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getContent() {
		return content;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setId(UUID id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Override public String toString() {
		return "Template{" + "id=" + id + ", name='" + name + '\'' + ", description='" + description
			+ '\'' + ", content='" + content + '\'' + ", createdAt=" + createdAt + '}';
	}
}
