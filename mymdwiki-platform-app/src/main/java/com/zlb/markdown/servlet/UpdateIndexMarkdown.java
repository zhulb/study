package com.zlb.markdown.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xquant.util.debug.Debug;
import com.xquant.util.markdown.EventConfig;
import com.xquant.util.markdown.Markdown;
import com.xquant.util.markdown.indexconfig.CreateIndexMdConfig;
import com.zlb.markdown.data.tool.DataTool;
import com.zlb.markdown.moudle.GetDataFactory;
import com.zlb.markdown.moudle.GetDirectoryIndexTree;
import com.zlb.markdown.moudle.GetIndexTree;
import com.zlb.markdown.moudle.fileSystem.tool.JSONTreeToTreeData;
import com.zlb.markdown.moudle.fileSystem.tool.PrintCurStackTrace;
import com.zlb.markdown.moudle.fileSystem.tool.Source;

public class UpdateIndexMarkdown extends HttpServlet {
	private HttpServletResponse response;
	private HttpServletRequest request;

	/**
	 * Constructor of the object.
	 */
	public UpdateIndexMarkdown() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}
	
	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//if(true)throw new RuntimeException("测试");
		if(Debug.printStackTrace)PrintCurStackTrace.println();
		this.request=request;
		this.response=response;
		Markdown create=new Markdown();
		EventConfig createIndexMdConfig=new CreateIndexMdConfig();
		create.addEventConfig(createIndexMdConfig);
		//TODO 设置读取文件路径
		create.add(CreateIndexMdConfig.mdUploadDir+"/");
		create.loadAll();
		System.out.println("更新导航文档成功！");
		returnText("{\"result\":true}");
	}

	private void returnText(String string) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.println(string);
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		response.setContentType("text/html");
//		response.setCharacterEncoding("utf-8");
//		PrintWriter out = response.getWriter();
//		out.println(DataTool.getDataTool().getHeadInfo());
//		out.flush();
//		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
	}
}
