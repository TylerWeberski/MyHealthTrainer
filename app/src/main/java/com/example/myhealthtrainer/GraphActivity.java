package com.example.myhealthtrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GraphActivity extends AppCompatActivity {

    LineGraphSeries<DataPoint> series;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        double x, y;
        x  = -5.0;

        GraphView graphView = (GraphView) findViewById(R.id.graphView);
        series = new LineGraphSeries<DataPoint>();

        for (int i = 0; i < 500; i++)
        {
            x = x + 0.1;
            y = Math.sin(x);
            series.appendData(new DataPoint(x ,y), true, 500);
        }
        graphView.addSeries(series);
    }
}