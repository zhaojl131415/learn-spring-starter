package com.zhao.innerClass;

public class X {
	// 静态常量
	private final static String a = "a";
	// 普通常量
	private final String b = "b";
	// 静态变量
	private static String c = "c";
	// 普通变量
	private String d = "d";

	public class Z {
		// 普通内部类允许声明静态常量
		private final static String za = "a";
		// 普通内部类允许声明普通常量
		private final String zb = "b";
		// 普通内部类不允许声明静态变量
		private static String zc = "c";
		// 普通内部类允许声明普通变量
		private String zd = "d";

		// 普通内部类可以声明非静态方法
		private void hello() {
			// 普通方法不允许声明静态常量/变量
			final static String zha = "a";
			static String zhc = "c";
			// 普通方法允许声明普通常量/变量
			final String zhb = "b";
			String zhd = "d";

			// 普通内部类允许访问外部(静态/非静态/私有/公有)的常量/变量
			System.out.println(a);
			System.out.println(b);
			System.out.println(c);
			System.out.println(d);
			System.out.println(za);
			System.out.println(zb);
			System.out.println(zc);
			System.out.println(zd);
		}

		// 普通内部类不能声明静态方法
		private static void world() {
		}

	}

	public static class W {
		// 静态内部类允许声明静态常量
		private final static String wa = "a";
		// 静态内部类允许声明普通常量
		private final String wb = "b";
		// 静态内部类允许声明静态变量
		private static String wc = "c";
		// 静态内部类允许声明普通变量
		private String wd = "d";

		// 静态内部类可以声明非静态方法
		private void hello() {
			// 普通方法不允许声明静态常量/变量
			final static String wha = "a";
			static String whc = "c";
			// 普通方法允许声明普通常量/变量
			final String whb = "b";
			String whd = "d";

			// 静态内部类允许访问外部(私有/公有)的静态常量/变量
			System.out.println(a);
			System.out.println(c);
			// 静态内部类不允许访问外部(私有/公有)的普通常量/变量
			System.out.println(b);
			System.out.println(d);
			System.out.println(wa);
			System.out.println(wb);
			System.out.println(wc);
			System.out.println(wd);
		}

		// 静态内部类可以声明静态方法
		private static void world() {
		}
	}
}
