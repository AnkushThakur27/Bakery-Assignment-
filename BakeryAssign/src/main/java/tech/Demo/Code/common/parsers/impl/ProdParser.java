package tech.Demo.Code.common.parsers.impl;

import tech.Demo.Code.models.Product;
import tech.Demo.Code.common.parsers.Parser;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class ProdParser implements Parser<Product> {
    @Override
    public Map<String, Product> parseList(List<String> lines) {
        return lines.stream()
                .map(this::getProduct)
                .collect(toMap(Product::getCode, product -> product));
    }

    private Product getProduct(String row) {
        String[] values = row.split(CSV_SEPARATOR);
        return new Product(values[0].trim(), values[1].trim());
    }
}
