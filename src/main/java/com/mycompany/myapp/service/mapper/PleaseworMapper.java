package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.PleaseworDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Pleasewor and its DTO PleaseworDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PleaseworMapper {

    PleaseworDTO pleaseworToPleaseworDTO(Pleasewor pleasewor);

    List<PleaseworDTO> pleaseworsToPleaseworDTOs(List<Pleasewor> pleasewors);

    Pleasewor pleaseworDTOToPleasewor(PleaseworDTO pleaseworDTO);

    List<Pleasewor> pleaseworDTOsToPleasewors(List<PleaseworDTO> pleaseworDTOs);
}
