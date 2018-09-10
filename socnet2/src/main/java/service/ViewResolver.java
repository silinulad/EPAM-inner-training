package service;

public interface ViewResolver {

	/**
	 * get a view name then add prefix and suffix and return the full path to the
	 * view
	 * 
	 * @param viewName
	 * @return full path to the view
	 */
	String getPathToView(String viewName);

}
