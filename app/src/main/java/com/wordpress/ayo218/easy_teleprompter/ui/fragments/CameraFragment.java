package com.wordpress.ayo218.easy_teleprompter.ui.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wordpress.ayo218.easy_teleprompter.R;
import com.wordpress.ayo218.easy_teleprompter.ui.fragments.template.BaseFragment;

import top.defaults.camera.CameraView;
import top.defaults.camera.Error;
import top.defaults.camera.Photographer;
import top.defaults.camera.PhotographerFactory;
import top.defaults.camera.PhotographerHelper;


public class CameraFragment extends BaseFragment {

    public static CameraFragment newInstance() {
        return new CameraFragment();
    }

    Photographer photographer;
    PhotographerHelper photographerHelper;
    private boolean isRecordingVideo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.camera_record_fragment, container, false);
        CameraView cameraView = view.findViewById(R.id.preview);
        photographer = PhotographerFactory.createPhotographerWithCamera2(getActivity(), cameraView);
        photographerHelper = new PhotographerHelper(photographer);
        cameraView.setFillSpace(true);
//        ButterKnife.bind(this, view);
        photographer.setOnEventListener(new Photographer.OnEventListener() {
            @Override
            public void onDeviceConfigured() {

            }

            @Override
            public void onPreviewStarted() {

            }

            @Override
            public void onZoomChanged(float zoom) {

            }

            @Override
            public void onPreviewStopped() {

            }

            @Override
            public void onStartRecording() {

            }

            @Override
            public void onFinishRecording(String filePath) {
                announceNewFile(filePath);
            }

            @Override
            public void onShotFinished(String filePath) {

            }

            @Override
            public void onError(Error error) {

            }
        });

        if (isRecordingVideo){
            finishRecording();
        } else {
            isRecordingVideo = true;
            photographer.startRecording(null);
        }

        return view;
    }

    private void announceNewFile(String filePath) {
        Toast.makeText(getContext(), "File: " + filePath, Toast.LENGTH_LONG).show();

    }

    private void finishRecording() {
        if (isRecordingVideo){
            isRecordingVideo = false;
            photographer.finishRecording();
        }
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
