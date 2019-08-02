package tech.Demo.Code.common.parsers.impl;

import tech.Demo.Code.models.ProductPrice;
import tech.Demo.Code.common.parsers.Parser;

import java.util.List;
import java.util.Map;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.groupingBy;

public class ProdPricePars implements Parser<List<ProductPrice>> {

    @Override
    public Map<String, List<ProductPrice>> parseList(List<String> lines) {
        return lines.stream()
                .map(this::getProductQuantityPrice)
                .collect(groupingBy(ProductPrice::getProductCode));
    }

    private ProductPrice getProductQuantityPrice(String rowValue) {
        String[] values = rowValue.split(CSV_SEPARATOR);
        return new ProductPrice(values[0].trim(), parseInt(values[1].trim()), parseFloat(values[2].trim()));
    }
}
