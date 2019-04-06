package com.wordpress.ayo218.easy_teleprompter.ui.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wordpress.ayo218.easy_teleprompter.R;
import com.wordpress.ayo218.easy_teleprompter.ui.fragments.ScriptFragment;
import com.wordpress.ayo218.easy_teleprompter.utils.Constants;
import com.wordpress.ayo218.easy_teleprompter.utils.animation.FabDialogMorphSetup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private static final int EDIT_CODE = 100;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        initFragment();

        navigationView.setNavigationItemSelectedListener(this);

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
//                        Toast.makeText(this, R.string.granted, Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "File Directory: " + Constants.MEDIA_DIR);
                    } else {
                        Toast.makeText(this, R.string.not_granted, Toast.LENGTH_SHORT).show();
                    }
                });

        new NetworkAsync().execute();
    }


    /**
     * Initialize script fragment which will have the list of the
     * scripts
     */
    private void initFragment() {
        ScriptFragment fragment = new ScriptFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment, fragment);
        transaction.commit();
    }

    /**
     * Rather than showing the dialog quick, I created a fancy animation
     * than shows how the fab button translate from a fab to a dialog
     */
    @SuppressLint({"NewApi", "RestrictedApi"})
    @OnClick(R.id.fab)
    protected void fabClick(){
        Intent intent = new Intent(this, ScriptEditorDialog.class);
        intent.putExtra(FabDialogMorphSetup.EXTRA_SHARED_ELEMENT_START_COLOR,
                ContextCompat.getColor(this, R.color.colorAccent));
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, fab, getString(R.string.transition_script_edit_dialog));
        startActivityForResult(intent, EDIT_CODE, options.toBundle());
    }

    /**
     * if the drawer is open, close it or else exit the app
     */
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Inflate the main menu and add items to the action bar it is present.
     * @param menu main menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Responsible for listening for clicks on the main menu.
     * And based on the item, it does something different. Obviously
     * @param item menu items
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Respnsible for listening for clicks on the drawer menu.
     * As you can see, not yet done.
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_settings){
            startActivity(new Intent(this, SettingsActivity.class));
        }
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Required by Udacity. But when you open the app, I imedidaltly check
     * if there's internet connection so that I back up the user's data to
     * firebase.
     */
    @SuppressLint("StaticFieldLeak")
    private class NetworkAsync extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connectivityManager != null;
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            Log.e(TAG, "doInBackground: Here Mother flower");
            return info != null && info.isConnected();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean){
                Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(MainActivity.this, "Not connected", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
