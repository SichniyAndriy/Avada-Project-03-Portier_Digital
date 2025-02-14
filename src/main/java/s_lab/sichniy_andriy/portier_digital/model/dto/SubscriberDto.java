package s_lab.sichniy_andriy.portier_digital.model.dto;

import java.io.Serializable;

/**
 * DTO for {@link s_lab.sichniy_andriy.portier_digital.model.Subscriber}
 */
public record SubscriberDto(
        Long id,
        String email
) implements Serializable {
}