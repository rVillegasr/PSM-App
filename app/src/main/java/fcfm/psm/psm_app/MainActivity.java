package fcfm.psm.psm_app;


import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import fcfm.psm.psm_app.Adapter.FragmentAdapter;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        // Nuestro adaptador de fragmentos
        FragmentAdapter adapter = new FragmentAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        // Asignamos viewpager a este tabLayout
        tabLayout.setupWithViewPager(viewPager);
    }

    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if(exit) {
            finish();
        }else{
            showToast("Tap again to exit");
            exit = true;
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run(){
                    exit = false;
                }
            }, 3 * 1000);
        }
    }
    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
