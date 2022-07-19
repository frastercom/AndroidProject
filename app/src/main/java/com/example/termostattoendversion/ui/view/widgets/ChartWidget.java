package com.example.termostattoendversion.ui.view.widgets;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.termostattoendversion.R;
import com.example.termostattoendversion.ui.jobs.json.JsonStatusMessage;
import com.example.termostattoendversion.ui.jobs.json.JsonWidgetMessage;
import com.example.termostattoendversion.ui.jobs.statics.ISetStatus;
import com.example.termostattoendversion.ui.jobs.statics.StaticsStatus;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChartWidget implements ISetStatus {

    private GraphView graph;
    private LineGraphSeries<DataPoint> series;
    private int size;
    private ArrayList<String> hm = new ArrayList<String>();

    @Override
    public void setStatus(JsonStatusMessage status) {
        try {
            JSONArray array = new JSONArray(status.getStatus());
            if ((array.length() > 1) || (series == null)) {
                graph.getSeries().clear();
                Log.d("debug", "test 1");
                DataPoint[] dp = new DataPoint[array.length()];
                for (int i = 0; i < array.length(); i++) {
                    Date date = new Date(array.getJSONObject(i).getLong("x"));
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    hm.add(sdf.format(date));
                    dp[i] = new DataPoint(i, array.getJSONObject(i).getInt("y1"));
                }
                size = dp.length;
                series = new LineGraphSeries<DataPoint>(dp);
                series.setColor(Color.GREEN);
                graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                    @Override
                    public String formatLabel(double value, boolean isValueX) {
                        if (isValueX) {
                            int a = (int) value;
                            if ((a >= 0) && (a < size))
                                return hm.get(a);
                            else if (a == size)
                                return hm.get(a - 1);
                            else
                                return "";
                        } else {
                            return super.formatLabel(value, isValueX);
                        }
                    }
                });
                graph.getViewport().setXAxisBoundsManual(true);
                graph.getViewport().setMaxX(dp.length);
                graph.getViewport().setScalable(true);
                graph.getViewport().setScrollable(true);
                graph.addSeries(series);
            } else {
                Log.d("debug", "test 2");
                Date date = new Date(array.getJSONObject(0).getLong("x"));
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                hm.add(sdf.format(date));
                series.appendData(new DataPoint(size, array.getJSONObject(0).getInt("y1")), true, size + 1);
                size++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getWidget(RecyclerView.ViewHolder viewHolder, JsonWidgetMessage message) {
        StaticsStatus.add(message.getTopic(), this);
        graph = viewHolder.itemView.findViewById(R.id.widget_chart);
        viewHolder.itemView.findViewById(R.id.id_widget_anydata).setVisibility(View.GONE);
        viewHolder.itemView.findViewById(R.id.id_widget_select).setVisibility(View.GONE);
        viewHolder.itemView.findViewById(R.id.id_widget_input).setVisibility(View.GONE);
        viewHolder.itemView.findViewById(R.id.switch_on_of).setVisibility(View.GONE);
        if (message.getStatus() instanceof JsonStatusMessage) {
            setStatus((JsonStatusMessage) message.getStatus());
        }
    }
}
