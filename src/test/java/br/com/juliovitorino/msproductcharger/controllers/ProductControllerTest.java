package br.com.juliovitorino.msproductcharger.controllers;

import br.com.juliovitorino.msproductcharger.dtos.ProductRequestDTO;
import br.com.juliovitorino.msproductcharger.dtos.ProductResponseDTO;
import br.com.juliovitorino.msproductcharger.dtos.WarehouseDTO;
import br.com.juliovitorino.msproductcharger.enums.WarehouseTypeEnum;
import br.com.juliovitorino.msproductcharger.models.ProductModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductControllerTest {

    final static String URL_SERVER = "http://localhost:";
    final static String MAPPING_REST_API_ADD_PRODUCT = "/add-product";
    final static String MAPPING_REST_API_SEARCH_PRODUCT = "/search-product";
    final static String MAPPING_REST_API_DELETE_PRODUCT = "/delete-product";
    final static String MAPPING_REST_API_UPDATE_PRODUCT = "/update-product";

    @Autowired private ObjectMapper mapper;
    @Autowired private TestRestTemplate restTemplate;
    @LocalServerPort private Integer port;

    private Long skuRandom;

    @Test
    void testAddProductAndWarehouse() throws JsonProcessingException {
        this.skuRandom = this.getSkuRandom();
        ProductRequestDTO pm = getProductRequestDTO(skuRandom);

        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        final HttpEntity<String> entity = new HttpEntity<String>(mapper.writeValueAsString(pm), headers);

        final ResponseEntity<String> responseEntity = this.restTemplate.exchange(URL_SERVER + port + MAPPING_REST_API_ADD_PRODUCT, HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void testUpdateProduct() throws JsonProcessingException, JSONException {
        this.testAddProductAndWarehouse();

        HttpEntity<String> entity = new HttpEntity<String>(null, null);
        ResponseEntity<String> responseEntity = this.restTemplate.exchange(URL_SERVER + port + MAPPING_REST_API_SEARCH_PRODUCT +  "?sku=" + this.skuRandom
                , HttpMethod.GET
                , entity
                , String.class);
        JSONObject jsonObject = new JSONObject(responseEntity.getBody());
        ProductResponseDTO pm = mapper.readValue( jsonObject.toString(), ProductResponseDTO.class);

        pm.setName("Donec aliquam nibh a sem dictum, et efficitur ex molestie");

        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        final HttpEntity<String> entityreq = new HttpEntity<String>(mapper.writeValueAsString((ProductResponseDTO) pm), headers);

        final ResponseEntity<String> responseEntityreq = this.restTemplate.exchange(URL_SERVER + port + MAPPING_REST_API_UPDATE_PRODUCT, HttpMethod.POST, entityreq, String.class);
        assertEquals(HttpStatus.OK, responseEntityreq.getStatusCode());
    }


    @Test
    void testSearchProductBySKU() throws JSONException, JsonProcessingException {

        this.testAddProductAndWarehouse();
        final HttpEntity<String> entity = new HttpEntity<String>(null, null);
        final ResponseEntity<String> responseEntity = this.restTemplate.exchange(URL_SERVER + port + MAPPING_REST_API_SEARCH_PRODUCT +  "?sku=" + this.skuRandom
                , HttpMethod.GET
                , entity
                , String.class);
        final JSONObject jsonObject = new JSONObject(responseEntity.getBody());
        final ProductResponseDTO pm = mapper.readValue( jsonObject.toString(), ProductResponseDTO.class);
        assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
        assertNotNull(pm);
    }

    @Test
    void testDeleteProductBySKU() throws JsonProcessingException, JSONException {

        this.testAddProductAndWarehouse();
        final HttpEntity<String> entity = new HttpEntity<String>(null, null);
        final ResponseEntity<String> responseEntity = this.restTemplate.exchange(URL_SERVER + port + MAPPING_REST_API_DELETE_PRODUCT +  "?sku=" + this.skuRandom
                , HttpMethod.GET
                , entity
                , String.class);
        final JSONObject jsonObject = new JSONObject(responseEntity.getBody());
        final ProductResponseDTO pm = mapper.readValue( jsonObject.toString(), ProductResponseDTO.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    //=========================
    // SUPPORT PRIVATE METHODS
    // ========================
    private ProductRequestDTO getProductRequestDTO(Long sku) {
        // create a new ProductModel to add to warehouse
        ProductRequestDTO pm = new ProductRequestDTO();
        List<WarehouseDTO> whlst = new ArrayList<WarehouseDTO>();
        pm.setSku(sku);
        pm.setName("Lorem Ipsum 10mg");

        WarehouseDTO wh1 = new WarehouseDTO();
        wh1.setLocality("SP");
        wh1.setQuantity(20L);
        wh1.setType(WarehouseTypeEnum.ECOMMERCE);

        whlst.add(wh1);
        pm.setWarehouses(whlst);
        return pm;
    }

    private Long getSkuRandom() {
        Random rnd = new Random();
        int number = rnd.nextInt() * 10000;
        if (number < 0) number = number * -1;
        return Long.valueOf(number);
    }


}