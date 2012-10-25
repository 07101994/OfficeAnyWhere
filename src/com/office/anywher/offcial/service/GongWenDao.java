package com.office.anywher.offcial.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.office.anywher.db.DbHelper;
import com.office.anywher.offcial.common.OfficailConst;
import com.office.anywher.offcial.common.OfficialDBConst;
import com.office.anywher.offcial.entity.GongWen;

/**
 * ���Ĵ������ؽӿ�
 * 
 * @author wangzp
 * 
 */
public class GongWenDao {

	private DbHelper helper;
	private SQLiteDatabase db;

	public GongWenDao(Context context) {
		helper = new DbHelper(context);
		db = helper.getWritableDatabase();
	}

	public void close() {
		db.close();
	}

	/**
	 * ���ݵ�ǰ�û��͵�ǰ�ڵ㣬��ȡ����
	 * 
	 * @param user
	 * @param curStep
	 * @return
	 */
	public GongWen[] getGongWen(String user, String curStep) {
		GongWen[] gongWens = null;
		GongWen gongWen = null;
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT * FROM ").append(OfficialDBConst.TABLE_H_GONGWEN)
				.append(" WHERE ").append(OfficialDBConst.H_GONGWEN.L_USER)
				.append(" = '").append(user).append("'").append(" AND ")
				.append(OfficialDBConst.H_GONGWEN.L_CUR_STEP).append(" = '")
				.append(curStep).append("'");
		Cursor c1 = db.rawQuery(sb.toString(), null);
		int count = c1.getCount();
		if (count < 1)
			insertOriginalData();
		Cursor c = db.rawQuery(sb.toString(), null); // �������ݺ���selectһ��
		gongWens = new GongWen[count];
		c.moveToFirst();
		for (int i = 0; i < count; i++) {
			if (c.isAfterLast())
				break;
			gongWen = new GongWen();
			gongWen.mGWId = c.getInt(c.getColumnIndex(OfficialDBConst.H_GONGWEN.L_ID));
			gongWen.mUser = c.getString(c.getColumnIndex(OfficialDBConst.H_GONGWEN.L_USER));
			gongWen.mState = c.getString(c.getColumnIndex(OfficialDBConst.H_GONGWEN.L_STATE));
			gongWen.mOldCode = c.getString(c.getColumnIndex(OfficialDBConst.H_GONGWEN.L_OLD_CODE));
			gongWen.mOldTitle = c.getString(c.getColumnIndex(OfficialDBConst.H_GONGWEN.L_OLD_TITLE));
			gongWen.mProcessName = c.getString(c.getColumnIndex(OfficialDBConst.H_GONGWEN.L_PROCESS_NAME));
			gongWen.mCurStep = c.getString(c.getColumnIndex(OfficialDBConst.H_GONGWEN.L_CUR_STEP));
			gongWen.mUpstep = c.getString(c.getColumnIndex(OfficialDBConst.H_GONGWEN.L_UP_STEP));
			gongWens[i] = gongWen;
			c.moveToNext();
		}
		return gongWens;
	}
	
	/**
	 * �ѵ�ǰ���ı���Ϊ��һ�ڵ��û��Ĺ��ġ�
	 * ��A�û������깫��1�󣬸ı乫��1���û��ֶ�ֵ���ڵ��ֶ�ֵ������ΪB�û��Ĺ���
	 * 
	 * @param gongWenOfUserA
	 */
	public void saveToNextUser(GongWen gongWenOfUserA) {
		if (gongWenOfUserA == null)
			return ;
		GongWen gongWenOfUserB = new GongWen();
		gongWenOfUserB.mUser = "B";
		gongWenOfUserB.mOldCode = gongWenOfUserA.mOldCode;
		gongWenOfUserB.mOldTitle = gongWenOfUserA.mOldTitle;
		gongWenOfUserB.mOffcialSummary = gongWenOfUserA.mOffcialSummary;
		gongWenOfUserB.mOffcialContent = gongWenOfUserA.mOffcialContent;
		gongWenOfUserB.mProcessName = gongWenOfUserA.mProcessName;
		gongWenOfUserB.mCurStep = OfficailConst.STATE_DESC_DY; // B�û��Ĺ���һ��ʼ�Ǵ��Ľڵ�
		gongWenOfUserB.mUpstep = gongWenOfUserA.mCurStep;
		insert(gongWenOfUserB);
	}
	
