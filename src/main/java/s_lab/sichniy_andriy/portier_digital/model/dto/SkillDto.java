package s_lab.sichniy_andriy.portier_digital.model.dto;

import java.io.Serializable;

/**
 * DTO for {@link s_lab.sichniy_andriy.portier_digital.model.Skill}
 */
public record SkillDto(
        Long id,
        String title,
        String description
) implements Serializable {}
