package genal_servlet;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import io.InputReader;
import io.OutputWriter;
import tool.URLParser;
/*
 * zkrestful类
 * 实现restful接口的servlet
 */
public abstract class ZkRESTful extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InputReader reader=new InputReader();
		InputStream input=request.getInputStream();
		reader.setInputstream(input);
		//System.out.println("respone encoding"+response.getCharacterEncoding());
		reader.setCharest("utf-8");
		
		String reqstr=request.getQueryString();
		HashMap<String, String> params= URLParser.params(reqstr, "utf-8");
		JSONObject obj=reader.readJSONobj();
		input.close();
//		if(obj==null)
//		{
//			response.sendError(500, "未接收到JSON数据！！");
//		}
		Object obj2 = null;
		try {
			obj2 = excute(obj,params,request,response);
		} catch (SQLException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		if(obj2==null)
		{
			response.sendError(500, "未发送JSON数据！！");
			return;
		}
		
		JSONObject jObj=null;
		
		if(obj2 instanceof JSONObject)
		{
			jObj= (JSONObject) obj2;
		}
		else
		{
			jObj=new JSONObject(obj2);
		}
		
		
		OutputWriter wirter=new OutputWriter();
		OutputStream output=response.getOutputStream();
		wirter.setCharest("utf-8");
		wirter.setOutputStream(output);
		wirter.writeJSONobj(jObj);
		output.close();
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	public abstract  Object excute(JSONObject obj,HashMap<String, String> params,HttpServletRequest req, HttpServletResponse resp) throws SQLException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException;

}
