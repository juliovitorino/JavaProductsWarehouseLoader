package br.com.juliovitorino.msproductcharger.dtos;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

/**
 * Pattern DTO (Data Transfer Object) for exchange information between external calls
 *
 * @author Julio Vitorino
 * @since Apr 19, 2020
 */

public class ProductRequestDTO {

    @NotBlank
    private Long sku;
    @NotBlank
    private String name;
    private List<WarehouseDTO> warehouses;

    public Long getSku() {
        return sku;
    }

    public void setSku(Long sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WarehouseDTO> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(List<WarehouseDTO> warehouses) {
        this.warehouses = warehouses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductRequestDTO that = (ProductRequestDTO) o;
        return Objects.equals(sku, that.sku) && Objects.equals(name, that.name) && Objects.equals(warehouses, that.warehouses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sku, name, warehouses);
    }
}
