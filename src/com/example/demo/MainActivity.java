package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.LableBean;
import com.example.nethelp.NetHelp;

public class MainActivity extends Activity {
	private List<LableBean> nowImgList, addImgList;
	private GridView nowGrid, addGrid;
	private GridAdapter adapter1;
	private DelGridAdapter adapter2;
	private TextView prompt;

	private int status = 0;
	private SharedPreferences sharedPreferences;
	private int[] imgResID = { R.drawable.manger_1_1, R.drawable.manger_1_2,
			R.drawable.manger_1_3, R.drawable.manger_1_4,
			R.drawable.manger_1_5, R.drawable.manger_1_6,
			R.drawable.manger_1_1, R.drawable.manger_1_2,
			R.drawable.manger_1_3, R.drawable.manger_1_4,
			R.drawable.manger_1_5, R.drawable.manger_1_6 };

	private Map<String,String> lableInfoMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		nowImgList = new ArrayList<LableBean>();
		addImgList = new ArrayList<LableBean>();
		initData();

		prompt = (TextView) findViewById(R.id.prompt_info);

		nowGrid = (GridView) findViewById(R.id.now_grid);
		adapter1 = new GridAdapter(this, nowImgList);
		nowGrid.setAdapter(adapter1);
		nowGrid.setOnItemClickListener(new OnItemClickListenerImpl());

		addGrid = (GridView) findViewById(R.id.add_grid);
		adapter2 = new DelGridAdapter(this, addImgList);
		addGrid.setAdapter(adapter2);
		addGrid.setOnItemClickListener(new OnItemClickListenerImpl2());
	}

	private void initData() {

		sharedPreferences = getSharedPreferences("lableManger", MODE_PRIVATE);
		String strData = sharedPreferences.getString("name", "");

		LableBean lableBean;
		//用于记录nowImgList中标签的序号，便于确定addImgList中标签的序号
		List<Integer> listNum = new ArrayList<Integer>(); 
		
		if ("".equals(strData) || strData == null) {
			for (int i = 0; i < 6; i++) {
				lableBean = new LableBean();
				lableBean.setId(i);
				lableBean.setImgID(imgResID[i]);
				nowImgList.add(lableBean);
			}
			nowImgList.add(new LableBean(-1, R.drawable.ic_launcher));
		} else {
			String[] data = strData.split(",");
			for (String strIndex : data) {
				if ("-1".equals(strIndex)) {
					break;
				}
				int index = Integer.parseInt(strIndex);
				listNum.add(index);
				lableBean = new LableBean();
				lableBean.setId(index);
				lableBean.setImgID(imgResID[index]);
				nowImgList.add(lableBean);
			}
			nowImgList.add(new LableBean(-1, R.drawable.ic_launcher));
		}

		for (int i = 0; i < 12; i++) {
			if (!listNum.contains(i)) {
				lableBean = new LableBean();
				lableBean.setId(i);
				lableBean.setImgID(imgResID[i]);
				addImgList.add(lableBean);
			}
		}

	}

	class OnItemClickListenerImpl implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (position == nowImgList.size() - 1) {
				if (prompt.getVisibility() == View.VISIBLE) {
					prompt.setVisibility(View.GONE);
					addGrid.setVisibility(View.GONE);
					status = 0;
				} else {
					prompt.setVisibility(View.VISIBLE);
					addGrid.setVisibility(View.VISIBLE);
					status = 1;
				}

			} else {
				if (status == 0) {
					Toast.makeText(MainActivity.this, "" + position,
							Toast.LENGTH_SHORT).show();
				} else {
					addImgList.add(addImgList.size(), nowImgList.get(position));
					nowImgList.remove(position);

					adapter1.notifyDataSetChanged();
					adapter2.notifyDataSetChanged();
				}

			}
		}
	}

	class OnItemClickListenerImpl2 implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			nowImgList.add(nowImgList.size() - 1, addImgList.get(position));
			addImgList.remove(position);

			adapter1.notifyDataSetChanged();
			adapter2.notifyDataSetChanged();

		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		sharedPreferences = getSharedPreferences("lableManger", MODE_PRIVATE);
		StringBuilder stringBuilder = new StringBuilder();
		for (LableBean bean : nowImgList) {
			stringBuilder.append(bean.getId() + ",");
		}
		sharedPreferences.edit().putString("name", stringBuilder.toString())
				.commit();
		lableInfoMap = new HashMap<String, String>();
		lableInfoMap.put("name", "name");
		lableInfoMap.put("lable", stringBuilder.toString().replaceAll(",", "%2C"));
		NetHelp.uploadLableInfo(lableInfoMap);
	}

}
