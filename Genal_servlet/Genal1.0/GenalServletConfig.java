package genal_servlet;
/*
 * ZkGeneralServletConfig类
 * 包含configs中的generaldo的信息
 * 
 */
public class GenalServletConfig {
	private String name;//映射名称
	private String className;//类名
	private Class clas;//.Class
	public GenalServletConfig()
	{
		
	}
	public GenalServletConfig(String name,String className)
	{
		this.name=name;
		this.className=className;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Class getClas() {
		return clas;
	}
	public void setClas(Class clas) {
		this.clas = clas;
	}
	
}
