package com.lzf.education;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.lzf.education.base.BaseActivity;
import com.lzf.education.model.UploadModel;
import com.lzf.education.ui.activity.CheckActivity;
import com.lzf.education.ui.adpter.UploadAdapter;
import com.lzf.education.ui.view.MyActionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.myActionBar)
    MyActionBar mMyActionBar;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.rv_file)
    RecyclerView mRvFile;
    private UploadAdapter mAdapter;
    private List<UploadModel> mUploadModelList=new ArrayList<>();

    protected int getLayout() {
        return R.layout.activity_main;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initView() {
        mMyActionBar = findViewById(R.id.myActionBar);
        mMyActionBar.setName("上传文件")
                .setRightText("编辑");
        mMyActionBar.setOnClickBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, CheckActivity.class);
                startActivity(intent);
            }
        });
        mMyActionBar.setOnClickRight(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"编辑",Toast.LENGTH_LONG).show();
            }
        });
        GridLayoutManager manager=new GridLayoutManager(this,1);
        mRvFile.setLayoutManager(manager);
        setUploadModelList();
        mAdapter=new UploadAdapter(mUploadModelList);
        mRvFile.setAdapter(mAdapter);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        setUploadModelList();
                        mAdapter.notifyDataSetChanged();
                        break;
                    default:
                        mUploadModelList.clear();
                        mAdapter.notifyDataSetChanged();
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUploadModelList(){
        mUploadModelList.clear();
        for (int i=0;i<50;i++){
            UploadModel uploadModel=new UploadModel();
            uploadModel.setId(i+1);
            uploadModel.setText1("101-(18三维2)");
            uploadModel.setText2("内务检查(晚上-扣1分)");
            uploadModel.setText3("地面有垃圾");
            uploadModel.setText4("类型：寝室纪律 周次：27");
            mUploadModelList.add(uploadModel);
        }
    }


    @OnClick(R.id.btn_upload)
    public void onViewClicked() {
        Toast.makeText(this,"开始上传",Toast.LENGTH_LONG).show();
    }
}
