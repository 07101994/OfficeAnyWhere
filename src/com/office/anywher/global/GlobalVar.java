package com.office.anywher.global;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.office.anywher.IConst;
import com.office.anywher.email.EmailInfo;
import com.office.anywher.sehedule.ScheduleInfo;

/**
 * unique variable single model
 * 
 * @author Administrator
 * 
 */
public class GlobalVar {
	private static Map<String, Object> mVar = new HashMap<String, Object>();
	private static GlobalVar mGlobalVar = new GlobalVar();
	private static int id = 0;
	private GlobalVar() {
		initEmailDatas();
		try {
			initSchedule();
			initEmailDatas();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public synchronized static int getId(){
		return id++;
	}
	public synchronized static GlobalVar getInstance() {
		return mGlobalVar;
	}

	public synchronized void put(String key, Object obj) {
		mVar.put(key, obj);
	}

	public synchronized Object get(String key) {
		if (mVar.containsKey(key)) {
			return mVar.get(key);
		}
		return null;
	}

	private static void initEmailDatas() {
		List<EmailInfo> aEmails = new ArrayList<EmailInfo>();
		for (int i = 0; i < 10; i++) {
			EmailInfo aEmail = new EmailInfo();
			aEmail.mDoneDate = new Date();
			aEmail.mEmailTitle = "A�ҵ��ռ����������"+i;
			aEmail.mEmailContent = "\tA�ҵ��ռ����������,�ҵ��ռ����������\n�ҵ��ռ����������,�ҵ��ռ����������.";
			aEmail.mType = 1;
			aEmail.mState =2;
			aEmail.mFrom = "2";
			aEmail.mTo = "1";
			aEmail.mEmailId = getId();
			aEmails.add(aEmail);
		}
		for (int i = 0; i < 6; i++) {
			EmailInfo aEmail = new EmailInfo();
			aEmail.mDoneDate = new Date();
			aEmail.mEmailId = getId();
			aEmail.mEmailTitle = "A�ҵķ������������"+i;
			aEmail.mEmailContent = "\tA�ҵķ������������,�ҵķ������������\n�ҵķ������������,�ҵķ������������.";
			aEmail.mType = 2;
			aEmail.mState =4;
			aEmail.mFrom = "2";
			aEmail.mTo = "1";
			aEmails.add(aEmail);
		}
		for (int i = 0; i < 8; i++) {
			EmailInfo aEmail = new EmailInfo();
			aEmail.mDoneDate = new Date();
			aEmail.mEmailId = getId();
			aEmail.mEmailTitle = "A�ҵĲݸ����������"+i;
			aEmail.mEmailContent = "\tA�ҵĲݸ����������,�ҵĲݸ����������\n�ҵĲݸ����������,�ҵĲݸ����������.";
			aEmail.mType  = 3;
			aEmail.mState = 5;
			aEmail.mFrom = "2";
			aEmail.mTo = "1";
			aEmails.add(aEmail);
		}
		Map<String,List<EmailInfo>> userEmailsMap= new HashMap<String,List<EmailInfo>>();
		userEmailsMap.put("1", aEmails);
		mVar.put(IConst.EMAIL_DEMO_DATA,userEmailsMap);
		
		
		aEmails = new ArrayList<EmailInfo>();
		for (int i = 0; i < 10; i++) {
			EmailInfo aEmail = new EmailInfo();
			aEmail.mDoneDate = new Date();
			aEmail.mEmailTitle = "B�ҵ��ռ����������"+i;
			aEmail.mEmailContent = "\tB�ҵ��ռ����������,�ҵ��ռ����������\n�ҵ��ռ����������,�ҵ��ռ����������.";
			aEmail.mType = 1;
			aEmail.mState =2;
			aEmail.mFrom = "1";
			aEmail.mTo = "2";
			aEmail.mEmailId = getId();
			aEmails.add(aEmail);
		}
		for (int i = 0; i < 6; i++) {
			EmailInfo aEmail = new EmailInfo();
			aEmail.mDoneDate = new Date();
			aEmail.mEmailTitle = "B�ҵķ������������"+i;
			aEmail.mEmailContent = "\tB�ҵķ������������,�ҵķ������������\n�ҵķ������������,�ҵķ������������.";
			aEmail.mType = 2;
			aEmail.mState =4;
			aEmail.mFrom = "1";
			aEmail.mTo = "2";
			aEmail.mEmailId = getId();
			aEmails.add(aEmail);
		}
		for (int i = 0; i < 8; i++) {
			EmailInfo aEmail = new EmailInfo();
			aEmail.mDoneDate = new Date();
			aEmail.mEmailTitle = "B�ҵĲݸ����������"+i;
			aEmail.mEmailContent = "\tB�ҵĲݸ����������,�ҵĲݸ����������\n�ҵĲݸ����������,�ҵĲݸ����������.";
			aEmail.mType  = 3;
			aEmail.mState = 5;
			aEmail.mFrom = "1";
			aEmail.mTo = "2";
			aEmail.mEmailId = getId();
			aEmails.add(aEmail);
		}
		userEmailsMap.put("2", aEmails);
		mVar.put(IConst.EMAIL_DEMO_DATA,userEmailsMap);
	}
	
	private void initSchedule() throws ParseException{
		List<ScheduleInfo> mScheduleInfoList = new ArrayList<ScheduleInfo>();
		SimpleDateFormat sdfMore = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int day = 1;
		String from = "2012-10-10 17:01:01";
		String to = "2012-10-20 22:01:01";
		for(int i=0;i<13;i++){
			from = "2012-10-"+(day>10?day:"0"+day)+" 17:01:01";
			to = "2012-10-"+(day>10?day:"0"+day)+" 22:01:01";
			ScheduleInfo aSchedule = new ScheduleInfo();
			aSchedule.mScheduleTitle = ""+i+"�ҵ��ճ̲�������"+i;
			aSchedule.mScheduleDetails = "�ҵ��ճ̲��������ҵ��ճ̲��������ҵ��ճ̲��������ҵ��ճ̲��������ҵ��ճ̲��������ҵ��ճ̲�������";
			Calendar c = Calendar.getInstance();
			
			c.setTime(sdfMore.parse(from));
			aSchedule.mFrom = sdfMore
					.parse(c.get(Calendar.YEAR)
							+ "-"
							+ ((c.get(Calendar.MONTH) + 1) >= 10 ? c
									.get(Calendar.MONTH) + 1 : "0"
									+ (c.get(Calendar.MONTH) + 1))
							+ "-"
							+ (c.get(Calendar.DATE) >= 10 ? c.get(Calendar.DATE)
									: "0" + c.get(Calendar.DATE))+" "+"00:01:01");
			c.setTime(sdfMore.parse(to));
			aSchedule.mTo = sdfMore
					.parse(c.get(Calendar.YEAR)
							+ "-"
							+ ((c.get(Calendar.MONTH) + 1) >= 10 ? c
									.get(Calendar.MONTH) + 1 : "0"
									+ (c.get(Calendar.MONTH) + 1))
							+ "-"
							+ (c.get(Calendar.DATE) >= 10 ? c.get(Calendar.DATE)
									: "0" + c.get(Calendar.DATE))+" "+"23:59:59");
			day+=2;
			mScheduleInfoList.add(aSchedule);
		}
		mVar.put(IConst.SCHEDUL_DEMO_DATA, mScheduleInfoList);
	}
}
