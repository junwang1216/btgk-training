package groovy.util;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * Created by zhaotao on 2016/1/20.
 */
public class GenerateCellPhone {

    public static String generate() {
        return "19" + RandomStringUtils.randomNumeric(9);
    }

}
