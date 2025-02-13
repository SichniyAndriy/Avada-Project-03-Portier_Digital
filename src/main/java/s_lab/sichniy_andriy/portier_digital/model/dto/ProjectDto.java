package s_lab.sichniy_andriy.portier_digital.model.dto;

import java.io.Serializable;

/**
 * DTO for {@link s_lab.sichniy_andriy.portier_digital.model.Project}
 */
public record ProjectDto(
        Long id,
        String title,
        String description,
        String picturePath
) implements Serializable {
}