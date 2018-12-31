package com.sjtfreaks.taiwu.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.sjtfreaks.taiwu.R;
import com.sjtfreaks.taiwu.activity.WebActivity;
import com.sjtfreaks.taiwu.adapter.AttAdapter;
import com.sjtfreaks.taiwu.bean.AttData;
import com.sjtfreaks.taiwu.utils.L;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttFragment extends Fragment {
    private ListView mListView;
    private List<AttData> mList = new ArrayList<>();
    private List<String> mListTitle = new ArrayList<>();
    private List<String> mListUrl = new ArrayList<>();

    public static AttFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putString("name", name);
        AttFragment fragment = new AttFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_att, null);
        initView(view);
        return view;
}


    private void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.mListView);
        mList.clear();
        String abc = getJson("game.json", getActivity());
        parsingJson(abc);
        //Toast.makeText(getActivity(),abc, Toast.LENGTH_SHORT).show();
        L.i("json:" + abc);
        //点击
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                L.i("position:" + position);
                Intent intent = new Intent(getActivity(), WebActivity.class);
                //2 way chuan zhi BUNdle
                intent.putExtra("title", mListTitle.get(position));
                intent.putExtra("url", mListUrl.get(position));
                startActivity(intent);
            }
        });
    }


    //将传入的is一行一行解析读取出来出来
    public static String getJson(String fileName, Context context){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream is = context.getAssets().open(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line=bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    private void parsingJson(String abc) {
        try {
            JSONObject jsonObject = new JSONObject(abc);
            JSONArray jsonList = jsonObject.getJSONArray("dates");

            for (int i = 0; i<jsonList.length();i++){
                JSONObject json = (JSONObject) jsonList.get(i);

                AttData data = new AttData();

                String url = json.getString("url");
                String title = json.getString("title");

                data.setTitle(json.getString("title"));
                data.setImages(json.getString("images"));
                data.setDesc(json.getString("desc"));

                mList.add(data);
                mListTitle.add(title);
                mListUrl.add(url);
            }
            AttAdapter adapter = new AttAdapter(getActivity(),mList);
            mListView.setAdapter(adapter);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