	/**
	 * ���빫�ı�ʱ��ȡ�µ�����
	 * 
	 * @return
	 */
	public int getNewId() {
		int newId = 0;
		Cursor c = db.rawQuery(" select count(*) as x from "
				+ OfficialDBConst.TABLE_H_GONGWEN, null);
		int count = 0;
		c.moveToFirst();
		while (!c.isAfterLast()) {
			count = c.getInt(c.getColumnIndex("x"));
			c.moveToNext();
		}
		c.close();
		c = null;
		if (count == 0)
			return 1;
		StringBuffer sb = new StringBuffer();
		sb.append(
				" SELECT MAX(" + OfficialDBConst.H_GONGWEN.L_ID + ") as x FROM ")
				.append(OfficialDBConst.TABLE_H_GONGWEN);
		c = db.rawQuery(sb.toString(), null);
		c.moveToFirst();
		while (!c.isAfterLast()) {
			newId = c.getInt(c.getColumnIndex("x")) + 1;
			c.moveToNext();
		}
		return newId;
	}

	/**
	 * �����µĹ��ļ�¼
	 * 
	 * @param gongWen
	 */
	public void insert(GongWen gongWen) {
		db.beginTransaction();
		try {
			db.execSQL("INSERT INTO " + OfficialDBConst.TABLE_H_GONGWEN
					+ " VALUES(?,?,?,?,?,?,?,?)", new Object[] { getNewId(),
					gongWen.mUser, gongWen.mState, gongWen.mOldCode,
					gongWen.mOldTitle, gongWen.mOffcialSummary,
					gongWen.mOffcialContent, gongWen.mProcessName,
					gongWen.mCurStep, gongWen.mUpstep });
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
	
	/**
	 * 
	 * �����ʼ���ݡ���������
	 * 
	 */
	public void insertOriginalData() {
		db.beginTransaction();
		try {
			String state = "0";
			String curStep = "����";
			String upStep = "���";
			for (int i = 1; i <= 4; i++) {
				if (i == 4)
					state = "1";
				if (i == 2) {
					upStep = curStep;
					curStep = "����";
				} else if (i == 3) {
					upStep = curStep;
					curStep = "����";
				} else if (i == 4) {
					upStep = curStep;
					curStep = "�Ѱ�";
				}
				db.execSQL("INSERT INTO " + OfficialDBConst.TABLE_H_GONGWEN
						+ " VALUES(?,?,?,?,?,?,?,?)", new Object[] {
						getNewId(), "A", state, "�ֺ�" + i, "����" + i,
						"����" + i + "��ժҪ", "����" + i + "�����ݡ�����", "����" + i,
						curStep, upStep });
				db.setTransactionSuccessful();
			}
		} finally {
			db.endTransaction();
		}
	}

	/**
	 * ���¹��ļ�¼
	 * 
	 * @param gongWen
	 */
	public void update(GongWen gongWen) {
		if (null == gongWen)
			return;
		ContentValues cv = new ContentValues();
		cv.put(OfficialDBConst.H_GONGWEN.L_STATE, gongWen.mState);
		db.update(OfficialDBConst.TABLE_H_GONGWEN, cv,
				OfficialDBConst.H_GONGWEN.L_ID + "=?",
				new String[] { gongWen.mGWId + "" });
	}

	/**
	 * ��������ɾ�����ļ�¼
	 * 
	 * @param gongWen
	 */
	public void delete(GongWen gongWen) {
		StringBuffer sb = new StringBuffer();
		sb.append(" DELETE FROM ").append(OfficialDBConst.TABLE_H_GONGWEN)
				.append(" WHERE ").append(OfficialDBConst.H_GONGWEN.L_ID)
				.append(" = ").append(gongWen.mGWId);
		db.execSQL(sb.toString());
	}

}
