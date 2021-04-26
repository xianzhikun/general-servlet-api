package genal_servlet;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.URLParser;

public abstract class GenalParamApi implements GenalDo{

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse resp) {
		HashMap<String, String> map=null;
		String query=req.getQueryString();
		try {
			map=URLParser.params(query, resp.getCharacterEncoding());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		excute(req, resp,map);
		
	}
	public abstract void excute(HttpServletRequest req, HttpServletResponse resp,HashMap<String, String> params);
	
}
