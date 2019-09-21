package com.lzf.education.ui.activity;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lzf.education.R;
import com.lzf.education.base.BaseActivity;
import com.lzf.education.ui.frame.DefaultFragment;
import com.lzf.education.ui.frame.HouseKeepingInspectionFragment;
import com.lzf.education.ui.frame.RoutineFragment;
import com.lzf.education.ui.view.MyActionBar;
import com.lzf.education.utils.DateUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class CheckActivity extends BaseActivity {

    @BindView(R.id.myActionBar)
    MyActionBar mMyActionBar;
    @BindView(R.id.tv_floor)
    TextView mTvFloor;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.tv_fraction)
    TextView mTvFraction;
    @BindView(R.id.tv_upload_num)
    TextView mTvUploadNum;
    @BindView(R.id.tv_camera_num)
    TextView mTvCameraNum;
    @BindView(R.id.tv_fraction_num)
    TextView mTvFractionNum;
    private String[] tabLayoutStrings=new String[]{"内务检查","大扫除","晨读","内务提醒","常规","妨碍检查"};

    Fragment mCurrentFragment;
    private final static String tag1="TAG1";
    private final static String tag2="TAG2";
    private final static String defaultTAG="defaultTAG";


    @Override
    protected int getLayout() {
        return R.layout.activity_check;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initView() {
        mMyActionBar.setName("常规检查");
        mTvFloor.setText("1号楼 101");
        mTvTime.setText(DateUtil.getCurrentTime());
        mTvFraction.setText("已扣8分");
        setTabLayout();
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        replaceFragment(tag1);
                        break;
                    case 1:
                    case 2:
                    case 3:
                    case 5:
                        replaceFragment(defaultTAG);
                        break;
                    case 4:
                        replaceFragment(tag2);
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
        replaceFragment(tag1);
    }

    private void setTabLayout(){
        for (String s:tabLayoutStrings){
            mTabLayout.addTab(mTabLayout.newTab().setText(s));
        }
    }

    @OnClick({R.id.tv_submit, R.id.iv_scan, R.id.iv_upload, R.id.iv_camera, R.id.iv_home})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                break;
            case R.id.iv_scan:
                break;
            case R.id.iv_upload:
                break;
            case R.id.iv_camera:
                break;
            case R.id.iv_home:
                break;
        }
    }

    private void replaceFragment(String tag){
        if (mCurrentFragment!=null){
            if (tag.equals(mCurrentFragment.getTag())){
                return;
            }else {
                getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag(mCurrentFragment.getTag())).commit();
            }
        }

        mCurrentFragment=getSupportFragmentManager().findFragmentByTag(tag);
        if (mCurrentFragment==null){
            switch (tag){
                case tag1:
                    mCurrentFragment=new RoutineFragment();
                    break;
                case tag2:
                    mCurrentFragment=new HouseKeepingInspectionFragment();
                    break;
                default:
                    mCurrentFragment=new DefaultFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().add(R.id.frame_check, mCurrentFragment, tag).commit();
        }else {
            getSupportFragmentManager().beginTransaction().show(mCurrentFragment).commit();
        }
    }
}
