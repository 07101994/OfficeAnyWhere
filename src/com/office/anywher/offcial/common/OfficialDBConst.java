package com.office.anywher.offcial.common;

/**
 * ����DB�����ĳ�������
 * 
 * @author wangzp
 * 
 */
public interface OfficialDBConst {

	/**
	 * ���ı�����
	 */
	public static final String TABLE_H_GONGWEN = "H_GONGWEN";
	
	/**
	 * ���ı��ֶζ���
	 */
	public interface H_GONGWEN {
		public static final String L_ID = "L_ID";
		public static final String L_USER = "L_USER"; // �û�����ʱ��ΪA��B�����û�
		public static final String L_STATE = "L_STATE"; // ״̬������δ���0�����������1
		public static final String L_OLD_CODE = "L_OLD_CODE"; // �����ֺ�
		public static final String L_OLD_TITLE = "L_OLD_TITLE"; // ����
		public static final String L_OFFICIAL_SUMMARY = "L_OFFICIAL_SUMMARY"; // ����ժҪ
		public static final String L_OFFICIAL_CONTENT = "L_OFFICIAL_CONTENT"; // ��������
		public static final String L_PROCESS_NAME = "L_PROCESS_NAME"; // ��������
		public static final String L_CUR_STEP = "L_CUR_STEP"; // ��ǰ�ڵ㣨��塢���ġ����졢���졢�Ѱ죩
		public static final String L_UP_STEP = "L_UP_STEP"; // ��һ�ڵ�
	}
	
	/**
	 * �������ı�H_GONGWEN��sql���
	 */
	public interface CreateGWTableSQL {
		public static final String CREATE_H_GONGWEN_SQL = " CREATE TABLE IF NOT EXISTS "
				+ TABLE_H_GONGWEN
				+ " ( "
				+ H_GONGWEN.L_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ H_GONGWEN.L_USER + " text, "
				+ H_GONGWEN.L_STATE + " text, "
				+ H_GONGWEN.L_OLD_CODE + " text, "
				+ H_GONGWEN.L_OLD_TITLE + " text, "
				+ H_GONGWEN.L_OFFICIAL_SUMMARY + " text, "
				+ H_GONGWEN.L_OFFICIAL_CONTENT + " text, "
				+ H_GONGWEN.L_PROCESS_NAME + " text, "
				+ H_GONGWEN.L_CUR_STEP + " text, "
				+ H_GONGWEN.L_UP_STEP + " text)";
	}
	
	/**
	 * ɾ�����ı�H_GONGWEN��sql���
	 */
	public interface DropGWTableSQL {
		public static final String DROP_H_REMEBER_LOGIN_SQL = " DROP TABLE  IF  EXISTS "
				+ TABLE_H_GONGWEN;
	}

}
