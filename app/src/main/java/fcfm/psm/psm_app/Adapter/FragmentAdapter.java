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
            case 0:
                EventListActivity fragmentEventHeader = new EventListActivity();
                Bundle args = new Bundle();
                args.putBoolean("useCustomLayout", true);
                args.putString("asdasd", "asdfasdf");

                fragmentEventHeader.setArguments(args);
                return fragmentEventHeader;
            case 1:
                EventListActivity fragmentEventHeader2 = new EventListActivity();
                Bundle args2 = new Bundle();
                args2.putBoolean("useCustomLayout", true);
                args2.putString("asdasd", "asdfasdf");

                fragmentEventHeader2.setArguments(args2);
                return fragmentEventHeader2;
            default:
                return null;


        }
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "PROXIMOS EVENTOS";
            case 1:
                return "TODOS LOS EVENTOS";
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
