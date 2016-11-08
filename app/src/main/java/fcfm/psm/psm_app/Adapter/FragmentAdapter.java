package fcfm.psm.psm_app.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import fcfm.psm.psm_app.EventListActivity;
import fcfm.psm.psm_app.R;

/**
 * Created by RVR_ on 25/09/2016.
 */
public class FragmentAdapter extends FragmentPagerAdapter{
    Context mContext;

    public FragmentAdapter(Context mContext, FragmentManager fm) {
        super(fm);
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: {
                EventListActivity fragmentEventHeader = new EventListActivity();
                Bundle args = new Bundle();
                args.putBoolean("all", false);

                fragmentEventHeader.setArguments(args);
                return fragmentEventHeader;
            }
            case 1: {
                EventListActivity fragmentEventHeader = new EventListActivity();
                Bundle args = new Bundle();
                args.putBoolean("all", true);

                fragmentEventHeader.setArguments(args);
                return fragmentEventHeader;
            }
            case 2: {
                EventListActivity fragmentEventHeader = new EventListActivity();
                Bundle args = new Bundle();
                args.putBoolean("all", true);

                fragmentEventHeader.setArguments(args);
                return fragmentEventHeader;
            }
            case 3: {
                EventListActivity fragmentEventHeader = new EventListActivity();
                Bundle args = new Bundle();
                args.putBoolean("all", true);

                fragmentEventHeader.setArguments(args);
                return fragmentEventHeader;
            }
            case 4: {
                EventListActivity fragmentEventHeader = new EventListActivity();
                Bundle args = new Bundle();
                args.putBoolean("all", true);

                fragmentEventHeader.setArguments(args);
                return fragmentEventHeader;
            }
            case 5: {
                EventListActivity fragmentEventHeader = new EventListActivity();
                Bundle args = new Bundle();
                args.putBoolean("all", true);

                fragmentEventHeader.setArguments(args);
                return fragmentEventHeader;
            }
            case 6: {
                EventListActivity fragmentEventHeader = new EventListActivity();
                Bundle args = new Bundle();
                args.putBoolean("all", true);

                fragmentEventHeader.setArguments(args);
                return fragmentEventHeader;
            }
            default:
                return null;


        }
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.tab_upcoming_events);
            case 1:
                return mContext.getString(R.string.tab_all_events);
            case 2:
                return mContext.getString(R.string.tab_art_and_culture);
            case 3:
                return mContext.getString(R.string.tab_music);
            case 4:
                return mContext.getString(R.string.tab_science_and_tecnology);
            case 5:
                return mContext.getString(R.string.tab_sports);
            case 6:
                return mContext.getString(R.string.tab_theater);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 7;
    }
}
