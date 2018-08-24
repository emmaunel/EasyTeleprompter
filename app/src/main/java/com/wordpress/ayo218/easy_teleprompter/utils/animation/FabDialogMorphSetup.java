package com.wordpress.ayo218.easy_teleprompter.utils.animation;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.transition.ArcMotion;
import android.view.View;
import android.view.animation.Interpolator;

/**
 * Helper class for setting up Fab <-> Dialog shared element transitions.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class FabDialogMorphSetup {

    public static final String EXTRA_SHARED_ELEMENT_START_COLOR =
            "EXTRA_SHARED_ELEMENT_START_COLOR";
    public static final String EXTRA_SHARED_ELEMENT_START_CORNER_RADIUS =
            "EXTRA_SHARED_ELEMENT_START_CORNER_RADIUS";

    private static Interpolator fastOutSlowIn;
    private static Interpolator fastOutLinearIn;

    private FabDialogMorphSetup() { }

    /**
     * Configure the shared element transitions for morphin from a fab <-> dialog. We need to do
     * this in code rather than declaratively as we need to supply the color to transition from/to
     * and the dialog corner radius which is dynamically supplied depending upon where this screen
     * is launched from.
     */
    public static void setupSharedElementTransitions(@NonNull Activity activity,
                                                     @Nullable View target,
                                                     int dialogCornerRadius){
        if (!activity.getIntent().hasExtra(EXTRA_SHARED_ELEMENT_START_COLOR)) return;

        int startCornerRadius = activity.getIntent().getIntExtra
                (EXTRA_SHARED_ELEMENT_START_CORNER_RADIUS, -1);

        ArcMotion arcMotion = new ArcMotion();
        arcMotion.setMinimumHorizontalAngle(50f);
        arcMotion.setMinimumVerticalAngle(50f);
        int color = activity.getIntent().
                getIntExtra(EXTRA_SHARED_ELEMENT_START_COLOR, Color.TRANSPARENT);
        Interpolator easeInOut = getFastOutSlowInInterpolator();
        MorphFabToDialog sharedEnter =
                new MorphFabToDialog(color, dialogCornerRadius, startCornerRadius);
        sharedEnter.setPathMotion(arcMotion);
        sharedEnter.setInterpolator(easeInOut);

        MorphDialogToFab sharedReturn =
                new MorphDialogToFab(color, startCornerRadius);
        sharedReturn.setPathMotion(arcMotion);
        sharedReturn.setInterpolator(easeInOut);
        if (target != null) {
            sharedEnter.addTarget(target);
            sharedReturn.addTarget(target);
        }

        activity.getWindow().setSharedElementEnterTransition(sharedEnter);
        activity.getWindow().setSharedElementReturnTransition(sharedEnter);

    }

    public static Interpolator getFastOutSlowInInterpolator(){
        if (fastOutSlowIn == null) {
            fastOutSlowIn = new FastOutSlowInInterpolator();
        }
        return fastOutSlowIn;
    }

    public static Interpolator getFastOutLinearInInterpolator() {
        if (fastOutLinearIn == null) {
            fastOutLinearIn = new FastOutLinearInInterpolator();
        }
        return fastOutLinearIn;
    }
}
