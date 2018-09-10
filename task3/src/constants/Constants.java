package constants;

public final class Constants {

	private Constants() {}

	public static final int		ZERO_LENGTH_ARRAY		= 0;
	public static final String	SPACE					= " ";
	public static final String	EMPTY_STRING			= "";
	public static final String	OPEN_BRACKET			= "(";
	public static final String	CLOSE_BRACKET			= ")";
	public static final String	ERROR_EXPRESSION		= "Error expression";
	public static final String	ERROR_BRACKETS			= "Error brackets";
	public static final String	ERROR_FILE				= "Input file error";
	public static final String	NEW_LINE				= System.lineSeparator();

	// regular expressions
	public static final String	INITIAL_REGEX			= "(calculator|coins)[1-9]\\d*";
	public static final String	ONE_OR_MORE_SPACES		= "\\s+";
	public static final String	ONLY_DIGITS				= "^[\\d\\s]*$";
	public static final String	ALL_LINE				= "[^\\*\\/\\+\\-\\(\\)\\d\\s]+";
	public static final String	REPEATED_OPERATION		= "[\\*\\/\\+\\-][\\*\\/\\+\\-]+";
	public static final String	START_LINE				= "^[\\*\\/\\+\\-\\)]";
	public static final String	END_LINE				= "[\\*\\/\\+\\-\\(]$";
	public static final String	LINE_WITH_OPEN_BRACKET	= "\\d*\\s*\\(\\s*[\\*\\/\\+\\-]+|\\d+\\s*\\(\\s*[\\*\\/\\+\\-]*";
	public static final String	LINE_WITH_CLOSE_BRACKET	= "[\\*\\/\\+\\-]*\\s*\\)\\s*\\d+|[\\*\\/\\+\\-]+\\s*\\)\\s*\\d*";
	public static final String	BRACKETS				= "\\(\\s*[\\d\\s]*\\s*\\)|\\)\\s*\\(";
}
