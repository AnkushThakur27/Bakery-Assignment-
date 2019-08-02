package tech.Demo.Code.action;

import tech.Demo.Code.common.Constants;
import tech.Demo.Code.common.exceptions.AppReadeExcep;
import tech.Demo.Code.common.parsers.Parser;
import tech.Demo.Code.common.parsers.impl.ProdParser;
import tech.Demo.Code.common.parsers.impl.ProdPricePars;
import tech.Demo.Code.common.utils.FileUtils;
import tech.Demo.Code.models.Product;
import tech.Demo.Code.models.ProductPrice;

import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static java.util.Objects.isNull;
import static tech.Demo.Code.io.impl.ConsoleWriter.*;

public class ProdStore {
    private static ProdStore prodStore;
    private static Map<String, Product> productMap;

    private ProdStore() {
        loadProductMap();
        loadPriceInProductMap();
    }

    public static ProdStore getInstance() {
        if(isNull(prodStore)) {
            prodStore = new ProdStore();
        }

        return prodStore;
    }

    public Product findProduct(String productCode) {
        return productMap.get(productCode);
    }

    private void loadProductMap() {
        try {
            Parser<Product> productParser = new ProdParser();
            productMap = productParser.parseList(FileUtils.readFileText(Constants.PRODUCT_CSV_FILE));
        } catch (AppReadeExcep rex) {
            write(rex.getMessage());
        }
    }

    private void loadPriceInProductMap() {
        Map<String, List<ProductPrice>> productPriceMap = getProductPriceMap();
        populatePriceInProductMap(productPriceMap);
    }

    private Map<String, List<ProductPrice>> getProductPriceMap() {
        try {
            Parser<List<ProductPrice>> productPriceParser = new ProdPricePars();
            return productPriceParser.parseList(FileUtils.readFileText(Constants.PRODUCT_PRICE_CSV_FILE));
        } catch (AppReadeExcep rex) {
            write(rex.getMessage());
            return emptyMap();
        }
    }

    private void populatePriceInProductMap(Map<String, List<ProductPrice>> productPriceMap) {
        productPriceMap.entrySet().forEach(this::populatePriceInProduct);
    }

    private void populatePriceInProduct(Map.Entry<String, List<ProductPrice>> productPriceEntry) {
        Product product = productMap.get(productPriceEntry.getKey());

        productPriceEntry.getValue().forEach(
                productPrice -> {
                    product.addPricePack(productPrice.getPackSize(), productPrice.getPrice());
                }
        );
    }
}
