package com.app.fastindex;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.app.fastindex.library.FastIndexView;

import java.util.ArrayList;
import java.util.Collections;

import IndexAdapter.IndexAdapter;
import bean.Person;
import util.Engine;


public class MainActivity extends AppCompatActivity {

    private FastIndexView mIndexView;
    private ListView mListView;
    private TextView mToast;
    private ArrayList<Person> mArrayLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.list_view_main);
        mToast = (TextView) findViewById(R.id.tv_toast);
        mIndexView = (FastIndexView) findViewById(R.id.fast_index);
        mIndexView.setListener(new FastIndexView.OnLetterUpdateListener() {
            @Override
            public void onLetterUpdate(String letter) {
                showLetter(letter);
                for (int i = 0; i < mArrayLists.size(); i++) {
                    Person p = mArrayLists.get(i);
                    String str = p.getPinyin().charAt(0) + "";
                    if (TextUtils.equals(str, letter)) {
                        mListView.setSelection(i);
                        break;
                    }
                }
            }
        });
        mArrayLists = new ArrayList<Person>();
        fillDataAndSort();
        mListView.setAdapter(new IndexAdapter(MainActivity.this, mArrayLists));
    }

    public Handler mhanlder = new Handler();
    
    private void showLetter(String letter) {
        mToast.setVisibility(View.VISIBLE);
        mToast.setText(letter);
        mhanlder.removeCallbacksAndMessages(null);
        mhanlder.postDelayed(new Runnable() {
            @Override
            public void run() {
                mToast.setVisibility(View.GONE);
            }
        }, 500);
    }

    private void fillDataAndSort() {
        for (int i = 0; i < Engine.TestStrings.length; i++) {
            Person person = new Person(Engine.TestStrings[i]);
            mArrayLists.add(person);
        }

        //sort
        Collections.sort(mArrayLists);
    }
}
