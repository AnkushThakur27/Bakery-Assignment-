package test.ank.testCase.common.utils;

import org.junit.Assert;
import org.junit.Test;
import tech.Demo.Code.common.exceptions.AppReadeExcep;
import tech.Demo.Code.common.utils.FileUtils;

import java.util.List;

import static tech.Demo.Code.common.Constants.PRODUCT_CSV_FILE;

public class FileUtilsTest {
    @Test
    public void testReadFileValid() throws Exception {
        List<String> strings = FileUtils.readFileText(PRODUCT_CSV_FILE);
        Assert.assertNotNull(strings);
        Assert.assertEquals(3, strings.size());
    }

    @Test(expected = AppReadeExcep.class)
    public void testReadFileInvalid() throws Exception {
        FileUtils.readFileText(TestData.INVALID_CSV_FILE);
    }
}
