package com.wordpress.ayo218.easy_teleprompter.utils.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.wordpress.ayo218.easy_teleprompter.R;
import com.wordpress.ayo218.easy_teleprompter.database.AppDatabase;
import com.wordpress.ayo218.easy_teleprompter.database.ViewModel.ScriptViewModel;
import com.wordpress.ayo218.easy_teleprompter.models.Scripts;

import java.util.List;

public class WidgetRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = "WidgetRemoteViewFactory";
    private Context context;
    private List<Scripts> scripts;
    private int mAppWidgetId;

    public WidgetRemoteViewFactory(Context context, Intent intent){
        this.context = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

    }

    @Override
    public void onCreate() {
        AppDatabase database = AppDatabase.getsInstance(context);
        scripts = database.scriptDao().loadScripts();
        Log.e(TAG, "Widget Here " + scripts.get(0).getTitle());
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {
        scripts.clear();
    }

    @Override
    public int getCount() {
        return scripts.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget_list_item);
        views.setTextViewText(R.id.script_widget_txt, scripts.get(i).getTitle());
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
