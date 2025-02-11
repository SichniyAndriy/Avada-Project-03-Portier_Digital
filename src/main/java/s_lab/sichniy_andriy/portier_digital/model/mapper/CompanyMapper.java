package s_lab.sichniy_andriy.portier_digital.model.mapper;

import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import s_lab.sichniy_andriy.portier_digital.model.Company;
import s_lab.sichniy_andriy.portier_digital.model.dto.CompanyDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = ComponentModel.SPRING)
public interface CompanyMapper {

    Company toEntity(CompanyDto companiesDto);

    CompanyDto toDto(Company companies);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Company partialUpdate(CompanyDto companiesDto, @MappingTarget Company companies);

    @IterableMapping(elementTargetType = CompanyDto.class)
    List<CompanyDto> toDto(List<Company> companies);

}