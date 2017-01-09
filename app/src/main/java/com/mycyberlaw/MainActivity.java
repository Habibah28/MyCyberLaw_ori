package com.mycyberlaw;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mycyberlaw.menu_manager.CopyrightFragment;
import com.mycyberlaw.menu_manager.HomeFragment;
import com.mycyberlaw.menu_manager.LawsFragment;
import com.mycyberlaw.menu_manager.LecturerFragment;
import com.mycyberlaw.menu_manager.OutlineFragment;
import com.mycyberlaw.menu_manager.PDPAFragment;
import com.mycyberlaw.menu_manager.StudentsFragment;

public class MainActivity extends ActionBarActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // view layout home here
        Fragment fragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            startActivity(new Intent(this, AboutMenu.class));
            return true;
        }

        if (id == R.id.action_register) {
            startActivity(new Intent(this, RegisterMenu.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Fragment fragment = null;

        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the home action
            fragment = new HomeFragment();

        } else if (id == R.id.nav_outline) {
            fragment = new OutlineFragment();
            getSupportActionBar().setTitle("Course Outline");

        } else if (id == R.id.nav_PDPA) {
            fragment = new PDPAFragment();
            getSupportActionBar().setTitle("PDPA 2010");

        } else if (id == R.id.nav_copyright) {
            fragment = new CopyrightFragment();
            getSupportActionBar().setTitle("Copyright Act 2012");

        } else if (id == R.id.nav_laws) {
            fragment = new LawsFragment();
            getSupportActionBar().setTitle("List of Laws");

        } else if (id == R.id.nav_students) {
            fragment = new StudentsFragment();
            getSupportActionBar().setTitle("Student");
        } else if (id == R.id.nav_lecturers) {
            fragment = new LecturerFragment();
            getSupportActionBar().setTitle("Lecturer");
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Config.RESULT_CODE && requestCode == Config.REQUEST_CODE) {

            Fragment frg = getSupportFragmentManager().findFragmentById(R.id.nav_students);
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container_body, new StudentsFragment());
            ft.detach(frg);
            ft.attach(frg);
            ft.commit();

        }
    }
}
