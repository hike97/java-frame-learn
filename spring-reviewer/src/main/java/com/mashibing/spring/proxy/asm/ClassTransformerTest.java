package com.mashibing.spring.proxy.asm;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;

import java.io.File;
import java.io.FileOutputStream;

import static jdk.internal.org.objectweb.asm.Opcodes.ASM4;
import static jdk.internal.org.objectweb.asm.Opcodes.INVOKESTATIC;

/**
 * @author hike97 2month
 * @create 2021-04-29 13:22
 * @desc ASM 转换
 **/
public class ClassTransformerTest {
	public static void main (String[] args) throws Exception {
		ClassWriter classWriter = new ClassWriter (0);
		ClassReader classReader = new ClassReader (ClassPrinter.class.getClassLoader ().getResourceAsStream ("com/mashibing/spring/proxy/asm/Tank.class"));
		ClassVisitor classVisitor = new ClassVisitor (ASM4, classWriter) {
			@Override
			public MethodVisitor visitMethod (int access, String name, String descriptor, String signature, String[] exceptions) {
				MethodVisitor mv = super.visitMethod (access, name, descriptor, signature, exceptions);
				return new MethodVisitor (ASM4,mv) {
					@Override
					public void visitCode () {
						visitMethodInsn(INVOKESTATIC, "com/mashibing/spring/proxy/asm/TimeProxy","before", "()V", false);
						super.visitCode ();
					}
				};
			}
		};
		//classReader 接受classVisitor  然后 写入到classWriter 中 最后 输出增强后的字节码
		classReader.accept (classVisitor,0);

		byte[] bytes = classWriter.toByteArray ();

		//先初始化增强类TimeProxy
		MyClassLoader cl = new MyClassLoader ();
		cl.loadClass ("com.mashibing.spring.proxy.asm.TimeProxy");

		Class c2 = cl.defineClass ("com.mashibing.spring.proxy.asm.Tank", bytes);
		c2.getConstructor ().newInstance ();

		String path = (String)System.getProperties().get("user.dir");
		File f = new File(path + "/com/mashibing/spring/proxy/asm/");
		f.mkdirs();

		FileOutputStream fos = new FileOutputStream(new File(path + "/com/mashibing/spring/proxy/asm/Tank_0.class"));
		fos.write(bytes);
		fos.flush();
		fos.close();


	}
}
