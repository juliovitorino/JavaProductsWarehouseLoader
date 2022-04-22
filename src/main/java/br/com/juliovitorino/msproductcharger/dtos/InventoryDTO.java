package br.com.juliovitorino.msproductcharger.dtos;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Pattern DTO (Data Transfer Object) for exchange information between external calls
 *
 * @author Julio Vitorino
 * @since Apr 19, 2020
 */


public class InventoryDTO {
    private int quantity;
    @NotBlank
    private Set<WarehouseDTO> warehouseDTOList;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Set<WarehouseDTO> getWarehouseDTOList() {
        return warehouseDTOList;
    }

    public void setWarehouseDTOList(Set<WarehouseDTO> warehouseDTOList) {
        this.warehouseDTOList = warehouseDTOList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryDTO that = (InventoryDTO) o;
        return quantity == that.quantity && Objects.equals(warehouseDTOList, that.warehouseDTOList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, warehouseDTOList);
    }
}
