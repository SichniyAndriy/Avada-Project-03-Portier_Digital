package s_lab.sichniy_andriy.portier_digital.model.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import s_lab.sichniy_andriy.portier_digital.model.Contacts;
import s_lab.sichniy_andriy.portier_digital.model.dto.ContactsDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = ComponentModel.SPRING)
public interface ContactsMapper {

    Contacts toEntity(ContactsDto contactsDto);

    ContactsDto toDto(Contacts contacts);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Contacts partialUpdate(ContactsDto contactsDto, @MappingTarget Contacts contacts);

}