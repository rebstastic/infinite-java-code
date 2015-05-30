package hr.fer.zemris.java.tecaj.hw07.shell;

/**
 * Shell status. It has two states: {@code CONTINUE} - used when every command
 * except {@code exit} command is executed, and {@code TERMINATE} - used only
 * when {@code exit} command is ecexuted.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public enum ShellStatus {

	/**
	 * Used when every command except {@code exit} command is executed.
	 */
	CONTINUE,

	/**
	 * Used only when {@code exit} command is ecexuted.
	 */
	TERMINATE;

}
