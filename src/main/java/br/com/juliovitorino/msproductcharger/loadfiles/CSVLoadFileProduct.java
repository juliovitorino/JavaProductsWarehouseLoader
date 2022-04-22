package br.com.juliovitorino.msproductcharger.loadfiles;

import br.com.juliovitorino.msproductcharger.enums.WarehouseTypeEnum;
import br.com.juliovitorino.msproductcharger.models.ProductModel;
import br.com.juliovitorino.msproductcharger.models.WarehouseModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete Factory for Product Loader File in CSV format.
 *
 * @author Julio Vitorino
 * @since Apr 21, 2022
 */
public class CSVLoadFileProduct extends AbstractGatewayLoadFilesProduct {

    public CSVLoadFileProduct(MultipartFile file) throws IOException {
        this.file = file.getInputStream();
    }

    @Override
    public List<ProductModel> execute() {
        String line = "";
        int count = 0;
        List<ProductModel> pmlst = new ArrayList<ProductModel>();

        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(this.file, "UTF-8"));
            while((line = fileReader.readLine()) != null){
                //System.out.println(line);
                String[] values = line.split(";");
                //System.out.println(values[0]);

                // Only after first line are valid, first line is the header columns
                if(count > 0){
                    // add fixed elements to the list
                    ProductModel pm = new ProductModel();
                    pm.setSku(Long.valueOf(values[AbstractGatewayLoadFilesProduct.COL_SKU]));
                    pm.setName(values[AbstractGatewayLoadFilesProduct.COL_NAME]);

                    // Load the warehouses independent numbers of terms
                    List<WarehouseModel> whmlst = new ArrayList<WarehouseModel>();
                    for(int idx = AbstractGatewayLoadFilesProduct.COL_LOCALITY; idx < values.length; idx+=3){
                        WarehouseModel wm = new WarehouseModel();
                        wm.setProductModel(pm);
                        wm.setLocality(values[idx]);
                        wm.setQuantity(Long.parseLong(values[idx+1]));
                        wm.setType(WarehouseTypeEnum.UNDEFINED);

                        // Adjust type for warehouse if necessary
                        if (values[idx+2].equals("ECOMMERCE")) {
                            wm.setType(WarehouseTypeEnum.ECOMMERCE);
                        } else if(values[idx+2].equals("PHYSICAL_STORE")) {
                            wm.setType(WarehouseTypeEnum.PHYSICAL_STORE);
                        }
                        whmlst.add(wm);
                    }
                    pm.setWarehouses(whmlst);
                    pmlst.add(pm);
                } else {
                    count++;
                }

            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pmlst;
    }
}
