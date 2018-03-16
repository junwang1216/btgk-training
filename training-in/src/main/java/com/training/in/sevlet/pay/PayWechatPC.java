package com.training.in.sevlet.pay;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.training.core.common.util.PayCommonUtil;
import com.training.core.common.util.PayConfigUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * 生成二维码，手机扫码支付
 * Created by wangjun on 2018/3/14.
 */
@WebServlet("/pay/wechat/pc")
public class PayWechatPC extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(PayWechatPC.class);

    public static int defaultWidthAndHeight = 200;

    public PayWechatPC() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nonce_str = PayCommonUtil.getNonce_str();
        long time_stamp = System.currentTimeMillis() / 1000;
        String key = PayConfigUtil.API_KEY; // key

        logger.info(request.getQueryString());
        String product_id = "hd_goodsssss_10";

        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
        packageParams.put("appid", PayConfigUtil.APP_ID);
        packageParams.put("mch_id", PayConfigUtil.MCH_ID);
        packageParams.put("time_stamp", String.valueOf(time_stamp));
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("product_id", product_id);
        String sign = PayCommonUtil.createSign("UTF-8", packageParams,key);//MD5哈希
        packageParams.put("sign", sign);

        //生成参数
        String str = ToUrlParams(packageParams);
        String payurl = "weixin://wxpay/bizpayurl?" + str;

        logger.info("payurl:" + payurl);

        //生成二维码
        Map<EncodeHintType, Object>  hints=new HashMap<EncodeHintType, Object>();
        // 指定纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        // 指定编码格式
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(payurl,BarcodeFormat.QR_CODE, defaultWidthAndHeight, defaultWidthAndHeight, hints);
            OutputStream out = response.getOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "png", out);//输出二维码
            out.flush();
            out.close();
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public String ToUrlParams(SortedMap<Object, Object> packageParams){
        //实际可以不排序
        StringBuffer sb = new StringBuffer();
        Set es = packageParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v)) {
                sb.append(k + "=" + v + "&");
            }
        }

        sb.deleteCharAt(sb.length()-1);//删掉最后一个&
        return sb.toString();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
