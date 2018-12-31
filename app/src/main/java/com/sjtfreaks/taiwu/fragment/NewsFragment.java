package com.sjtfreaks.taiwu.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.sjtfreaks.taiwu.R;
import com.sjtfreaks.taiwu.activity.WebActivity;
import com.sjtfreaks.taiwu.adapter.OneAdapter;
import com.sjtfreaks.taiwu.bean.OneData;
import com.sjtfreaks.taiwu.utils.L;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {
    private ListView mListView;
    private List<OneData> mList = new ArrayList<>();
    private List<String> mListTitle = new ArrayList<>();
    private List<String> mListUrl = new ArrayList<>();

    public static NewsFragment newInstance(String name){
        Bundle args = new Bundle();
        args.putString("name", name);
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news,null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.mListView);
        mList.clear();

        String url = "http://ic.snssdk.com/2/article/v25/stream/?count=20";
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                //Toast.makeText(getActivity(),t, Toast.LENGTH_SHORT).show();
                parsingJson(t);
                L.i("json:"+t);
            }
        });
        //点击
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                L.i("position:"+position);
                Intent intent = new Intent(getActivity(), WebActivity.class);
                //2 way chuan zhi BUNdle
                intent.putExtra("title",mListTitle.get(position));
                intent.putExtra("url",mListUrl.get(position));
                startActivity(intent);
            }
        });
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONArray jsonList = jsonObject.getJSONArray("data");

            for (int i = 0; i<jsonList.length();i++){
                JSONObject json = (JSONObject) jsonList.get(i);

                OneData data = new OneData();

                String url = json.getString("article_url");
                String title = json.getString("title");

                data.setTitle(json.getString("title"));
                data.setAbst(json.getString("abstract"));
                data.setDatetime(json.getString("behot_time"));
                data.setMedia_name(json.getString("source"));

                mList.add(data);
                mListTitle.add(title);
                mListUrl.add(url);
            }
            OneAdapter adapter = new OneAdapter(getActivity(),mList);
            mListView.setAdapter(adapter);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }


}
