package groovy.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by codingbaby on 2017/5/12.
 */
public class DataUtils {

    private static ThreadLocal<Map<String, SimpleDateFormat>> dateFormatMap = new ThreadLocal<Map<String, SimpleDateFormat>>() {
        @Override
        public Map<String, SimpleDateFormat> initialValue() {
            return new HashMap<String, SimpleDateFormat>();
        }
    };


    private static SimpleDateFormat getSimpleDateFormat(String format) {
        Map<String, SimpleDateFormat> map = dateFormatMap.get();
        SimpleDateFormat simpleDateFormat = map.get(format);
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(format);
            map.put(format, simpleDateFormat);
        }
        return simpleDateFormat;
    }

    public static String format(Date date, String format) {
        if (date == null)
            return "";
        return getSimpleDateFormat(format).format(date);
    }

}
