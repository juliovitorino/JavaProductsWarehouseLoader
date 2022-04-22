package br.com.juliovitorino.msproductcharger.loadfiles;

import br.com.juliovitorino.msproductcharger.enums.WarehouseTypeEnum;
import br.com.juliovitorino.msproductcharger.models.ProductModel;
import br.com.juliovitorino.msproductcharger.models.WarehouseModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Concrete Factory for Product Loader File in XLS format.
 *
 * @author Julio Vitorino
 * @since Apr 21, 2022
 */
public class XLSLoadFileProduct extends AbstractGatewayLoadFilesProduct {

    public XLSLoadFileProduct(MultipartFile file) throws IOException {
        this.file = file.getInputStream();
    }

    @Override
    public List<ProductModel> execute() {

        List<ProductModel> pmlst = new ArrayList<ProductModel>();

        try {
            // Get the workbook reference
            Workbook workbook = new XSSFWorkbook(this.file);
            DataFormatter dataFormatter = new DataFormatter();

            // Get the sheets reference to iterate it.
            Iterator<Sheet> sheets = workbook.sheetIterator();
            while(sheets.hasNext()) {
                Sheet sh = sheets.next();
                System.out.println("Sheet name is " + sh.getSheetName());

                int count = 0;

                // Get the Row iterator for the single sheet
                Iterator<Row> rowIterator = sh.iterator();
                while(rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    // Ignore header line
                    if(count > 0){

                        // Get the Cell iterator for iterate it in a single row
                        Iterator<Cell> cellIterator = row.iterator();
                        ArrayList<String> cells = new ArrayList<String>();
                        while(cellIterator.hasNext()) {
                            Cell cell = cellIterator.next();
                            String cellValue = dataFormatter.formatCellValue(cell);
                            cells.add(cellValue);
                        }

                        Object values[] = cells.toArray();

                        // add fixed elements to the list
                        ProductModel pm = new ProductModel();
                        pm.setSku(Long.valueOf((String) values[AbstractGatewayLoadFilesProduct.COL_SKU]));
                        pm.setName((String) values[AbstractGatewayLoadFilesProduct.COL_NAME]);

                        // Load the warehouses independent numbers of terms int the row
                        List<WarehouseModel> whmlst = new ArrayList<WarehouseModel>();
                        for(int idx = AbstractGatewayLoadFilesProduct.COL_LOCALITY-1; idx < values.length-1; idx+=3){
                            WarehouseModel wm = new WarehouseModel();
                            wm.setProductModel(pm);
                            wm.setLocality((String) values[idx]);
                            wm.setQuantity(Long.parseLong((String) values[idx+1]));
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
            }
            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pmlst;
    }
}
