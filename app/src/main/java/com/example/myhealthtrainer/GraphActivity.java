package com.example.myhealthtrainer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myhealthtrainer.viewmodel.MainActivityViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class GraphActivity extends AppCompatActivity {

    LineGraphSeries<DataPoint> series;
    private FirebaseFirestore db;
    private ArrayList<QueryDocumentSnapshot> docs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        db = FirebaseFirestore.getInstance();
        docs = new ArrayList<QueryDocumentSnapshot>();
        initGraph();

    }

    private void initGraph()
    {
        CollectionReference userRef = db.collection("users/" + MainActivityViewModel.getUser().getUid() + "/workouts");

        userRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                {
                    for (QueryDocumentSnapshot document : task.getResult())
                    {
                        docs.add(document);
                    }

                    sortDocumentSnapshotsByDate();

                    double x, y;
                    x  = -5.0;

                    GraphView graphView = (GraphView) findViewById(R.id.graphView);
                    series = new LineGraphSeries<DataPoint>();
                    graphView.setTitle("Bench Progress");

                    for (int i = 0; i < 10; i++)
                    {
                        x = i;
                        y = Double.parseDouble(docs.get(i).get("weight").toString());
                        series.appendData(new DataPoint(x ,y), true, 20);
                    }
                    graphView.addSeries(series);
                }
            }
        });
    }

    private void sortDocumentSnapshotsByDate() {
        Comparator<QueryDocumentSnapshot> dateComparator = new Comparator<QueryDocumentSnapshot>() {
            @Override
            public int compare(QueryDocumentSnapshot doc1, QueryDocumentSnapshot doc2) {
                String dateString1 = doc1.getString("date");
                String dateString2 = doc2.getString("date");

                LocalDateTime date1 = parseDate(dateString1);
                LocalDateTime date2 = parseDate(dateString2);

                if (date1 != null && date2 != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        return date1.compareTo(date2);
                    }
                } else {
                    return 0;
                }
                return 0;
            }
        };

        docs.sort(dateComparator);
    }

    private LocalDateTime parseDate(String dateString) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return LocalDateTime.parse(dateString);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}