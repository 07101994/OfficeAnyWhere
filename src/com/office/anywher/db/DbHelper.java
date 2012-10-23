package com.office.anywher.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "hecheng_oa.db";  
	private static final int DATABASE_VERSION = 2;
	public DbHelper(Context context) {  
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }  
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DBConst.CreateTableSQL.CREATE_H_REMEBER_LOGIN_SQL);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int paramInt1,
			int paramInt2) {
		db.execSQL(DBConst.DropTableSQL.DROP_H_REMEBER_LOGIN_SQL);
		onCreate(db);
	} 
}
/*
NULL ֵΪNULL
INTEGER ֵΪ�����ŵ����ͣ����������1��2��3��4��6��8�ֽڴ洢
REAL ֵΪ�����ͣ�8�ֽڴ洢 
TEXT ֵΪtext�ַ�����ʹ�����ݿ����(UTF-8, UTF-16BE or UTF-16-LE)�洢
BLOB ֵΪ���������ݣ����忴ʵ������
��ʵ���ϣ�sqlite3Ҳ�������µ��������ͣ�
smallint  16 λԪ������
interger  32 λԪ������
decimal(p,s)  p ��ȷֵ�� s ��С��ʮ��λ��������ȷֵp��ָȫ���м�����(digits)��Сֵ    ��s��ָС�������м�λ�������û���ر�ָ������ϵͳ����Ϊ p=5; s=0 ��
float   32λԪ��ʵ����
double   64λԪ��ʵ����
char(n)   n ���ȵ��ִ���n���ܳ��� 254��
varchar(n)  ���Ȳ��̶�������󳤶�Ϊ n ���ִ���n���ܳ��� 4000��
graphic(n)  �� char(n) һ���������䵥λ��������Ԫ double-bytes�� n���ܳ���127��   �����̬��Ϊ��֧Ԯ������Ԫ���ȵ����壬���������֡�
vargraphic(n)  �ɱ䳤��������󳤶�Ϊ n ��˫��Ԫ�ִ���n���ܳ��� 2000��
date   ������ ��ݡ��·ݡ����ڡ�
time   ������ Сʱ�����ӡ��롣
timestamp  ������ �ꡢ�¡��ա�ʱ���֡��롢ǧ��֮һ�롣
 * */
