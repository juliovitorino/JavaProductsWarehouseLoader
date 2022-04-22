package br.com.juliovitorino.msproductcharger.controllers;

import br.com.juliovitorino.msproductcharger.dtos.InventoryDTO;
import br.com.juliovitorino.msproductcharger.dtos.ProductRequestDTO;
import br.com.juliovitorino.msproductcharger.dtos.ProductResponseDTO;
import br.com.juliovitorino.msproductcharger.dtos.WarehouseDTO;
import br.com.juliovitorino.msproductcharger.exceptions.SkuExistenteProductException;
import br.com.juliovitorino.msproductcharger.mapper.ProductMapper;
import br.com.juliovitorino.msproductcharger.models.ProductModel;
import br.com.juliovitorino.msproductcharger.models.WarehouseModel;
import br.com.juliovitorino.msproductcharger.services.ProductService;
import br.com.juliovitorino.msproductcharger.services.WarehouseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;

/**
 * Create endpoints for Rest API to perform a CRUD process.
 *
 * @author Julio Vitorino
 * @since Apr 19, 2022
 */

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    WarehouseService warehouseService;

    /**
     * Add product info. Validate information by annotation @Valid
     *
     * @param  productDTO  a Structured JSON Object with product info
     * @return      a JSON structure for the invoker
     */
    @PostMapping("/add-product")
    public ResponseEntity<ProductResponseDTO> addProduct(@RequestBody ProductRequestDTO productDTO)
        throws SkuExistenteProductException {

        // Verify sku exists into the database. Locate the product info by sku field
        productService.isProductModelExists(productService.findBySku(productDTO.getSku()));

        // Create a Model to be populated and copy essential attributes from DTO to Model
        final ProductModel productModel = ProductMapper.INSTANCE.requestDtoModel(productDTO);
        for (WarehouseModel warehouseItem: productModel.getWarehouses()) {
            warehouseItem.setProductModel(productModel);
        }

        // calls the ProductService to apply business rules after attributes has been validated
        productService.addProduct(productModel);

        // calculate warehouse position
        ProductResponseDTO productResponseDTO = this.calculateInventory(productModel);

        // return Model
        return new ResponseEntity<>(productResponseDTO, HttpStatus.CREATED);
    }

    /**
     * Search product info using sku field.
     *
     * @param  sku  sku product code
     * @return      a JSON structure for the invoker
     */
    @GetMapping("/search-product")
    public ResponseEntity<ProductResponseDTO> searchProduct(@RequestParam Long sku) {

        ProductModel productModel =  productService.findBySku(sku);

        // calculate warehouse position
        ProductResponseDTO productResponseDTO = this.calculateInventory(productModel);

        // return DTO
        return new ResponseEntity<ProductResponseDTO>(productResponseDTO, HttpStatus.FOUND);
    }

    /**
     * Delete specified product info using sku field.
     *
     * @param  sku sku product code
     * @return      a JSON structure for the invoker
     */
    @GetMapping("/delete-product")
    public ResponseEntity<ProductResponseDTO> deleteProduct(@RequestParam Long sku) {

        productService.deleteBySku(sku);
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setSku(sku);

        // return DTO
        return new ResponseEntity<ProductResponseDTO>(productResponseDTO, HttpStatus.OK);
    }

    /**
     * Update specified product info using sku field.
     *
     * @param  productDTO sku product code
     * @return      a JSON structure for the invoker
     */
    @PostMapping("/update-product")
    public ResponseEntity<ProductResponseDTO> updateProduct(@RequestBody ProductRequestDTO productDTO) {

       // Locate the product info by sku field
       ProductModel productModel = productService.findBySku(productDTO.getSku());
       if(productModel == null) return new ResponseEntity<ProductResponseDTO>(new ProductResponseDTO(), HttpStatus.OK);

       // Change the attributes and then save
        productModel.setName(productDTO.getName());
        productService.updateProduct(productModel);
        productModel = productService.findBySku(productDTO.getSku());

        // calculate warehouse position
        ProductResponseDTO productResponseDTO = this.calculateInventory(productModel);

        // return DTO
        return new ResponseEntity<ProductResponseDTO>(productResponseDTO, HttpStatus.OK);
    }

    /**
     * List all product info.
     *
     * @return a JSON structure for the invoker with Product list
     */
    @GetMapping("/list-product")
    public ResponseEntity<List<ProductModel>> listAll() {
        return new ResponseEntity<>(productService.listAll(), HttpStatus.OK);
    }

    /**
     * Upload file to load Products and warehouses into the database
     *
     * @param file A predefined file format CSV or XLS
     * @return a JSON structure for the invoker with Product list
     */
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws SkuExistenteProductException {
        String message = "";
        List<ProductModel> pmlst;

        try {
            pmlst = productService.loaderFile(file);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }

        // add each entry to the database
        for(ProductModel pmItem : pmlst){
            // Verify sku exists into the database. Locate the product info by sku field
            productService.isProductModelExists(productService.findBySku(pmItem.getSku()));
            productService.addProduct(pmItem);
        }

        message = "Uploaded the file successfully: " + file.getOriginalFilename();
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    /***** SUPPORT PRIVATE METHODS ****/
    /**
     * Calculate the inventory for productModel
     * @param productModel populated
     * @return a calculated ProductResponseDTO Object
     */
    private ProductResponseDTO calculateInventory(ProductModel productModel) {

        // Create an instance for response
        ProductResponseDTO response = new ProductResponseDTO();
        if(productModel == null) return response;

        // create an instance for inventory and initiate it.
        InventoryDTO inventoryDTO = new InventoryDTO();

        // create warehouse list for compute and add to response
        HashSet<WarehouseDTO> hsWarehouse = new HashSet<>();
        int count = 0;
        for (WarehouseModel whmItem: productModel.getWarehouses()) {
            count += whmItem.getQuantity();
            WarehouseDTO warehouseDTO = new WarehouseDTO();
            warehouseDTO.setQuantity(whmItem.getQuantity());
            warehouseDTO.setLocality(whmItem.getLocality());
            warehouseDTO.setType(whmItem.getType());
            hsWarehouse.add(warehouseDTO);
        }
        inventoryDTO.setQuantity(count);
        inventoryDTO.setWarehouseDTOList(hsWarehouse);

        // populate the response
        response.setSku(productModel.getSku());
        response.setName(productModel.getName());
        response.setMarketable(count > 0);
        response.setInventoryDTO(inventoryDTO);

        return response;

    }
}
