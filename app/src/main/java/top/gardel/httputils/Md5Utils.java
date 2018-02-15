package top.gardel.httputils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;

public class Md5Utils {
	//字符串MD5算法
	public static String Md5(String input) {
		try {
			// 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
			MessageDigest messageDigest =MessageDigest.getInstance("MD5");
			byte[] inputByteArray = input.getBytes();
			messageDigest.update(inputByteArray);
			byte[] resultByteArray = messageDigest.digest();
			byte[] byteArray=resultByteArray;
			char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'a','b','c','d','e','f' };
			char[] resultCharArray =new char[byteArray.length * 2];
			int index = 0;
			for (byte b : byteArray) {
				resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
				resultCharArray[index++] = hexDigits[b & 0xf];
			}
			return new String(resultCharArray);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
	//文件MD5算法
	public static String Md5(File file) {
		if (file == null) return null;
        if (!file.isFile()) {
			return "";
		} else {
			MessageDigest digest = null;
			FileInputStream in = null;
			byte buffer[] = new byte[1024];
			int len;
			try {
				digest = MessageDigest.getInstance("MD5");
				in = new FileInputStream(file);
				while ((len = in.read(buffer, 0, 1024)) != -1) {
					digest.update(buffer, 0, len);
				}
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			BigInteger bigint=new BigInteger(1, digest.digest());
			return bigint.toString(16);
		}
	}
}
