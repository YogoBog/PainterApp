package com.example.myapplication;


import static java.security.AccessController.getContext;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int edges;
    private Button colors;
    private Button undo;
    private Button rec;
    private Button circle;
    private Button path;
    private Button poly;
    public static boolean circActive;
    public static boolean pathActive;
    public static boolean recActive;
    public static boolean polyActive;
    private MyCanvasView myCanvasView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        polyActive = false;
        recActive = false;
        circActive = false;
        pathActive = true;

        findViewById(R.id.resetButton).setOnClickListener(v -> myCanvasView.reset());

        myCanvasView = findViewById(R.id.myCanvasView);
        circle = findViewById(R.id.circleButton);
        path = findViewById(R.id.pathButton);
        rec = findViewById(R.id.recButton);
        undo = findViewById(R.id.undoButton);
        colors = findViewById(R.id.ColorsButton);
        poly = findViewById(R.id.polyButton);

        circle.setOnClickListener(view -> {
            circActive = true;
            pathActive = false;
            recActive = false;
            polyActive = false;
        });

        path.setOnClickListener(view -> {
            circActive = false;
            recActive = false;
            pathActive = true;
            polyActive = false;
        });

        rec.setOnClickListener(view -> {
            circActive = false;
            recActive = true;
            pathActive = false;
            polyActive = false;
        });

        poly.setOnClickListener(view -> {
            circActive = false;
            recActive = false;
            pathActive = false;
            polyActive = true;

            edges = 3;
            polyDialog(MainActivity.this, 3);
        });

        undo.setOnClickListener(view -> {
            myCanvasView.undo();
            myCanvasView.invalidate();
        });

        colors.setOnClickListener(view -> {
            showDialog(MainActivity.this);
        });

    }

    private void showDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final android.view.View dialogView = inflater.inflate(R.layout.colors_dialog, null);

        SeekBar seekBarRed = dialogView.findViewById(R.id.seekBar3);
        SeekBar seekBarGreen = dialogView.findViewById(R.id.seekBar1);
        SeekBar seekBarBlue = dialogView.findViewById(R.id.seekBar2);
        TextView textView = dialogView.findViewById(R.id.textView);
        Button okButton = dialogView.findViewById(R.id.okButton);

        final int[] color = {Color.rgb(0, 0, 0)};

        seekBarRed.setMin(0);
        seekBarGreen.setMin(0);
        seekBarBlue.setMin(0);
        seekBarRed.setMax(255);
        seekBarGreen.setMax(255);
        seekBarBlue.setMax(255);

        textView.setBackgroundColor(Color.rgb(0, 0, 0));


        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setView(dialogView)
                .create();
        alertDialog.show();

        SeekBar.OnSeekBarChangeListener sbl = new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

                if (seekBar.getId() == seekBarRed.getId()) {
                    textView.setBackgroundColor(Color.rgb(progress, seekBarGreen.getProgress(), seekBarBlue.getProgress()));
                    color[0] = Color.rgb(progress, seekBarGreen.getProgress(), seekBarBlue.getProgress());
                }

                if (seekBar.getId() == seekBarGreen.getId()) {
                    textView.setBackgroundColor(Color.rgb(seekBarRed.getProgress(), progress, seekBarBlue.getProgress()));
                    color[0] = Color.rgb(seekBarRed.getProgress(), progress, seekBarBlue.getProgress());
                }
                if (seekBar.getId() == seekBarBlue.getId()) {
                    textView.setBackgroundColor(Color.rgb(seekBarRed.getProgress(), seekBarGreen.getProgress(), progress));
                    color[0] = Color.rgb(seekBarRed.getProgress(), seekBarGreen.getProgress(), progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        };

        seekBarRed.setOnSeekBarChangeListener(sbl);
        seekBarGreen.setOnSeekBarChangeListener(sbl);
        seekBarBlue.setOnSeekBarChangeListener(sbl);

        okButton.setOnClickListener(v -> {
            alertDialog.dismiss();
            myCanvasView.setDrawColor(color[0]);
            myCanvasView.invalidate();
        });

    }

    private void polyDialog(Context context, int progress) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final android.view.View dialogView = inflater.inflate(R.layout.poly_dialog, null);

        TextView progressTxt = dialogView.findViewById(R.id.edgeTextView);
        SeekBar dialSeekBar = dialogView.findViewById(R.id.polySeekBar);
        Button okButton = dialogView.findViewById(R.id.okButton2);

        dialSeekBar.setMax(9);
        dialSeekBar.setMin(3);
        dialSeekBar.setProgress(progress);
        progressTxt.setText(String.valueOf(progress));

        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setView(dialogView)
                .create();

        alertDialog.show();

        dialSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                edges = i;
                progressTxt.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        okButton.setOnClickListener(v -> {
            myCanvasView.setEdges(edges);
            alertDialog.dismiss();
        });
    }
}