package com.zlb.markdown.test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.UnicodeDetector;

public class EncodingCheck {
	private static EncodingCheck encodingCheck=null;
	public static EncodingCheck getInstance(){
		if(encodingCheck==null){
			encodingCheck=new EncodingCheck();
		}
		return encodingCheck;
	}
	public static void main(String[] args) throws MalformedURLException, IOException {
		CodepageDetectorProxy codepageDetectorProxy=CodepageDetectorProxy.getInstance();
		codepageDetectorProxy.add(ASCIIDetector.getInstance());
		codepageDetectorProxy.add(UnicodeDetector.getInstance());
		
		File file=new File(EncodingCheck.getInstance().getClass().getClassLoader().getResource("com/zlb/markdown/test/EncodingCheck_utf-8.txt").getPath());
		
		System.out.println(file.getPath());
		Charset charset=null;
		System.out.println(file.exists());
		charset=codepageDetectorProxy.detectCodepage(file.toURL());
		System.out.println(charset.name());
	}
}
