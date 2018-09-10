package service.action.common;

import exception.ActionException;

/**
 * Created by Silin on 07.2018.
 */

/**
 * ActionFactory provides the suitable Action object based on the user "action"
 * request parameter. Implementation of the FactoryMethod pattern
 */
public class ActionFactory {
	private static final String ERROR_MESSAGE = "Action not found: ";
	private static ActionFactory instance = new ActionFactory();

	public static ActionFactory getInstance() {
		return instance;
	}

	private ActionFactory() {
	}

	/**
	 * Get an Action object from the ActionProvider by an action request parameter
	 *
	 * @param name
	 *            of the action from the request
	 * @return Action object
	 * @throws ActionException
	 */
	public Action getAction(String name) throws ActionException {
		if (name != null) {
			for (ActionProvider actionProvider : ActionProvider.values()) {
				if (name.equalsIgnoreCase(actionProvider.getName())) {
					return actionProvider.getAction();
				}
			}
		}
		throw new ActionException(ERROR_MESSAGE + name);
	}

	/**
	 * Get a boolean value from the ActionProvider by an action request parameter
	 *
	 * @param name
	 *            of the action from the request
	 * @return true if the action can repeat adding the item into the database
	 *         otherwise false
	 * @throws ActionException
	 */
	public boolean isRepeatsAdding(String name) throws ActionException {
		if (name != null) {
			for (ActionProvider actionProvider : ActionProvider.values()) {
				if (name.equalsIgnoreCase(actionProvider.getName())) {
					return actionProvider.isRepeatsAdding();
				}
			}
		}
		throw new ActionException(ERROR_MESSAGE + name);
	}
}