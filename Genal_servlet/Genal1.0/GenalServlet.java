package genal_servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Element;

import io.XmlReader;

/**
 * Servlet implementation class GenalServlet
 */
//@WebServlet("/GenalServlet")
public abstract class GenalServlet extends HttpServlet {
	HashMap<String, GenalServletConfig> configs;
	
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		initConfigs();
		System.out.println("初始化：获得generalConfigs|at:GenalServlet.init()");
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(configs==null)
		{
			System.out.println("GenalServlet configs 未初始化|at GenalServlet.doGet()");
			initConfigs();
		}
		
		String quer=req.getRequestURI();
		System.out.println("运行：General servlet.url:"+quer+"|at:GenalServlet.doGet()");
		System.out.println("运行：获得请求服务用户 sessionid:"+req.getRequestedSessionId()+"|at:GenalServlet.doGet()");
		
		String key=mapKey(req,resp);
		System.out.println("获得映射名："+key);
		
		excuteByConfig(key,req,resp);
	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void excuteByConfig(String mapKey,HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
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
	
	public void  initConfigs()
	{
		String path=configPath();
		configs=getConfigs(path);
	}
	
	public abstract String configPath();
	
	public abstract String mapKey(HttpServletRequest req, HttpServletResponse resp);
	
	public  HashMap<String, GenalServletConfig> getConfigs(String path)
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
				System.out.println("警告：未定义"+name+"对应类路径名称| at:GenalServlet().getConfigs()");
				break;
			}
			
			GenalServletConfig config=new GenalServletConfig(name, className);
			
			Class claz = null;
			try{
				 claz=Class.forName(className);
			}catch (ClassNotFoundException e) {
				System.out.println("错误：未找到"+className+"对应类| at:GenalServlet().getConfigs()");
				e.printStackTrace();
			}
			
			config.setClas(claz);
			cons.put(name, config);
		}
		return cons;
	}

}
