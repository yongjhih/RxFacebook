package rx.facebook.app;

import java.util.Locale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import butterknife.InjectView;
import butterknife.ButterKnife;

import com.sromku.simple.fb.SimpleFacebook;
import rx.facebook.FacebookObservable;

import rx.android.app.*;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.pager)
    ViewPager mViewPager;

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

        mViewPager.setAdapter(new SimpleFragmentPagerAdapter(getSupportFragmentManager()));
    }

    public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

        public SimpleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return MainFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
            }
            return null;
        }
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
