package com.boha.malengagolf.library;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.boha.malengagolf.library.data.*;
import com.boha.malengagolf.library.fragments.ScoringByHoleFragment;
import com.boha.malengagolf.library.util.SharedUtil;
import com.boha.malengagolf.library.util.ToastUtil;

import java.util.List;

/**
 * Hosts fragment ScoringByHoleFragment
 * Created by aubreyM on 2014/04/12.
 */
public class ScoringByHoleActivity extends FragmentActivity
        implements ScoringByHoleFragment.ScoringByHoleListener {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = getApplicationContext();
        setContentView(R.layout.activity_sbh);
        scoringByHoleFragment = (ScoringByHoleFragment) getSupportFragmentManager()
                .findFragmentById(R.id.SBH_fragment);
        tournament = (TournamentDTO) getIntent().getSerializableExtra("tournament");
        leaderBoard = (LeaderBoardDTO) getIntent().getSerializableExtra("leaderBoard");
        if (leaderBoard == null) throw new UnsupportedOperationException("Leaderboard is null. Why da fuck?");
        golfGroup = SharedUtil.getGolfGroup(ctx);
        administrator = SharedUtil.getAdministrator(ctx);

        scoringByHoleFragment.setGolfGroup(golfGroup);
        scoringByHoleFragment.setAdministrator(administrator);
        scoringByHoleFragment.setTournament(tournament);
        scoringByHoleFragment.setTourneyPlayerScore(leaderBoard);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if (carrier == null) {
            Log.i(LOG, "onBackPressed, carrier is NULL");
        } else {
            Log.i(LOG, "onBackPressed, carrier is LOADED");
        }

        if (carrier != null) {
            Intent intent = new Intent();
            intent.putExtra("response", carrier);
            setResult(Activity.RESULT_OK, intent);
            finish();
            return;
        }

        finish();
    }

    @Override
    public void onPause() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        super.onPause();
    }

    @Override
    public void onResume() {
        Log.d(LOG, "onResume ...nothing to be done");
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle b) {
        Log.d(LOG, "--- onSaveInstanceState ...");
        super.onSaveInstanceState(b);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.scoring_menu, menu);
        mMenu = menu;
        return true;
    }

    public void setRefreshActionButtonState(final boolean refreshing) {
        if (mMenu != null) {
            final MenuItem refreshItem = mMenu.findItem(R.id.menu_back);
            if (refreshItem != null) {
                if (refreshing) {
                    refreshItem.setActionView(R.layout.action_bar_progess);
                } else {
                    refreshItem.setActionView(null);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getTitle().toString().equalsIgnoreCase(ctx.getResources().getString(R.string.take_pic))) {
            Intent z = new Intent(ctx, PictureActivity.class);
            z.putExtra("golfGroup", golfGroup);
            z.putExtra("tournament", tournament);
            startActivity(z);
            return true;
        }
        if (item.getTitle().toString().equalsIgnoreCase(ctx.getResources().getString(R.string.back))) {
            onBackPressed();
            return true;
        }

        if (item.getTitle().toString().equalsIgnoreCase(ctx.getResources().getString(R.string.help_me))) {
            ToastUtil.toast(ctx, "Under Construction");
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    Menu mMenu;

    static final String LOG = "ScoringByHoleActivity";
    Vibrator vb;
    TournamentDTO tournament;
    LeaderBoardDTO leaderBoard;
    GolfGroupDTO golfGroup;
    AdministratorDTO administrator;

    ScoringByHoleFragment scoringByHoleFragment;
    Context ctx;

    @Override
    public void setBusy() {
        setRefreshActionButtonState(true);
    }

    @Override
    public void setNotBusy() {
        setRefreshActionButtonState(false);
    }

    ResponseDTO carrier;

    @Override
    public void scoringSubmitted(List<LeaderBoardDTO> tps) {
        Log.i(LOG, "scoringSubmitted, tps list: " + tps.size());
        carrier = new ResponseDTO();
        carrier.setLeaderBoardList(tps);
        onBackPressed();
    }


    List<LeaderBoardDTO> updatedTeeList;

}