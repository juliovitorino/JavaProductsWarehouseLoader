package br.com.juliovitorino.msproductcharger.models;

import javax.persistence.*;
import java.util.*;

/**
 * Model for Product Domain and create annotations for bind with database
 *
 * @author Julio Vitorino
 * @since Apr 19, 2020
 */

@Entity
@Table(name = "product")
public class ProductModel {
    @Id
    @Column(nullable = false)
    private Long sku;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "productModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WarehouseModel> warehouses;

    // BoilerPlate Code were necessary caused by conflict between lombok and Mapper packages :(
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

    public List<WarehouseModel> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(List<WarehouseModel> warehouses) {
        this.warehouses = warehouses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductModel that = (ProductModel) o;
        return Objects.equals(sku, that.sku) && Objects.equals(name, that.name) && Objects.equals(warehouses, that.warehouses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sku, name, warehouses);
    }
}
