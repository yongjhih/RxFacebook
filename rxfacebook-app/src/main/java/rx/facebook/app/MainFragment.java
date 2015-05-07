package rx.facebook.app;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
//import android.support.v4.app.NavUtils;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.StaggeredGridLayoutManager;
//import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Handler;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.*;

import rx.android.app.*;

import rx.facebook.*;

import android.support.v4.widget.SwipeRefreshLayout;
import com.github.florent37.materialviewpager.*;

public class MainFragment extends Fragment {

    @InjectView(R.id.list)
    RecyclerView listView;
    @InjectView(R.id.loading)
    SwipeRefreshLayout loading;

    private Handler handler;
    private SwipeRefreshLayout.OnRefreshListener refresher;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static MainFragment newInstance(int sectionNumber) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
        handler = new Handler();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        android.util.Log.d("RxFacebook", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        android.util.Log.d("RxFacebook", "getPhotos {");
        AppObservable.bindFragment(this, FacebookObservable.getPhotos(getActivity()))
            .subscribe(p -> {
                android.util.Log.d("RxFacebook", "user: " + p.getFrom().getName());
                android.util.Log.d("RxFacebook", "link: " + p.getLink());
            }, e -> {
                android.util.Log.d("RxFacebook", "getPhotos: e: " + e);
            }, () -> {
                android.util.Log.d("RxFacebook", "getPhotos: onCompleted");
            });
        android.util.Log.d("RxFacebook", "getPhotos }");

        /*
        FacebookObservable.getPosts(getActivity()).subscribe(p -> {
            android.util.Log.d("RxFacebook", "name: " + p.getFrom().getName());
            android.util.Log.d("RxFacebook", "message: " + p.getMessage()));
        });
        */

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), listView, null);
    }
}
