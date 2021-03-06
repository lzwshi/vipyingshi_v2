package com.movtalent.app.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antiless.support.widget.TabLayout;
import com.media.playerlib.model.DataInter;
import com.movtalent.app.R;
import com.movtalent.app.adapter.OnlineTabFragmentPagerAdapter;
import com.movtalent.app.model.vo.ItemTab;
import com.movtalent.app.model.vo.VideoTypeVo;
import com.movtalent.app.view.list.MovListFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author huangyong
 * createTime 2019/6/23
 */
public class MovieRootFragment extends Fragment {


    @BindView(R.id.sec_tablayout)
    TabLayout secTablayout;
    @BindView(R.id.sec_tab_vp)
    ViewPager secTabVp;
    Unbinder unbinder;
    private ArrayList<MovListFragment> movListFragments;
    private OnlineTabFragmentPagerAdapter pagerAdpter;

    public static MovieRootFragment newInstance(ArrayList<VideoTypeVo.ClassBean> itemTabs) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DataInter.Key.TYPE_ID_ARRAY, itemTabs);
        MovieRootFragment movieRootFragment = new MovieRootFragment();
        movieRootFragment.setArguments(bundle);
        return movieRootFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.tab_second_root_layout, null);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {

        ArrayList<VideoTypeVo.ClassBean> typeIdArray = (ArrayList<VideoTypeVo.ClassBean>) getArguments().getSerializable(DataInter.Key.TYPE_ID_ARRAY);
        if (typeIdArray ==null|| typeIdArray.size()==0){
            return;
        }

        movListFragments = new ArrayList<>();

        for (int i = 0; i < typeIdArray.size(); i++) {
            MovListFragment listFragment = MovListFragment.newInstance(typeIdArray.get(i).getType_id());
            movListFragments.add(listFragment);
        }

        pagerAdpter = new OnlineTabFragmentPagerAdapter(getChildFragmentManager(), movListFragments, typeIdArray);
        secTabVp.setAdapter(pagerAdpter);
        secTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        secTablayout.setupWithViewPager(secTabVp);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
