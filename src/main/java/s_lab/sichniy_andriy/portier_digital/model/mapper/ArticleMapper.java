package s_lab.sichniy_andriy.portier_digital.model.mapper;

import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import s_lab.sichniy_andriy.portier_digital.model.Article;
import s_lab.sichniy_andriy.portier_digital.model.dto.ArticleDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = ComponentModel.SPRING)
public interface ArticleMapper {

    Article toEntity(ArticleDto articleDto);

    ArticleDto toDto(Article article);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Article partialUpdate(ArticleDto articleDto, @MappingTarget Article article);

    @IterableMapping(elementTargetType = ArticleDto.class)
    List<ArticleDto> toDto(List<Article> articles);

}