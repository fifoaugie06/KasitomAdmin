package t.com.kasitomadmin.ui.uddata;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import t.com.kasitomadmin.R;
import t.com.kasitomadmin.ui.adapter.SectionPagerAdapter;
import t.com.kasitomadmin.ui.uddata.antonimud.AntonimUDFragment;
import t.com.kasitomadmin.ui.uddata.globalchat.GlobalChatUDFragment;
import t.com.kasitomadmin.ui.uddata.quizud.QuizUDFragment;
import t.com.kasitomadmin.ui.uddata.sinonimud.SinonimUDFragment;


public class UpdateDeleteFragment extends Fragment {
    private View myFragment;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    public UpdateDeleteFragment() {
        // Required empty public constructor
    }

    public static UpdateDeleteFragment getInstance() {
        return new UpdateDeleteFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        myFragment = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager = myFragment.findViewById(R.id.viewPager);
        tabLayout = myFragment.findViewById(R.id.tabLayout);

        return myFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpViewPager(ViewPager viewPager) {
        SectionPagerAdapter adapter = new SectionPagerAdapter(getChildFragmentManager());

        adapter.addFragment(new AntonimUDFragment(), "Antonim");
        adapter.addFragment(new SinonimUDFragment(), "Sinonim");
        adapter.addFragment(new GlobalChatUDFragment(), "GChat");
        adapter.addFragment(new QuizUDFragment(), "Quiz");

        viewPager.setAdapter(adapter);
    }
}