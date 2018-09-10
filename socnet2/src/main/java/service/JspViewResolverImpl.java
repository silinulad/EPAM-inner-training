package service;

public class JspViewResolverImpl implements ViewResolver {

	private static final String	PREFIX	= "/WEB-INF/";
	private static final String	SUFFIX	= ".jsp";
	
	
	@Override
	public String getPathToView(String viewName) {
		return PREFIX + viewName + SUFFIX;
	}
}
