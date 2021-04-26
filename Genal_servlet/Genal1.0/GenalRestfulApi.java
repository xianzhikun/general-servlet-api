package genal_servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;


/*
 * ZkGeneralRestfulApi类 继承ZkRESTful并实现zkgeneraldo接口
 * 处理restful模式
 */
public abstract class GenalRestfulApi extends ZkRESTful implements GenalDo{

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse resp) {
		try {
			doGet(req,resp);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

}
