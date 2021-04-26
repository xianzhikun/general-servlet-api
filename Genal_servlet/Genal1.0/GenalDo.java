package genal_servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
 * zkgeneraldo接口
 * 对req，resp的通用处理
 */
public interface GenalDo {
	public void excute(HttpServletRequest req, HttpServletResponse resp);
}
