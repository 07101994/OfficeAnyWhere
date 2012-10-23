package com.office.anywher.offcial;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.Window;

public class BarChartActivity extends Activity {
	private static String mChartTitle="���Ĵ������α���";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(ChartFactory.getBarChartView(this, getBarDataset(), getBarRenderer(), Type.DEFAULT));
	}

	// ��״ͼ��Ⱦ��
	public XYMultipleSeriesRenderer getBarRenderer() {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		SimpleSeriesRenderer r = new SimpleSeriesRenderer();
		r.setColor(Color.BLUE);// ��ɫ��ɫ
		r.setDisplayChartValues(true);
		r.setChartValuesTextSize(15);
		renderer.addSeriesRenderer(r);
		renderer.setChartTitle("������ȷ��");// ���ñ���
		renderer.setXTitle("֪ʶ��");// x�����
		renderer.setYTitle("��ȷ��");// y�����
		renderer.setAxisTitleTextSize(18);
		renderer.setAxesColor(Color.YELLOW);
		renderer.setXAxisMin(0.5);// x����Сֵ
		renderer.setXAxisMax(5.5);
		renderer.setYAxisMin(0);// y����Сֵ
		renderer.setYAxisMax(100);
		renderer.setXLabelsAlign(Align.RIGHT);
		renderer.setMarginsColor(Color.BLACK);
		renderer.setYLabelsAlign(Align.RIGHT);
		renderer.setApplyBackgroundColor(true);
		renderer.setGridColor(Color.BLACK);
		renderer.setShowGrid(true);// ��ʾ����
		renderer.setFitLegend(true);// �������ʵ�λ��
		renderer.setBackgroundColor(Color.BLACK);
		renderer.setXLabels(0);
		
		renderer.addXTextLabel(1, "֪ʶ��һ");
		renderer.addXTextLabel(2, "֪ʶ���");
		renderer.addXTextLabel(3, "֪ʶ����");
		renderer.addXTextLabel(4, "֪ʶ����");
		renderer.setBarSpacing(2);
		return renderer;
	}

	// ��ͼ����
	private XYMultipleSeriesDataset getBarDataset() {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		CategorySeries series = new CategorySeries("������ϰ %"); // ����һ������ͼ
		// Ϊ����ͼ���ֵ
		series.add(50);
		series.add(80);
		series.add(44);
		series.add(32);
		dataset.addSeries(series.toXYSeries());// ��Ӹ�����ͼ�����������б�
		return dataset;
	}

}
