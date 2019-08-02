package tech.Demo.Code.action;

import tech.Demo.Code.common.Constants;
import tech.Demo.Code.common.exceptions.InputExcep;
import tech.Demo.Code.common.parsers.Parser;
import tech.Demo.Code.common.parsers.impl.InputParser;
import tech.Demo.Code.models.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;
import static java.util.Objects.nonNull;

public class OrderProc {
    private ProdStore prodStore = ProdStore.getInstance();
    private Parser<Integer> userInputParser = new InputParser();
    private Bakery bakery;

    public OrderProc(Bakery bakery) {
        this.bakery = bakery;
    }

    public String process(String inputString) {
        if(!Constants.EXIT_COMMANDS.contains(inputString.trim())) {
            try {
                Map<String, Integer> userInput = userInputParser.parseList(singletonList(inputString));
                return userInput.entrySet().stream()
                        .map(this::generateOrderBill)
                        .collect(Collectors.joining(Constants.NEWLINE));
            } catch (InputExcep ie) {
                return ie.getMessage();
            }
        } else {
            bakery.close();
            return Constants.BAKERY_CLOSED_TEXT;
        }
    }

    private String generateOrderBill(Entry<String, Integer> userInputEntry) {
        final Product product = prodStore.findProduct(userInputEntry.getKey());

        if(nonNull(product)) {
            final Integer quantity = userInputEntry.getValue();
            return printBill(calculateBill(product, quantity), product, quantity);
        } else {
            throw new InputExcep(Constants.INVALID_PRODUCT_CODE);
        }
    }

    private Map<Integer, Integer> calculateBill(Product product, Integer quantity) {
        Map<Integer, Integer> output = new HashMap<>();

        List<Integer> packSizeList = product.getSortedSupportedPackList();

        int q = quantity;
        int start = 0;
        int packSize = 0;

        while (q > 0 && start < packSizeList.size()) {
            if(packSize > 0) {
                if(packSizeList.indexOf(packSize)+1 == packSizeList.size()) {
                    packSize = packSizeList.get(0);
                }

                if(output.containsKey(packSize)) {
                    q = q + packSize;

                    if (output.get(packSize) > 1) {
                        output.put(packSize, output.get(packSize) - 1);
                    } else {
                        output.remove(packSize);
                    }

                    start = packSizeList.indexOf(packSize) + 1;
                }
            }

            for (int i=start; i<packSizeList.size(); i++) {
                if (q/packSizeList.get(i) > 0) {
                    packSize = packSizeList.get(i);
                    output.put(packSize, q/packSize);
                    q = q % packSize;
                }
            }

            start++;
        }

        if(q > 0) {
            output.clear();
        }

        return output;
    }

    private String printBill(Map<Integer, Integer> output, Product product, Integer quantity) {
        if(output.isEmpty()) {
            return Constants.INVALID_INPUT_PRODUCT_COUNT;
        } else {
            StringBuffer outputBuffer = new StringBuffer();
            float totalOrderValue = 0f;

            for(Integer packSize :  output.keySet()) {
                totalOrderValue += output.get(packSize) * product.getPrice(packSize);

                outputBuffer.append(Constants.NEWLINE + Constants.TABSPACE + output.get(packSize) + Constants.MUL + packSize + Constants.CURRENCY
                        + product.getPrice(packSize));
            }

            return quantity + Constants.SPACE + product + Constants.SPACE + Constants.CURRENCY + totalOrderValue + outputBuffer.toString();
        }
    }
}
