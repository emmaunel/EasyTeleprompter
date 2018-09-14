package com.wordpress.ayo218.easy_teleprompter.ui.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wordpress.ayo218.easy_teleprompter.R;

import top.defaults.camera.CameraView;
import top.defaults.camera.Photographer;
import top.defaults.camera.PhotographerFactory;
import top.defaults.camera.PhotographerHelper;


public class CameraFragment extends Fragment {

    public static CameraFragment newInstance() {
        return new CameraFragment();
    }

    Photographer photographer;
    PhotographerHelper photographerHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.camera_record_fragment, container, false);
        CameraView cameraView = view.findViewById(R.id.preview);
        photographer = PhotographerFactory.createPhotographerWithCamera2(getActivity(), cameraView);
        photographerHelper = new PhotographerHelper(photographer);
        cameraView.setFillSpace(true);
//        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        enterFullscreen();
        photographer.startPreview();
    }

    @Override
    public void onPause() {
        photographer.stopPreview();
        super.onPause();
    }

    private void enterFullscreen() {
        View decorView = getActivity().getWindow().getDecorView();
        decorView.setBackgroundColor(Color.BLACK);
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }
}
