package test.ank.testCase.common.parsers.impl;

import org.junit.Test;
import tech.Demo.Code.common.parsers.Parser;
import tech.Demo.Code.common.parsers.impl.ProdParser;
import tech.Demo.Code.common.parsers.impl.ProdPricePars;
import tech.Demo.Code.common.parsers.impl.InputParser;
import tech.Demo.Code.models.Product;
import tech.Demo.Code.models.ProductPrice;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static test.ank.testCase.common.utils.TestData.PRODUCT_LIST;
import static test.ank.testCase.common.utils.TestData.PRODUCT_PRICE_LIST;
import static test.ank.testCase.common.utils.TestData.USER_INPUT_LIST;

public class ParsersTest {
    @Test
    public void testProductParser() {
        Parser<Product> productParser = new ProdParser();
        Map<String, Product> productMap = productParser.parseList(PRODUCT_LIST);
        assertNotNull(productMap);
        assertEquals(3, productMap.entrySet().size());
        assertEquals("Vegemite Scroll", productMap.get("VS5").getName());
        assertEquals("Blueberry Muffin", productMap.get("MB11").getName());
        assertEquals("Croissant", productMap.get("CF").getName());
    }

    @Test
    public void testProductPriceParser() {
        Parser<List<ProductPrice>> productPriceParser = new ProdPricePars();
        Map<String, List<ProductPrice>> productPriceMap = productPriceParser.parseList(PRODUCT_PRICE_LIST);
        assertNotNull(productPriceMap);
        assertEquals(3, productPriceMap.entrySet().size());
        assertEquals(1, productPriceMap.get("VS5").size());
        assertEquals(2, productPriceMap.get("MB11").size());
        assertEquals(3, productPriceMap.get("CF").size());
    }

    @Test
    public void testParseGameCharacter() {
        Parser<Integer> userInputParser = new InputParser();
        Map<String, Integer> userInputMap = userInputParser.parseList(USER_INPUT_LIST);
        assertNotNull(userInputMap);
        assertEquals(3, userInputMap.entrySet().size());
        assertEquals(15, userInputMap.get("VS5").intValue());
        assertEquals(13, userInputMap.get("MB11").intValue());
        assertEquals(27, userInputMap.get("CF").intValue());
    }
}
