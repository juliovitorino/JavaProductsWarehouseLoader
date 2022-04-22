package br.com.juliovitorino.msproductcharger.repositories;

import br.com.juliovitorino.msproductcharger.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    ProductModel findBySku(Long sku);
    ProductModel findByName(String name);
    //long deleteBySku(Long sku);
}
