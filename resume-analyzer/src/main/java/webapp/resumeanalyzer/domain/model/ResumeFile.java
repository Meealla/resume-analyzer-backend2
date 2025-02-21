package webapp.resumeanalyzer.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

/**
 * Сущность ResumeFile для хранения файлов резюме в базе данных.
 *
 * TODO: Заменить поле owner на маппинг на пользователя
 */
@Entity
@Table(name = "resume_file")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeFile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true)
    private UUID id;

    @Column(unique = true)
    private String filename;

    private String fileType;

    private long size;

    private byte[] data;

    private LocalDateTime createdAt;

    //В дальнейшем заменить на сущность пользователя с мапингом.
    private String owner;

    public ResumeFile(String filename) {
        this.filename = filename;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        ResumeFile that = (ResumeFile) o;
        return size == that.size && Objects.equals(id, that.id) &&
                Objects.equals(filename, that.filename) &&
                Objects.equals(fileType, that.fileType) &&
                Objects.deepEquals(data, that.data) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, filename, fileType, size, Arrays.hashCode(data), createdAt, owner);
    }
}
