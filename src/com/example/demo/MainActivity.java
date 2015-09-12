package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private List<Integer> nowImgList, addImgList;
	private GridView nowGrid, addGrid;
	private GridAdapter adapter1;
	private DelGridAdapter adapter2;
	private TextView prompt;
	
	private int status = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		nowImgList = new ArrayList<Integer>();
		addImgList = new ArrayList<Integer>();
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
	
	private void initData(){
		nowImgList.add(R.drawable.manger_1_1);
		nowImgList.add(R.drawable.manger_1_2);
		nowImgList.add(R.drawable.manger_1_3);
		nowImgList.add(R.drawable.manger_1_4);
		nowImgList.add(R.drawable.manger_1_5);
		nowImgList.add(R.drawable.manger_1_6);
		nowImgList.add(R.drawable.ic_launcher);
		
		addImgList.add(R.drawable.manger_1_1);
		addImgList.add(R.drawable.manger_1_2);
		addImgList.add(R.drawable.manger_1_3);
		addImgList.add(R.drawable.manger_1_4);
		addImgList.add(R.drawable.manger_1_5);
		addImgList.add(R.drawable.manger_1_6);
		
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
				if (status == 0 ) {
					Toast.makeText(MainActivity.this, "" + position,
							Toast.LENGTH_SHORT).show();
				} else {
					addImgList.add(addImgList.size(),nowImgList.get(position));
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
			nowImgList.add(nowImgList.size()-1,addImgList.get(position));
			addImgList.remove(position);
			
			adapter1.notifyDataSetChanged();
			adapter2.notifyDataSetChanged();

		}
	}

}
