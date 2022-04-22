package br.com.juliovitorino.msproductcharger.models;

import br.com.juliovitorino.msproductcharger.enums.WarehouseTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

/**
 * Model for Warehouse Domain and create annotations for bind with database
 *
 * @author Julio Vitorino
 * @since Apr 19, 2020
 */

@Entity
@Table(name = "warehouse")
public class WarehouseModel {

    @Id
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "UUID")
    @Type(type = "uuid-binary")
    private UUID warehouse_id;

    @Column(nullable = false)
    private String locality;

    @Column(nullable = false)
    private long quantity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WarehouseTypeEnum type;

    @ManyToOne
    @JoinColumn(name = "sku")
    @JsonIgnore
    private ProductModel productModel;

    public UUID getWarehouse_id() {
        return warehouse_id;
    }

    public void setWarehouse_id(UUID warehouse_id) {
        this.warehouse_id = warehouse_id;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public WarehouseTypeEnum getType() {
        return type;
    }

    public void setType(WarehouseTypeEnum type) {
        this.type = type;
    }

    public ProductModel getProductModel() {
        return productModel;
    }

    public void setProductModel(ProductModel productModel) {
        this.productModel = productModel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WarehouseModel that = (WarehouseModel) o;
        return quantity == that.quantity && Objects.equals(warehouse_id, that.warehouse_id) && Objects.equals(locality, that.locality) && type == that.type && Objects.equals(productModel, that.productModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(warehouse_id, locality, quantity, type, productModel);
    }
}
