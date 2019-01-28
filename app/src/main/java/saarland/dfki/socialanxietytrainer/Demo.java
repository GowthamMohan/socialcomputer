package saarland.dfki.socialanxietytrainer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class Demo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        BarChart barChart = (BarChart) findViewById(R.id.barchart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(6f, 0));
        entries.add(new BarEntry(3f, 1));
        entries.add(new BarEntry(9f, 2));
        entries.add(new BarEntry(5f, 3));
        entries.add(new BarEntry(7f, 4));
        entries.add(new BarEntry(2f, 5));
        entries.add(new BarEntry(1f, 6));
        entries.add(new BarEntry(2f, 7));
        entries.add(new BarEntry(4f, 8));
        entries.add(new BarEntry(9f, 9));
        entries.add(new BarEntry(8f, 10));
        entries.add(new BarEntry(2f, 11));




        BarDataSet bardataset = new BarDataSet(entries, "Categories");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Sun");
        labels.add("Sun");
        labels.add("Sat");
        labels.add("Sat");
        labels.add("Fri");
        labels.add("Thurs");
        labels.add("Thurs");
        labels.add("Wed");
        labels.add("Wed");
        labels.add("Tue");
        labels.add("Tue");
        labels.add("Mon");
        labels.add("Mon");

        BarData data = new BarData(labels, bardataset);
        barChart.setData(data); // set the data and list of lables into chart

        barChart.setDescription("Progress Level");  // set the description

        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        barChart.animateY(5000);


    }
}
