package s_lab.sichniy_andriy.portier_digital.model.dto;

import java.io.Serializable;

/**
 * DTO for {@link s_lab.sichniy_andriy.portier_digital.model.Article}
 */
public record ArticleDto(
        Long id,
        String title,
        String content,
        String imagePath
        ) implements Serializable {
}
