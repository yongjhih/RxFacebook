package rx.facebook.app;

import java.util.Locale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import butterknife.InjectView;
import butterknife.ButterKnife;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.sromku.simple.fb.SimpleFacebook;

import rx.facebook.FacebookObservable;

import rx.android.app.*;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.pager)
    ViewPager mViewPager;
    @InjectView(R.id.tab)
    SmartTabLayout mTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        mSimpleFacebook = SimpleFacebook.getInstance(this);

        if (!mSimpleFacebook.isLogin()) {
            AppObservable.bindActivity(this, FacebookObservable.login(this))
                .subscribe(simpleFacebook -> {
                    android.util.Log.d("RxFacebook", "" + simpleFacebook);
                });
        }

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("Photos", MainFragment.class)
                .create());

        mViewPager.setAdapter(adapter);

        mTab.setViewPager(mViewPager);
        //mTab.setOnPageChangeListener(mPageChangeListener);
    }

    private SimpleFacebook mSimpleFacebook;

    @Override
    protected void onResume() {
        super.onResume();
        mSimpleFacebook = SimpleFacebook.getInstance(this);
    }

    public SimpleFacebook getSimpleFacebook() {
        if (mSimpleFacebook == null) {
            mSimpleFacebook = SimpleFacebook.getInstance(this);
        }
        return mSimpleFacebook;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mSimpleFacebook != null) {
            mSimpleFacebook.onActivityResult(this, requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
