package groovy.util;

import org.apache.commons.codec.binary.Hex;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by wentao on 2015/3/5.
 */
public class MD5Util {

    public static String md5(String data){
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 algorithm available!");
        }

        return new String(Hex.encodeHex(digest.digest(data.getBytes())));
    }

    public static String md5(File file){
        FileInputStream in =null;
        try {
        in = new FileInputStream(file);
        FileChannel ch = in.getChannel();
        MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(byteBuffer);
        return new String(Hex.encodeHex(messageDigest.digest()));
        } catch (Exception e) {
        } finally {
            if(null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return "";
    }


}
