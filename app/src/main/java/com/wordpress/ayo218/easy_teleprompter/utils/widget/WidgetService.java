package com.wordpress.ayo218.easy_teleprompter.utils.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetRemoteViewFactory(this.getApplicationContext(), intent);
    }
}
