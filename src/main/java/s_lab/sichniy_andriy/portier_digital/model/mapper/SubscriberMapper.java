package s_lab.sichniy_andriy.portier_digital.model.mapper;

import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import s_lab.sichniy_andriy.portier_digital.model.Subscriber;
import s_lab.sichniy_andriy.portier_digital.model.dto.SubscriberDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = ComponentModel.SPRING)
public interface SubscriberMapper {

    Subscriber toEntity(SubscriberDto subscriberDto);

    SubscriberDto toDto(Subscriber subscriber);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Subscriber partialUpdate(SubscriberDto subscriberDto, @MappingTarget Subscriber subscriber);

    @IterableMapping(elementTargetType = SubscriberDto.class)
    List<SubscriberDto> toDto(List<Subscriber> subscribers);

}
