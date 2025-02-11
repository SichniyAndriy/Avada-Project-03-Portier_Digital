package s_lab.sichniy_andriy.portier_digital.model.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import s_lab.sichniy_andriy.portier_digital.model.Companies;
import s_lab.sichniy_andriy.portier_digital.model.dto.CompaniesDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = ComponentModel.SPRING)
public interface CompaniesMapper {

    Companies toEntity(CompaniesDto companiesDto);

    CompaniesDto toDto(Companies companies);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Companies partialUpdate(CompaniesDto companiesDto, @MappingTarget Companies companies);

}