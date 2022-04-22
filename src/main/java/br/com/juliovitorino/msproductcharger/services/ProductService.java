package br.com.juliovitorino.msproductcharger.services;

import br.com.juliovitorino.msproductcharger.dtos.WarehouseDTO;
import br.com.juliovitorino.msproductcharger.exceptions.SkuExistenteProductException;
import br.com.juliovitorino.msproductcharger.loadfiles.AbstractGatewayLoadFilesProduct;
import br.com.juliovitorino.msproductcharger.models.ProductModel;
import br.com.juliovitorino.msproductcharger.models.WarehouseModel;
import br.com.juliovitorino.msproductcharger.repositories.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Create a service to resolve business process that will be called by RestApi or future consumer
 * subscribed on a messaging service as a RabbitMQ, Kafka or anything else.
 *
 * @author Julio Vitorino
 * @since Apr 19, 2022
 */

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    /**
     * addProduct - Returns ProductDTO object that can then be used for any application.
     *
     * @param  productModel  a ProductDTO to be used in the process
     * @return  ProductModel object added in repository
     */
    public ProductModel addProduct(ProductModel productModel) {
        return productRepository.save(productModel);
    }

    /**
     * listAll - Returns a list of ProductModel but DO NOT calculated the inventory.
     *
     * @return  list of ProductModel object added in repository
     */
    public List<ProductModel> listAll()
    {
        return productRepository.findAll();
    }

    /**
     * findBySku - Find a Product using the SKU. Calculate de inventory of product in all warehouses attached it.
     *
     * @param sku The key to perform a search into database
     * @return  ProductModel object
     */
    public ProductModel findBySku(Long sku) {
        return productRepository.findBySku(sku);
    }

    /**
     * deleteBySku - Find a Product using the SKU. Calculate de inventory of product in all warehouses attached it.
     *
     * @param sku The key to perform a delete into database
     * @return  ProductModel object
     */
    public void deleteBySku(long sku){
        productRepository.deleteById(sku);
    }

    /**
     * updateProduct - Find a Product using the SKU. Calculate de inventory of product in all warehouses attached it.
     *
     * @param productModel The object to perform an update operation into database
     * @return  ProductModel object
     */
    public ProductModel updateProduct(ProductModel productModel) {
        return productRepository.save(productModel);
    }

    /**
     * loaderFile - it calls the factory to perform load product into database
     *
     * @param file file to load. It can be a CSV, XLS or any kind of them, but need concrete class implementation
     * @return  List List de ProductModel processed from file
     */
    public List<ProductModel> loaderFile(MultipartFile file) throws IOException {
        return AbstractGatewayLoadFilesProduct.getInstance(file.getContentType(), file).execute();
    }

    public void isProductModelExists(ProductModel pmItem) throws SkuExistenteProductException {
        // Verify sku exists into the database. Locate the product info by sku field
        if(pmItem != null) {
            throw new SkuExistenteProductException("SKU " + pmItem.getSku() + " exists, VERIFIY and try new one.");
        }
    }
}
