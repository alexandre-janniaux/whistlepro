package fr.enst.pact34.whistlepro.app.activities.v2;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import fr.enst.pact34.whistlepro.app.R;
import fr.enst.pact34.whistlepro.app.fragments.QuickRecorderFragment;

public class StarterActivity extends FragmentActivity {

    private StarterPageAdapter starterPageAdapter;
    private ViewPager viewPager;

    class StarterPageAdapter extends FragmentPagerAdapter {

        QuickRecorderFragment quickRecorderFragment;

        public StarterPageAdapter(FragmentManager fm) {
            super(fm);
            quickRecorderFragment = new QuickRecorderFragment();
        }

        @Override
        public Fragment getItem(int position) {
            return this.quickRecorderFragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_starter);

        this.starterPageAdapter = new StarterPageAdapter(getSupportFragmentManager());
        this.viewPager = (ViewPager) findViewById(R.id.pager);
        this.viewPager.setAdapter(this.starterPageAdapter);

        final ActionBar actionBar = getActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle(R.string.app_name);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    }
}