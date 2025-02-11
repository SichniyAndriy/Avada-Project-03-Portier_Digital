package s_lab.sichniy_andriy.portier_digital.model.dto;

import java.io.Serializable;

/**
 * DTO for {@link s_lab.sichniy_andriy.portier_digital.model.Contacts}
 */
public record ContactsDto(
        Long id,
        String type,
        String address
) implements Serializable {}
