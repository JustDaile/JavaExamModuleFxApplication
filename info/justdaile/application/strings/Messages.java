package info.justdaile.application.strings;

public class Messages {
	
	public static final String RESPONSE =  "%answer%:\n%response%\n\n";
	public static final String TEST_RESULT_MESSAGE = "Result : %score% out of %total% correct!";

	public static String compileMessage(String message, Object...args) {
		for(int i = 0; i < args.length; i++){
			message = message.replaceFirst("(%)[a-z]+(%)", args[i].toString());
		}
		return message;
	}
	
	

}
