package br.com.juliovitorino.msproductcharger.dtos;

import lombok.Data;

import java.util.Objects;

/**
 * Pattern DTO (Data Transfer Object) for exchange information between external calls
 *
 * @author Julio Vitorino
 * @since Apr 19, 2020
 */

public class ProductResponseDTO  {
    private Long sku;
    private String name;
    private InventoryDTO inventoryDTO;
    private boolean isMarketable;

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

    public InventoryDTO getInventoryDTO() {
        return inventoryDTO;
    }

    public void setInventoryDTO(InventoryDTO inventoryDTO) {
        this.inventoryDTO = inventoryDTO;
    }

    public boolean isMarketable() {
        return isMarketable;
    }

    public void setMarketable(boolean marketable) {
        isMarketable = marketable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductResponseDTO that = (ProductResponseDTO) o;
        return isMarketable == that.isMarketable && Objects.equals(sku, that.sku) && Objects.equals(name, that.name) && Objects.equals(inventoryDTO, that.inventoryDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sku, name, inventoryDTO, isMarketable);
    }
}
