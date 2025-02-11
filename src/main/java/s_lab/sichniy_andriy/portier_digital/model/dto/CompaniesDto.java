package s_lab.sichniy_andriy.portier_digital.model.dto;

import java.io.Serializable;

/**
 * DTO for {@link s_lab.sichniy_andriy.portier_digital.model.Companies}
 */
public record CompaniesDto(
        Long id,
        String title,
        String position
) implements Serializable {}
