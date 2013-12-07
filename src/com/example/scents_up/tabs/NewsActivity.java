package com.example.scents_up.tabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


import com.example.scents_up.R;
import com.example.scents_up.service.SyncHttp;
import com.example.scents_up.util.DensityUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class NewsActivity extends Activity {

	private final int COLUMNWIDTHPX = 55;
	private final int FLINGVELOCITYPX = 800; // 滚动距离
	private final int NEWSCOUNT = 5; // 返回新闻数目
	private final int SUCCESS = 0;// 加载成功
	private final int NONEWS = 1;// 该栏目下没有新闻
	private final int NOMORENEWS = 2;// 该栏目下没有更多新闻
	private final int LOADERROR = 3;// 加载失败

	private int mColumnWidthDip;
	private int mFlingVelocityDip;
	private int mCid;
	private String mCatName;
	private ArrayList<HashMap<String, Object>> mNewsData;
	private ListView mNewsList;
	private SimpleAdapter mNewsListAdapter;
	private LayoutInflater mInflater;
	private Button mTitlebarRefresh;
	private ProgressBar mLoadnewsProgress;
	private Button mLoadMoreBtn;

	private LoadNewsAsyncTask loadNewsAsyncTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.news_main);

		mInflater = getLayoutInflater();
		mNewsData = new ArrayList<HashMap<String, Object>>();
		mNewsList = (ListView) findViewById(R.id.news_listview);
		mTitlebarRefresh = (Button) findViewById(R.id.titlebar_refresh);
		mLoadnewsProgress = (ProgressBar) findViewById(R.id.loadnews_progress);
		mTitlebarRefresh.setOnClickListener(loadMoreListener);

		// 把px转换成dip
		mColumnWidthDip = DensityUtil.px2dip(this, COLUMNWIDTHPX);
		mFlingVelocityDip = DensityUtil.px2dip(this, FLINGVELOCITYPX);
		// 默认选中的新闻分类
		mCid = 1;
		mCatName = "新闻";

		mNewsListAdapter = new SimpleAdapter(this, mNewsData,
				R.layout.newslist_item, new String[] { "newslist_item_title",
						"newslist_item_digest", "newslist_item_source",
						"newslist_item_time" }, new int[] {
						R.id.newslist_item_title, R.id.newslist_item_digest,
						R.id.newslist_item_source, R.id.newslist_item_time });
		View loadMoreLayout = mInflater.inflate(R.layout.loadmore, null);
		mNewsList.addFooterView(loadMoreLayout);
		mNewsList.setAdapter(mNewsListAdapter);
		mNewsList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(NewsActivity.this,
						NewsDetailsActivity.class);
				// 把需要的信息放到Intent中
				intent.putExtra("newsDate", mNewsData);
				intent.putExtra("position", position);
				intent.putExtra("categoryName", mCatName);
				startActivity(intent);
			}
		});

		mLoadMoreBtn = (Button) findViewById(R.id.loadmore_btn);
		mLoadMoreBtn.setOnClickListener(loadMoreListener);

		loadNewsAsyncTask = new LoadNewsAsyncTask();
		loadNewsAsyncTask.execute(mCid, 0, true);
	}

	/**
	 * 获取指定类型的新闻列表
	 * 
	 * @param cid
	 *            类型ID
	 * @param newsList
	 *            保存新闻信息的集合
	 * @param startnid
	 *            分页
	 * @param firstTimes
	 *            是否第一次加载
	 */
	private int getSpeCateNews(int cid, List<HashMap<String, Object>> newsList,
			int startid, Boolean firstTimes) {
		if (firstTimes) {
			// 如果是第一次，则清空集合里数据
			newsList.clear();
		}
		// 请求URL和字符串
		String url = "http://xiangchi.com:83/scentsProject/getAllNews";
		String params = "startid=" + startid + "&count=" + NEWSCOUNT + "&cid="
				+ cid;
		SyncHttp syncHttp = new SyncHttp();
		try {
			// 以Get方式请求，并获得返回结果
			String retStr = syncHttp.httpGet(url, params);

			JSONObject jsonObject = new JSONObject(retStr);
			// 获取返回码，0表示成功
			int retCode = jsonObject.getInt("ret");
			if (0 == retCode) {
				JSONObject dataObject = jsonObject.getJSONObject("data");
				// 获取返回数目
				int totalnum = dataObject.getInt("totalnum");
				if (totalnum > 0) {
					// 获取返回新闻集合
					JSONArray newslist = dataObject.getJSONArray("newslist");
					for (int i = 0; i < newslist.length(); i++) {
						JSONObject newsObject = (JSONObject) newslist.opt(i);
						HashMap<String, Object> hashMap = new HashMap<String, Object>();
						hashMap.put("id", newsObject.getInt("id"));
						hashMap.put("newslist_item_title",
								newsObject.getString("title"));
						hashMap.put("newslist_item_digest",
								newsObject.getString("digest"));
						hashMap.put("newslist_item_source",
								newsObject.getString("source"));
						hashMap.put("newslist_item_time",
								newsObject.getString("time"));
						newsList.add(hashMap);
					}
					return SUCCESS;
				} else {
					if (firstTimes) {
						return NONEWS;
					} else {
						return NOMORENEWS;
					}
				}
			} else {
				return LOADERROR;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return LOADERROR;
		}
	}

	private OnClickListener loadMoreListener = new OnClickListener() {

		public void onClick(View v) {
			loadNewsAsyncTask = new LoadNewsAsyncTask();
			switch (v.getId()) {
			case R.id.loadmore_btn:

				loadNewsAsyncTask.execute(mCid, mNewsData.size(), false);
				break;
			case R.id.titlebar_refresh:
				loadNewsAsyncTask.execute(mCid, 0, true);
				break;
			}

		}
	};

	private class LoadNewsAsyncTask extends AsyncTask<Object, Integer, Integer> {

		@Override
		protected void onPreExecute() {
			// 隐藏刷新按钮
			mTitlebarRefresh.setVisibility(View.GONE);
			// 显示进度条
			mLoadnewsProgress.setVisibility(View.VISIBLE);
			// 设置LoadMore Button 显示文本
			mLoadMoreBtn.setText(R.string.loadmore_txt);
		}

		@Override
		protected Integer doInBackground(Object... params) {
			return getSpeCateNews((Integer) params[0], mNewsData,
					(Integer) params[1], (Boolean) params[2]);
		}

		@Override
		protected void onPostExecute(Integer result) {
			// 根据返回值显示相关的Toast
			switch (result) {
			case NONEWS:
				Toast.makeText(NewsActivity.this, R.string.no_news,
						Toast.LENGTH_LONG).show();
				break;
			case NOMORENEWS:
				Toast.makeText(NewsActivity.this, R.string.no_more_news,
						Toast.LENGTH_LONG).show();
				break;
			case LOADERROR:
				Toast.makeText(NewsActivity.this, R.string.load_news_failure,
						Toast.LENGTH_LONG).show();
				break;
			}
			mNewsListAdapter.notifyDataSetChanged();
			// 显示刷新按钮
			mTitlebarRefresh.setVisibility(View.VISIBLE);
			// 隐藏进度条
			mLoadnewsProgress.setVisibility(View.GONE);
			// 设置LoadMore Button 显示文本
			mLoadMoreBtn.setText(R.string.loadmore_btn);
		}
	}
}