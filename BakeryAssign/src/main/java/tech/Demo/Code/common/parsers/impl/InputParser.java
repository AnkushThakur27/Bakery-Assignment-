package tech.Demo.Code.common.parsers.impl;

import tech.Demo.Code.common.exceptions.InputExcep;
import tech.Demo.Code.common.parsers.Parser;

import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toMap;
import static tech.Demo.Code.common.Constants.INVALID_USER_INPUT;

public class InputParser implements Parser<Integer> {

    @Override
    public Map<String, Integer> parseList(List<String> lines) {
        try {
            return lines.stream()
                    .map(line -> line.split(USER_INPUT_SEPARATOR))
                    .collect(toMap(o -> o[1].trim(), o -> parseInt(o[0].trim())));
        } catch (Exception ex) {
            throw new InputExcep(INVALID_USER_INPUT);
        }
    }
}
