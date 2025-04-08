package com.example.quiz13.constans;

public enum ResMessage {
	
	SUCCESS(200, "Success"),//
	PARAM_DATE_ERROR(400,"param date error"),//
	QUES_TYPE_ERROR(400,"ques type error"),//
	PARAM_OPTIONS_ERROR(400,"parm options error"),//
	PARAM_OPTIONS1_ERROR(400,"parm options1 error"),//
	OPTIONS_PARSE_REEOR(400,"Options parse error"),//
	OPTIONS_SIZE_ERROR(400,"options size error"),//
	SQL_ERROR(400,"SQL error"),//
	PARAM_QUIZ_ID_ERROR(400,"param quiz id error"),//
	QUIZ_ID_MISMATCH(400,"Quiz_id_mismatch"),//
	ID_NOT_FOUND(400,"id_not_found"),//
	OUT_OF_FILLIN_DATE_RANGE(400, "Out of fillin date range!!"),//
	EMAIL_DUPLICATED(400, "Email duplicated!!"),//
	ANSWER_REQUIRED(400,"answer_required"),//
	ANSWER_OPTION_MISMATCH(400,"answer option mismatch"),//
	ONE_OPTION_IS_ALLOWED(400, "One option is allowed!!"),//
	ANSWER_PARSE_REEOR(400,"answer parse error"),//
	;
	private int code;
	
	private String message;
	
	

	private ResMessage(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
     public static class ConstantsMessage{
		
		public static final String PARAM_QUIZ_NAME_REEOR ="Param quiz_name error!!";
		
		public static final String PARAM_DESCRIPTION_REEOR ="Param description error!!";
		
		public static final String PARAM_DESCRIPTION_LENGTH_TOO_LONG ="Param description length too long!!";
		
		public static final String PARAM_START_DATE_REEOR ="Param start_date error!!";
		
		public static final String PARAM_END_DATE_REEOR ="Param end_date error!!";
		
		public static final String PARAM_QUIZ_ID_REEOR ="Param quiz_id error!!";
		
		public static final String PARAM_QUES_ID_REEOR ="Param ques_id error!!";
		
		public static final String PARAM_QUES_NAME_REEOR ="Param question_name error!!";
		
		public static final String PARAM_QUES_TYPE_REEOR ="Param question_type error!!";
		
		public static final String PARAM_QUES_LIST_REEOR ="Param question_list error!!";
		
		public static final String PARAM_QUIZ_ID_LIST_REEOR ="Param quiz_id_list error!!";
		
		public static final String EMAIL_IS_NECESSARY ="email is necessary!!";
		
		public static final String FILLIN_IS_NECESSARY ="fillin is necessary!!";
		
		
	}
}
