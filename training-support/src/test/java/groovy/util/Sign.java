package groovy.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by codingbaby on 2017/5/12.
 */
public class Sign {

    public static String getSign(String partner, String partnerKey, long timestamp, Map<String, Object> params) {
        List<String> paramList = params.entrySet().stream().filter(stringObjectEntry -> stringObjectEntry.getValue() != null && StringUtils.isNotEmpty(stringObjectEntry.getValue().toString())).sorted(Comparator.comparing(Map.Entry::getKey)).map(e -> e.getKey() + "=" + e.getValue()).collect(Collectors.toList());
        String result = StringUtils.join(paramList, "&");
        result += "&key=" + partnerKey;
        result += "&partner=" + partner;
        result += "&timestamp=" + timestamp;
        return MD5Util.md5(result).toUpperCase();
    }
}
