package com.mashibing.spring.proxy.asm;


import aj.org.objectweb.asm.ClassReader;

import java.io.IOException;

/**
 * @author hike97 2month
 * @create 2021-04-29 11:47
 * @desc asm 了解
 * 大致分三步：
 * 	一 classReader 读出 代理类
 * 	二 classVisitor 对字节码文件进行修改 加入代理代码
 * 	三 classWriter 写出被增强的代理类
 **/
public class ASMTest {
	public static void main (String[] args) throws IOException {
//		int a = 8;
//		a = a++;//去掉a= 就会变成9
//		//将局部变量表中的a+1 并没有改变栈中 a的数值  所以输出为8
//		System.out.println (a);
		/**
		 * ASM 第一步 读取class 文件
		 */
		ClassPrinter cp = new ClassPrinter ();
//		ClassReader cr = new ClassReader ("java.lang.Runnable");
		ClassReader cr = new ClassReader (ClassPrinter.class.getClassLoader ().getResourceAsStream ("com/mashibing/spring/proxy/asm/T1.class"));
		//		cr.accept (cp,0);
	}
	/**
	 *  字节码文件
	 *  0 bipush 8
	 *  2 istore_1
	 *  3 iload_1
	 *  4 iinc 1 by 1  将局部变量表中的a+1 并没有改变栈中 a的数值
	 *  7 istore_1
	 *  8 getstatic #2 <java/lang/System.out>
	 * 11 iload_1
	 * 12 invokevirtual #3 <java/io/PrintStream.println>
	 * 15 return
	 */
}
