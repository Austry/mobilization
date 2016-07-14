package com.austry.mobilization.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.austry.mobilization.R;
import com.austry.mobilization.fragments.AboutFragment;
import com.austry.mobilization.fragments.AllArtistsFragment;

public class MainActivity extends AppCompatActivity {

    private String LOG_TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            showFragment(new AllArtistsFragment());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemAbout:
                showFragment(new AboutFragment());
                break;
            case R.id.menuItemFeedback:
                sendFeedbackEmailIntent();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendFeedbackEmailIntent() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", getString(R.string.feedback_email), null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.feedback_email_subject));
        emailIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.feedback_email_body));
        startActivity(Intent.createChooser(emailIntent, getString(R.string.feedback_chooser_title)));
    }

    private void showFragment(Fragment fragment) {

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.flFragmentContainer);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if(currentFragment == null || !currentFragment.getClass().equals(fragment.getClass())) {
            ft.addToBackStack(fragment.getClass().getName())
                    .replace(R.id.flFragmentContainer, fragment, fragment.getClass().getName());
        }
        ft.commit();

    }
}
