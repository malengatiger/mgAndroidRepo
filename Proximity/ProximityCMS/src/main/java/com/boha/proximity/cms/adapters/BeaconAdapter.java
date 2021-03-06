package com.boha.proximity.cms.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.boha.proximity.cms.R;
import com.boha.proximity.data.BeaconDTO;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BeaconAdapter extends ArrayAdapter<BeaconDTO> {

    private final LayoutInflater mInflater;
    private final int mLayoutRes;
    private List<BeaconDTO> mList;
    private Context ctx;

    public BeaconAdapter(Context context, int textViewResourceId,
                         List<BeaconDTO> list) {
        super(context, textViewResourceId, list);
        this.mLayoutRes = textViewResourceId;
        mList = list;
        ctx = context;
        this.mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    View view;


    static class ViewHolderItem {
        TextView txtName, txtNumber,
                txtItemCount, txtMac, txtMajor, txtMinor;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem item;
        if (convertView == null) {
            convertView = mInflater.inflate(mLayoutRes, null);
            item = new ViewHolderItem();
            item.txtName = (TextView) convertView
                    .findViewById(R.id.SH_txtName);
            item.txtNumber = (TextView) convertView
                    .findViewById(R.id.SH_txtNumber);
            item.txtItemCount = (TextView) convertView
                    .findViewById(R.id.SH_txtItemCount);
            item.txtMac = (TextView) convertView
                    .findViewById(R.id.SH_txtMac);
            item.txtMajor = (TextView) convertView
                    .findViewById(R.id.SH_txtMajor);
            item.txtMinor = (TextView) convertView
                    .findViewById(R.id.SH_txtMinor);

            convertView.setTag(item);
        } else {
            item = (ViewHolderItem) convertView.getTag();
        }

        BeaconDTO p = mList.get(position);
        if (p.getBeaconName() == null) {
            item.txtName.setText("NoName Beacon #" + (position + 1));
        } else {
            item.txtName.setText(p.getBeaconName());
        }
        item.txtNumber.setText("" + (position + 1));
        item.txtMac.setText(p.getMacAddress());
        item.txtMajor.setText(""+p.getMajor());
        item.txtMinor.setText(""+p.getMinor());
        if (p.getImageFileNameList() == null) p.setImageFileNameList(new ArrayList<String>());
        item.txtItemCount.setText("" + p.getImageFileNameList().size());

        animateView(convertView);
        return (convertView);
    }

    public void animateView(final View view) {
        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.grow_fade_in_center);
        a.setDuration(1000);
        if (view == null)
            return;
        view.startAnimation(a);
    }

    static final Locale x = Locale.getDefault();
    static final SimpleDateFormat y = new SimpleDateFormat("dd MMMM yyyy", x);
    static final DecimalFormat df = new DecimalFormat("###,###,##0.0");
}
