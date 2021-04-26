package genal_servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//@WebServlet("*.do")
public class GenalServletByDo extends GenalServlet{

	@Override
	public String configPath() {
		return "/ConfigApiDo.xml";
	}

	@Override
	public String mapKey(HttpServletRequest req, HttpServletResponse resp) {
		String quer=req.getRequestURI();
		
		int startIndex=quer.lastIndexOf("/");
		int endIndex=quer.lastIndexOf(".");
		String mapKey=quer.substring(startIndex+1,endIndex);
		
		return mapKey;
	}
}
