package com.boha.malengagolf.library.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.boha.malengagolf.library.R;
import com.boha.malengagolf.library.util.LeaderBoardPage;
import com.boha.malengagolf.library.util.MGPageFragment;
import com.boha.malengagolf.library.util.SharedUtil;

/**
 * Created by aubreyM on 2014/04/23.
 */
public class SplashFragment extends Fragment implements MGPageFragment, LeaderBoardPage {


    @Override
    public void onAttach(Activity a) {
        Log.i("SplashFragment",
                "onAttach ---- Fragment called and hosted by "
                        + a.getLocalClassName()
        );
        super.onAttach(a);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saved) {
        ctx = getActivity();
        inflater = getActivity().getLayoutInflater();
        view = inflater
                .inflate(R.layout.splasher, container, false);

        setFields();
        return view;
    }

    public void setLoading(boolean isOn) {

    }

    @Override
    public void onResume() {
        setFields();
        bar.setVisibility(View.GONE);
        super.onResume();
    }

    @SuppressWarnings("deprecation")
    public void setFields() {
        background = view.findViewById(R.id.SPLASH_background);
        txtLoading = (TextView) view.findViewById(R.id.SPLASH_txtLoading);
        try {
            background.setBackgroundDrawable(SharedUtil.getRandomSplash(ctx));
        } catch (OutOfMemoryError e) {
            Log.e("Splash", "-----------------------> OutOfMemoryError");
        }
        bar = (ProgressBar) view.findViewById(R.id.SPLASH_progress);
    }

    public void setList() {
        // dummy
    }


    View background;
    TextView txtLoading;
    Context ctx;
    View view;
    ProgressBar bar;
}
