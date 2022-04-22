package br.com.juliovitorino.msproductcharger.mapper;

import br.com.juliovitorino.msproductcharger.dtos.ProductRequestDTO;
import br.com.juliovitorino.msproductcharger.models.ProductModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { WarehouseMapper.class } )
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "sku", target = "sku")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "warehouses", target = "warehouses")
    ProductModel requestDtoModel(ProductRequestDTO productRequestDTO);


}
