package br.com.juliovitorino.msproductcharger.loadfiles;

import br.com.juliovitorino.msproductcharger.enums.LoadFilesProductEnum;
import br.com.juliovitorino.msproductcharger.models.ProductModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Abstract Factory for Product Loader File. Any class inheritance should be implement your load process.
 *
 * By default, this class load file CSV and/or XLSX product list.
 *
 * @author Julio Vitorino
 * @since Apr 21, 2022
 */


public abstract class AbstractGatewayLoadFilesProduct {

    public final static int COL_SKU = 0;
    public final static int COL_NAME = 1;
    public final static int COL_QUANTITY = 2;
    public final static int COL_WAREHOUSES = 3;
    public final static int COL_LOCALITY = 4;

    public final static String FT_TXT_CSV = "text/csv";
    public final static String FT_APP_XLS = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    protected InputStream file;

    public static AbstractGatewayLoadFilesProduct getInstance(String filetype, MultipartFile file) throws IOException {
        return switch (filetype) {
            case FT_TXT_CSV -> new CSVLoadFileProduct(file);
            case FT_APP_XLS -> new XLSLoadFileProduct(file);
            default -> null;
        };

    }

    // Methods that will be implemented for the children classes
    public abstract List<ProductModel> execute();
}
