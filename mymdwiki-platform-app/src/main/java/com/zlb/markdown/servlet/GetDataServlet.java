package com.zlb.markdown.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zlb.markdown.data.tool.DataTool;
import com.zlb.markdown.moudle.GetDataFactory;
import com.zlb.markdown.moudle.GetDirectoryIndexTree;
import com.zlb.markdown.moudle.GetIndexTree;
import com.zlb.markdown.moudle.fileSystem.tool.JSONTreeToTreeData;
import com.zlb.markdown.moudle.fileSystem.tool.PrintCurStackTrace;
import com.zlb.markdown.moudle.fileSystem.tool.Source;

public class GetDataServlet extends HttpServlet {
	private HttpServletResponse response;
	private HttpServletRequest request;

	/**
	 * Constructor of the object.
	 */
	public GetDataServlet() {
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
		this.request=request;
		this.response=response;
		PrintCurStackTrace.println();
		String name=request.getParameter("name");
		if("directoryIndexTree".equals(name)){
			JSONArray resultArray=(JSONArray) Source.getInstance().getSource("directoryIndexTree",Source.choice_save,Source.choice_update);
			returnText(resultArray.toJSONString());
			return;
		}
		else if("fenLeiIndexTree".equals(name)){
			JSONArray resultArray=(JSONArray) Source.getInstance().getSource("fenLeiIndexTree",Source.choice_save,Source.choice_update);
			returnText(resultArray.toJSONString());
			return;
		}
		else if("tagIndexTree".equals(name)){
			JSONArray resultArray=(JSONArray) Source.getInstance().getSource("tagIndexTree",Source.choice_save,Source.choice_update);
			returnText(resultArray.toJSONString());
			return;
		}
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
