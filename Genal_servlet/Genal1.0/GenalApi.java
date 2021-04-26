package genal_servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
 * zkgeneralapi类 继承zkgeneraldo
 */
public abstract class GenalApi implements GenalDo{
	HttpServletRequest req;
	HttpServletResponse resp;
	public abstract void excute(HttpServletRequest req,HttpServletResponse resp);
}
