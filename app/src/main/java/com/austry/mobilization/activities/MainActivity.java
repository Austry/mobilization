package com.austry.mobilization.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.austry.mobilization.R;
import com.austry.mobilization.fragments.AboutFragment;
import com.austry.mobilization.fragments.AllArtistsFragment;
import com.austry.mobilization.receivers.HeadsetReceiver;

public class MainActivity extends AppCompatActivity {

    private final HeadsetReceiver headsetReceiver = new HeadsetReceiver();
    private String LOG_TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            addAllArtistsFragment();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(headsetReceiver, new IntentFilter(Intent.ACTION_HEADSET_PLUG));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(headsetReceiver);

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
                showAboutFragment();
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

    private void showAboutFragment() {
        Fragment fragment = new AboutFragment();
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.flFragmentContainer);
        if (currentFragment == null || !currentFragment.getClass().equals(fragment.getClass())) {
            getSupportFragmentManager().beginTransaction()
                    .addToBackStack(fragment.getClass().getName())
                    .replace(R.id.flFragmentContainer, fragment, fragment.getClass().getName())
                    .commit();
        }
    }

    private void addAllArtistsFragment() {
        Fragment fragment = new AllArtistsFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.flFragmentContainer, fragment, fragment.getClass().getName())
                .commit();
    }
}
