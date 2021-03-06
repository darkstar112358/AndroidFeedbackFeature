package com.example.FeedbackLibrary;

import android.os.Bundle;
import android.app.ListActivity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;
import android.widget.ImageView.ScaleType;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.File;
import java.util.ArrayList;

import com.example.FeedbackLibrary.FeedbackActivity.StateParameters;

public class Preview extends ListActivity {

    public static boolean systemLogCheck;

    private static String[] items = {"Package name", "Package Version", "Date", "Device", "SDK version", "Bulid Id",
            "Build Release", "Build Type", "Build Fingerprint", "Brand", "Phone type", "Network Type", "Running Apps",
            "System Log", "Events Log", "Screenshot"};

    private static String[] iteminfo = {FeedbackActivity.DeviceData.packageName, FeedbackActivity.DeviceData.packageVersion, FeedbackActivity.DeviceData.currentDate, FeedbackActivity.DeviceData.device
            , FeedbackActivity.DeviceData.sdkVersion, FeedbackActivity.DeviceData.buildId, FeedbackActivity.DeviceData.buildRelease, FeedbackActivity.DeviceData.buildType
            , FeedbackActivity.DeviceData.buildFingerPrint, FeedbackActivity.DeviceData.brand, FeedbackActivity.DeviceData.phoneType, FeedbackActivity.DeviceData.networkType, "Click for list of running apps"
            , "Click for viewing System Log", "Click for viewing Events Log", "Click to edit Screenshot"};

    private ArrayList<String> mData = new ArrayList<String>();

    private ArrayList<String> mData2 = new ArrayList<String>();

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!FeedbackActivity.state.contains(StateParameters.tablet))
            super.setTheme(R.style.AppBaseTheme);
        setContentView(R.layout.activity_preview);

        if (FeedbackActivity.state.contains(StateParameters.tablet)) {
            Display d = getWindowManager().getDefaultDisplay();

            if (d.getHeight() > 800) {

                WindowManager.LayoutParams params = getWindow().getAttributes();

                params.height = 800;

                this.getWindow().setAttributes(params);

            }
        }
        setListAdapter(new PreviewAdapter());
        for (int i = 0; i < items.length; i++) {
            if (((items[i].equals("Running Apps") || items[i].equals("System Log") ||
                    items[i].equals("Events Log")) && !FeedbackActivity.state.contains(FeedbackActivity.StateParameters.includeSystemDataCheck)) || (items[i].equals("Screenshot") && !FeedbackActivity.state.contains(FeedbackActivity.StateParameters.includeSnapshotCheck)))
                continue;
            mData.add(items[i]);
            mData2.add(iteminfo[i]);
        }
    }

    class PreviewAdapter extends ArrayAdapter<String> {

        PreviewAdapter() {
            super(Preview.this, R.id.mainText, mData);
        }

        @Override
        public int getItemViewType(int position) {
            if (mData.get(position).equals("Screenshot"))
                return 1;
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View row = convertView;

            if (convertView == null || getItemViewType(position) == 1) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                if (getItemViewType(position) == 1) {
                    row = vi.inflate(R.layout.row_screenshot, null);
                } else {
                    row = vi.inflate(R.layout.row_normal, null);
                }
            }

            if (mData.get(position).equals("Screenshot")) {

                Bitmap bitmap = BitmapFactory.decodeFile(FeedbackActivity.baseDir + File.separator + FeedbackActivity.screenShotFileName);

                int width = bitmap.getWidth();

                int height = bitmap.getHeight();

//                Display d = getWindowManager().getDefaultDisplay();

                float scaleWidth = (float) 0.5;

                float scaleHeight = (float) 0.5;

                Matrix matrix = new Matrix();

                matrix.postScale(scaleWidth, scaleHeight);

                Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);

                ImageView image1 = (ImageView) row.findViewById(R.id.screenshotImage);

                image1.setScaleType(ScaleType.CENTER);

                image1.setImageBitmap(resizedBitmap);

                TextView mainText = (TextView) row.findViewById(R.id.mainText);

                mainText.setText(mData.get(position));

                TextView subText = (TextView) row.findViewById(R.id.subText);

                subText.setText(mData2.get(position));
            } else if (getItemViewType(position) == 0) {

                TextView mainText = (TextView) row.findViewById(R.id.mainText);

                mainText.setText(mData.get(position));

                TextView subText = (TextView) row.findViewById(R.id.subText);

                subText.setText(mData2.get(position));

            }

            return (row);

        }
    }

    @Override
    public void onListItemClick(ListView parent, View v, int position, long id) {
        if (mData.get(position).equals("Running Apps")) {
            Intent intent2 = new Intent(this, ProcessList.class);
            intent2.putStringArrayListExtra("list1", getIntent().getStringArrayListExtra("list1"));
            startActivity(intent2);
        } else if (mData.get(position).equals("System Log")) {
            Intent intent3 = new Intent(this, LogList.class);
            systemLogCheck = true;
            startActivity(intent3);
        } else if (mData.get(position).equals("Events Log")) {
            Intent intent4 = new Intent(this, LogList.class);
            systemLogCheck = false;
            startActivity(intent4);
        } else if (mData.get(position).equals("Screenshot")) {
            Intent intent5 = new Intent(this, FingerPaint.class);
            startActivity(intent5);
        }
    }

}
