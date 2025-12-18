package com.example.hostelaid;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * Periodic background worker that pulls remote data and merges into local cache.
 */
public class SyncWorker extends Worker {

    public SyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        SyncManager manager = new SyncManager(getApplicationContext());
        boolean ok = manager.syncAllBlocking();
        return ok ? Result.success() : Result.retry();
    }
}

