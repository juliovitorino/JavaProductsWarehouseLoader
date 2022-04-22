package br.com.juliovitorino.msproductcharger.dtos;

import br.com.juliovitorino.msproductcharger.enums.WarehouseTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * Pattern DTO (Data Transfer Object) for exchange information between external calls
 *
 * @author Julio Vitorino
 * @since Apr 19, 2020
 */

public class WarehouseDTO {
    @NotBlank
    private String locality;
    @NotBlank
    private Long quantity;
    @NotBlank
    private WarehouseTypeEnum type;

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public WarehouseTypeEnum getType() {
        return type;
    }

    public void setType(WarehouseTypeEnum type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WarehouseDTO that = (WarehouseDTO) o;
        return Objects.equals(locality, that.locality) && Objects.equals(quantity, that.quantity) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(locality, quantity, type);
    }
}
