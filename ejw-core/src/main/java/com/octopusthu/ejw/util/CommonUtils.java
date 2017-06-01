package com.octopusthu.ejw.util;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author zhangyu octopusthu@gmail.com
 */
public class CommonUtils {
	protected static final Log log = LogFactory.getLog(CommonUtils.class);

	public static String appendUrlParam(String url, String pname, String pvalue) {
		try {
			if (url.indexOf("?") == -1) {
				url += "?" + pname + "=" + pvalue;
			} else {
				url += "&" + pname + "=" + pvalue;
			}
		} catch (Exception e) {
			log.error(e, e);
		}
		return url;
	}

	public static String appendUrlParams(String url, Map<String, String> params) {
		if (params == null) {
			return url;
		}
		try {
			for (Map.Entry<String, String> e : params.entrySet()) {
				String key = e.getKey();
				String value = e.getValue();
				url = appendUrlParam(url, key, value);
			}
		} catch (Exception e) {
			log.error(e, e);
		}
		return url;
	}

	public static String md5(String str) throws NoSuchAlgorithmException {

		return DigestUtils.md5Hex(str);

		// String strDigest = null;
		// byte[] msg = str.getBytes();
		// MessageDigest md = MessageDigest.getInstance("MD5");
		// md.reset();
		// md.update(msg);
		// byte digest[] = md.digest();
		// StringBuffer hexString = new StringBuffer();
		// for (int i = 0; i < digest.length; i++) {
		// hexString.append(Integer.toHexString(0xFF & digest[i]));
		// }
		// strDigest = hexString.toString();
		// return strDigest;
	}
}
