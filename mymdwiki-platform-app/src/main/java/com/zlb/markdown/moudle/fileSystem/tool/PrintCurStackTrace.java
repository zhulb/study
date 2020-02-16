package com.zlb.markdown.moudle.fileSystem.tool;

import java.io.PrintStream;

/**
 * 打印栈信息
 * @author zhulb
 *
 */
public class PrintCurStackTrace {
	
	public static void print(){
		System.out.print(Thread.currentThread().getStackTrace()[2].getClassName()+
				"->"+Thread.currentThread().getStackTrace()[2].getMethodName());
		System.err.println(Thread.currentThread().getStackTrace()[2]);
	}
	public static void println(){
//		System.out.println(Thread.currentThread().getStackTrace()[2].getClassName()+
//				"->"+Thread.currentThread().getStackTrace()[2].getMethodName());
		System.err.println(Thread.currentThread().getStackTrace()[2]);
	}
	
	public static void println(String msg) {
		String printMsg=Thread.currentThread().getStackTrace()[2].getClassName()
				+"."+Thread.currentThread().getStackTrace()[2].getMethodName()
				+"("+Thread.currentThread().getStackTrace()[2].getFileName()+":"
				+Thread.currentThread().getStackTrace()[2].getLineNumber()
				+")";
		printMsg+="->"+msg;
		System.err.println(printMsg);
		System.err.println(Thread.currentThread().getStackTrace()[2]);
	}
	
}
