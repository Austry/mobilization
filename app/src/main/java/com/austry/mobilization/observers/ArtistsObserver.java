package com.austry.mobilization.observers;

import android.database.ContentObserver;
import android.os.Handler;

import com.austry.mobilization.model.Artist;

public class ArtistsObserver extends ContentObserver {


    private final ArtistsChangedCallback callback;

    public ArtistsObserver(Handler handler, ArtistsChangedCallback callback) {
        super(handler);
        this.callback = callback;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        callback.notifyChanges();

    }

    public interface ArtistsChangedCallback {
        void notifyChanges();
    }
}
