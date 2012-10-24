package com.office.anywher.meeting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

import com.office.anywher.IConst;
import com.office.anywher.ListActivity;
import com.office.anywher.R;
import com.office.anywher.utils.ActivityStackUtil;
/**
 * Эͬ�칫
 * @author Administrator
 *
 */
public class MeetingActivity extends ListActivity {
	private static final String tag = "InfoPubActivity";
	private RelativeLayout mHeader;
	private Button mSearch;
	private Button mMoreSearch;
	private boolean mMoreClick = false;
	private RelativeLayout mMoreCondition;
	private EditText mPubTitleTxt;
	private EditText mCreatePersonTxt;
	private TextView mSelectFromDate;
	private TextView mSelectToDate;
	private Spinner mValidSpinner;
	private TextView mRoom;
	SimpleDateFormat sdfMore = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStackUtil.add(this);
        aBottomNav = IConst.NavigetText.MEETING;
        LayoutInflater aInflater = LayoutInflater.from(this);
        mHeader = (RelativeLayout) aInflater.inflate(R.layout.list_meeting_info_header, null);
        mSearch = (Button)mHeader.findViewById(R.id.search_button);
        mMoreCondition = (RelativeLayout)mHeader.findViewById(R.id.more_condition);
        mPubTitleTxt = (EditText)mHeader.findViewById(R.id.pub_title_txt);
        mRoom  = (TextView)mHeader.findViewById(R.id.pub_info_state);
        mCreatePersonTxt = (EditText)mHeader.findViewById(R.id.create_person_txt);
        mSelectFromDate = (TextView)mHeader.findViewById(R.id.from_txt);
        mSelectToDate = (TextView)mHeader.findViewById(R.id.to_txt);
        mMoreSearch = (Button)mHeader.findViewById(R.id.more_search);
        mValidSpinner = (Spinner)mHeader.findViewById(R.id.valid_spiner);
        setValidSpinner();
        mSelectFromDate.setText("��ѡ��ʱ��");
        mSelectToDate.setText("��ѡ��ʱ��");
        mSelectFromDate.setOnClickListener(new TimeSelect());
        mSelectToDate.setOnClickListener(new TimeSelect());
        mMoreSearch.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				mMoreClick = !mMoreClick;
				Button btn = (Button)arg0;
				if(mMoreClick){
					btn.setBackgroundResource(R.drawable.up_log);
					mMoreCondition.setVisibility(View.VISIBLE);
				}else{
					btn.setBackgroundResource(R.drawable.down_log);
					mMoreCondition.setVisibility(View.GONE);
				}
			}
        });
        mListView.addHeaderView(mHeader);
        mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> paramAdapterView,
					View paramView, int paramInt, long paramLong) {
				TextView item = (TextView)paramView.findViewById(R.id.item_text);
				alterShowPubInfoDetails(paramInt-1);
			}
		});
        mSearch.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				try {
				    Date from = (mSelectFromDate == null || mSelectFromDate
							.getText().toString().contains("��ѡ��ʱ��")) ? null
							: sdfMore.parse(mSelectFromDate.getText()
									.toString());
					Date to = (mSelectToDate == null || mSelectToDate
							.getText().toString().contains("��ѡ��ʱ��")) ? null
							: sdfMore.parse(mSelectToDate.getText()
									.toString());
					if(from!=null && to != null && from.after(to)){
						Toast.makeText(
								MeetingActivity.this,
								"��ʼʱ�䣺[" + mSelectFromDate.getText()
										+ "]���ڽ���ʱ�䣺[" + mSelectToDate.getText()
										+ "]", Toast.LENGTH_SHORT).show();
					}else{
						getDatas();
						seteAdapter();
					}
					mMoreCondition.setVisibility(View.GONE);
					mMoreClick = false;
					mMoreSearch.setBackgroundResource(R.drawable.down_log);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
        });
        
        clickListener = new OnClickListener(){
    		@Override
    		public void onClick(View v) {
    			mCurClickView = v;
    			mDefaultProgress.show();
    			v.setEnabled(false);
    			switch(v.getId()){
    			case  R.id.bottom_naviget_1:
    				setNavigetStyle(0,false);
    				mDataType.setType("HuiYi 1");
    				type =1;
    				break;
    			case  R.id.bottom_naviget_2:
    				setNavigetStyle(1,false);
    				mDataType.setType("HuiYi 2");
    				type =2;
    				break;
    			case  R.id.bottom_naviget_3:
    				setNavigetStyle(2,false);
    				mDataType.setType("HuiYi3");
    				type =3;
    				break;
    			}
    			new Thread(mPullDatasThread).start();

    		}
    	};
    	 setNavigetStyle(0,true);
	}
	@Override
	public void onResume(){
		super.onResume();
		seteAdapter();
	}
	private int valid = 0;
	private void setValidSpinner(){
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter.add("ȫ��");
		adapter.add("��Ч");
		adapter.add("ʧЧ");
		mValidSpinner.setAdapter(adapter);
		mValidSpinner
		.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView,
					View view, int position, long id) {
				valid = position;
			}
			@Override
			public void onNothingSelected(AdapterView<?> view) {

			}
		});
	}
	private void alterShowPubInfoDetails(int index){
		if(list==null || list.size()==0 ||index>=list.size())return ;
		MeetingInfo info = list.get(index);
		LayoutInflater aInflater = LayoutInflater.from(this);
		RelativeLayout view= (RelativeLayout) aInflater.inflate(R.layout.pub_info, null);
		((TextView)view.findViewById(R.id.pub_info_title)).setText(info.mTitle==null?"":info.mTitle);
		((TextView)view.findViewById(R.id.pub_info_content)).setText(info.mContent==null?"":info.mContent);
		((TextView)view.findViewById(R.id.pub_info_state)).setText(info.mRoom==null?"":info.mRoom);
		String validName = "";
		switch(info.mValid){/*1��Ч��2��Ч*/
		case 1:
			validName = "��Ч";
			break;
		case 2:
			validName = "��Ч";
			break;
		case 0:
			validName = "��";
			break;
		}
		((TextView)view.findViewById(R.id.pub_info_time)).setText(info.mFrom==null?"":sdfMore.format(info.mFrom)+" �� ");
		((TextView)view.findViewById(R.id.pub_info_valid)).setText(validName);
		((TextView)view.findViewById(R.id.pub_info_dep)).setText(info.mDep==null?"":info.mDep);
		((TextView)view.findViewById(R.id.pub_info_create_personer)).setText(info.mTo==null?"":sdfMore.format(info.mTo));
		AlertDialog.Builder builder = new Builder(MeetingActivity.this);
		builder.setTitle("������Ϣ");
		builder.setView(view);
		builder.setPositiveButton("�ر�",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				 
			}
		});
		builder.create().show();
	}
	
	private int state = 0;
	private void setStateSpinner(){}
	
	class TimeSelect implements OnClickListener{
		private int year,month,day,hour,min,sec;
		@Override
		public void onClick(View v) {
			if(!(v instanceof TextView))return ;
			final TextView aTimeTextView = (TextView)v;
			AlertDialog.Builder builder = new Builder(MeetingActivity.this);
			LayoutInflater aInflater = LayoutInflater.from(MeetingActivity.this);
			RelativeLayout aSelectorOfTime = (RelativeLayout) aInflater.inflate(R.layout.date_select, null);
			final TimePicker tp = (TimePicker)aSelectorOfTime.findViewById(R.id.timePicker1);
			final DatePicker dp = (DatePicker)aSelectorOfTime.findViewById(R.id.datePicker1);
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
			month = calendar.get(Calendar.MONTH) + 1;
			day = calendar.get(Calendar.DAY_OF_MONTH);
			dp.init(year, month, day, new OnDateChangedListener() {
				public void onDateChanged(DatePicker view, int _year,
						int monthOfYear, int dayOfMonth) {
					year = _year;
					month = monthOfYear;
					day = dayOfMonth;
				}
			});
			tp.setOnTimeChangedListener(new OnTimeChangedListener(){
				@Override
				public void onTimeChanged(TimePicker arg0, int arg1, int arg2) {
					hour = arg1;min = arg2;
				}
			});
			tp.setIs24HourView(true);
			builder.setView(aSelectorOfTime);
			builder.setTitle("ʱ��ѡ����");
			builder.setPositiveButton("ȷ��",new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					aTimeTextView.setText(year+"-"+(month>=10?month:"0"+month)+"-"+(day>=10?day:"0"+day)+" "+(hour>=10?hour:"0"+hour)+":"+(min>=10?min:"0"+min));
				}
			});
			builder.create().show();
		}
	}
	private int type = 1;
	private List<MeetingInfo> list = null;
	@Override
	protected synchronized void getDatas() {
		mDatasList.clear();
		list = null;
		try {
			MeetingInfoUtil piu = new MeetingInfoUtil();
			list = piu.getMeetfoByUser(
					mPubTitleTxt == null ? "" : mPubTitleTxt.getText()
							.toString(),
					mRoom == null ? "" : mRoom.getText().toString(),
					(mSelectFromDate == null || mSelectFromDate.getText()
							.toString().contains("��ѡ��ʱ��")) ? null : sdfMore
							.parse(mSelectFromDate.getText().toString()),
					(mSelectToDate == null || mSelectToDate.getText()
							.toString().contains("��ѡ��ʱ��")) ? null : sdfMore
							.parse(mSelectToDate.getText().toString()),
					valid,type);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		for(MeetingInfo pi:list){
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("ItemSrc",R.drawable.list_title_log);
			map.put("ItemText",""+pi.mTitle);
			mDatasList.add(map);
		}
		mPullDatasSuc = true;
	}
	
	private synchronized void seteAdapter(){
		mAdapter = new SimpleAdapter(this, mDatasList, R.layout.list_item,
				new String[] { "ItemText" }, new int[] { R.id.item_text });
		mListView.setAdapter(mAdapter);
	}
}
