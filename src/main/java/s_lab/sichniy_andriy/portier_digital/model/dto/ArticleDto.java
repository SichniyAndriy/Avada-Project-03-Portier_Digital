package s_lab.sichniy_andriy.portier_digital.model.dto;

import java.io.Serializable;

/**
 * DTO for {@link s_lab.sichniy_andriy.portier_digital.model.Article}
 */
public record ArticleDto(
        Long id,
        String picturePath,
        String title,
        String content
) implements Serializable {}