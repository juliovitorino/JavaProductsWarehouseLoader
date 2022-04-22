package br.com.juliovitorino.msproductcharger.services;

import br.com.juliovitorino.msproductcharger.models.WarehouseModel;
import br.com.juliovitorino.msproductcharger.repositories.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService {

    @Autowired
    WarehouseRepository warehouseRepository;

    public WarehouseModel addWarehouse(WarehouseModel warehouseModel)
    {
        try {
            warehouseModel = warehouseRepository.save(warehouseModel);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return warehouseModel;
    }
}
