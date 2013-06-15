/*
 * Copyright 2013 longkai
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

/**
 * Assert uitlities.
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 * @since  2013-3-29
 */
public class Assert {

	/**
	 * assert the object is null with the given exception message.
	 */
	public static void notNull(Object obj, String msg) {
		if (obj == null) {
			throw new IllegalArgumentException(msg);
		}
	}
	
	/**
	 * assert the string object is not null and not empty! 
	 */
	public static void notEmpty(String str, String msg) {
		if (str == null || str.length() == 0) {
			throw new IllegalArgumentException(msg);
		}
	}
	
	/**
	 * assert the string not null and the has the given minimal length.
	 */
	public static void minLength(String str, int length, String msg) {
		notNull(str, msg);
		if (str.length() < length) {
			throw new IllegalArgumentException(msg);
		}
	}
	
	/**
	 * assert the string not null and the has the given maximize length.
	 */
	public static void maxLength(String str, int length, String msg) {
		notNull(str, msg);
		if (str.length() > length) {
			throw new IllegalArgumentException(msg);
		}
	}
	
	/**
	 * assert two object are equals, see {@link java.lang.Object#equals(Object)}
	 */
	public static void equals(Object a, Object b, String msg) {
		if (!a.equals(b)) {
			throw new IllegalArgumentException(msg);
		}
	}
	
}
