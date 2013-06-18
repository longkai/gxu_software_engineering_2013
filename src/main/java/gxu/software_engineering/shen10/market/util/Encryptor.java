/*
 * Copyright 2013 龙凯
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package gxu.software_engineering.shen10.market.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 密码加密工具。
 * 
 * @author longkai
 * @email  im.longkai@gmail.com
 * @since  2013-3-28
 */
public class Encryptor {

	public synchronized static String MD5(String src) {  
        MessageDigest md = null;  
  
        try {  
            md = MessageDigest.getInstance("MD5");  
            md.reset();  
            md.update(src.getBytes("UTF-8"));  
        } catch (NoSuchAlgorithmException e) {  
        	throw new RuntimeException("MD5加密时出错！", e);
        } catch (UnsupportedEncodingException e) {  
        	throw new RuntimeException("MD5加密时出错！", e);
        }  
  
        byte[] byteArray = md.digest();  
        StringBuffer md5StrBuff = new StringBuffer();  
  
        for (int i = 0; i < byteArray.length; i++) {              
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)  
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));  
            else  
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));  
        }  
  
        return md5StrBuff.toString();  
    }  
	
}