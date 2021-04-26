package genal_servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Element;

import io.XmlReader;
/*
 * zkgeneralservlet类
 * 实现分开控制servlet，实现servlet通用模式的管理(.do)可自己适配
 * configs包含servlet的类名和路径信息
 */
//@WebServlet("*.do")
public class GenalServletXT extends HttpServlet{
//	ArrayList<ZkGeneralServletConfig> servlets;
	HashMap<String, GenalServletConfig> configs;
	@Override
	public void init() throws ServletException {
		configs=getConfigs("/ConfigApiDo.xml");
		System.out.println("初始化：获得generalConfigs|at:GenalServlet.init()");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//...do
		String quer=req.getRequestURI();
		String quu=req.getQueryString();
		String quuu=req.getServerName();
		String quuuu=req.getServletPath();
	
		System.out.println("运行：General servlet.url:"+quer+"|at:GenalServlet.doGet()");
		System.out.println("运行：获得请求服务用户 sessionid:"+req.getRequestedSessionId()+"|at:GenalServlet.doGet()");
		
		int startIndex=quer.lastIndexOf("/");
		int endIndex=quer.lastIndexOf(".");
		String mapKey=quer.substring(startIndex+1,endIndex);
		
	
		GenalServletConfig config=configs.get(mapKey);
		if(config==null)
		{
			resp.sendError(404, "未找到当前指定servlet:"+mapKey+"");
			System.out.println("运行："+mapKey+"不在服务配置中|at:GenalServlet.doGet()");
			return;
		}
		
		GenalDo obj=null;
		try {
			obj=(GenalDo) config.getClas().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			System.out.println("错误：不能实例化"+config.getClassName()+"目标类|at:GenalServlet.doGet()");
			e.printStackTrace();
		}
		System.out.println("运行：General servlet:"+mapKey+"|at:GenalServlet.doGet()");
		obj.excute(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}

	
	public HashMap<String, GenalServletConfig> getConfigs(String path)
	{
		HashMap<String, GenalServletConfig> cons=new HashMap<String, GenalServletConfig>();
		XmlReader reader=new XmlReader(path);
		Element root=reader.read().getRoot();
		Iterator<Element> elements=reader.getIter(root);
		while(elements.hasNext())
		{
			Element el=elements.next();
			String name=el.attributeValue("name").trim();
			String className=el.attributeValue("class").trim();
			if(className==null||className.equals(""))
			{
				System.out.println("警告：未定义"+name+"对应类路径名称| at:GenalServlet.init().getConfigs()");
				break;
			}
			GenalServletConfig config=new GenalServletConfig(name, className);
			Class claz = null;
			try{
				 claz=Class.forName(className);
			}catch (ClassNotFoundException e) {
				System.out.println("错误：未找到"+className+"对应类| at:GenalServlet.init().getConfigs()");
				e.printStackTrace();
			}
			config.setClas(claz);
			cons.put(name, config);
		}
		return cons;
	}
}
