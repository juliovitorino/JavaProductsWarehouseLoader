package br.com.juliovitorino.msproductcharger.mapper;

import br.com.juliovitorino.msproductcharger.dtos.WarehouseDTO;
import br.com.juliovitorino.msproductcharger.models.WarehouseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WarehouseMapper {

    WarehouseMapper INSTANCE = Mappers.getMapper(WarehouseMapper.class);

    @Mapping(source = "locality", target="locality")
    @Mapping(source = "quantity", target="quantity")
    @Mapping(source = "type", target="type")
    WarehouseModel requestDtoModel(WarehouseDTO warehouseDTO);

}
