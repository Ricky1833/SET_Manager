package org.sdsu.cs532.set;

/**
 * Interface defining the application's command IDs.
 * Key bindings can be defined for specific commands.
 * To associate an action with a command, use IAction.setActionDefinitionId(commandId).
 *
 * @see org.eclipse.jface.action.IAction#setActionDefinitionId(String)
 */
public interface ICommandIds {

    public static final String CMD_OPEN = "org.sdsu.cs532.SET.open";
    public static final String CMD_OPEN_MESSAGE = "org.sdsu.cs532.SET.openMessage";
    public static final String CMD_OPEN_LISTING = "org.sdsu.cs532.SET.openListing";
    
}
