package com.example.termostattoendversion.ui.view.widgets;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.termostattoendversion.R;
import com.example.termostattoendversion.ui.jobs.json.JsonStatusMessage;
import com.example.termostattoendversion.ui.jobs.json.JsonWidgetMessage;
import com.example.termostattoendversion.ui.jobs.mqtt.MqttConnection;
import com.example.termostattoendversion.ui.jobs.statics.ISetStatus;
import com.example.termostattoendversion.ui.jobs.statics.StaticsStatus;

import org.json.JSONException;

import java.util.Calendar;

public class InputWidget implements ISetStatus {

    private Button textStatus;
    private View view;

    @Override
    public void setStatus(JsonStatusMessage status) {
        Log.d("STATUS", "Status Input Widget>>> " + status.getStatus());
        textStatus.setText(status.getStatus());
    }

    @Override
    public void getWidget(RecyclerView.ViewHolder viewHolder, JsonWidgetMessage message) {
        StaticsStatus.add(message.getTopic(), this);
        view = viewHolder.itemView;
        TextView textName = viewHolder.itemView.findViewById(R.id.widget_input_name);
        textStatus = viewHolder.itemView.findViewById(R.id.widget_input_status);
        if (message.getIcon().equals("thermometer")) {
            setThermometerStatus(message);
        } else if (message.getIcon().equals("alarm-outline")) {
            setTimeStatus(message);
        } else if (message.getIcon().equals("")) {
            textStatus.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        } else {
            textStatus.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }


        if (message.getColor().equals("orange")) {
            textStatus.setBackgroundColor(Color.rgb(255, 165, 0));
        } else if (message.getColor().equals("green")) {
            textStatus.setBackgroundColor(Color.GREEN);
        } else if (message.getColor().equals("red")) {
            textStatus.setBackgroundColor(Color.RED);
        }


        if (message.getDescr() != null) {
            textName.setText(message.getDescr());
        } else {
            textName.setVisibility(View.GONE);
        }
        viewHolder.itemView.findViewById(R.id.id_widget_anydata).setVisibility(View.GONE);
        viewHolder.itemView.findViewById(R.id.id_widget_chart).setVisibility(View.GONE);
        viewHolder.itemView.findViewById(R.id.id_widget_select).setVisibility(View.GONE);
        viewHolder.itemView.findViewById(R.id.switch_on_of).setVisibility(View.GONE);
        if (message.getStatus() instanceof JsonStatusMessage) {
            setStatus((JsonStatusMessage) message.getStatus());
        }
    }

    private void setTimeStatus(JsonWidgetMessage jsonWidgetMessage) {
        textStatus.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(view.getContext(), R.drawable.ic_alarm_outline_icon), null, null, null);
//                    textStatus.setPointerIcon(PointerIcon.getSystemIcon(view.getContext(), R.drawable.ic_temperature_icon));
//                    textStatus.setText("test");

        textStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar dateAndTime = Calendar.getInstance();
                TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        dateAndTime.set(Calendar.MINUTE, minute);
//                                    setInitialDateTime();
                        if (dateAndTime.getTime().getHours() > 9)
                            if (dateAndTime.getTime().getMinutes() > 9)
                                textStatus.setText(dateAndTime.getTime().getHours() + ":" + dateAndTime.getTime().getMinutes());
                            else
                                textStatus.setText(dateAndTime.getTime().getHours() + ":0" + dateAndTime.getTime().getMinutes());
                        else if (dateAndTime.getTime().getMinutes() > 9)
                            textStatus.setText("0" + dateAndTime.getTime().getHours() + ":" + dateAndTime.getTime().getMinutes());
                        else
                            textStatus.setText("0" + dateAndTime.getTime().getHours() + ":0" + dateAndTime.getTime().getMinutes());
                        MqttConnection.outputMessage(jsonWidgetMessage.getTopic().concat("/control"), textStatus.getText().toString());
                    }
                };
                String s = textStatus.getText().toString();
                if (s.length() == 5) {
                    int hour = Integer.parseInt(s.substring(0, 2));
                    int minets = Integer.parseInt(s.substring(3));
                    new TimePickerDialog(view.getContext(), t, hour, minets, true).show();
                } else {
                    new TimePickerDialog(view.getContext(), t, 0, 0, true).show();
                }
            }
        });
    }

    private void setThermometerStatus(JsonWidgetMessage jsonWidgetMessage) {
        textStatus.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(view.getContext(), R.drawable.ic_temperature_icon), null, null, null);
//                    textStatus.setPointerIcon(PointerIcon.getSystemIcon(view.getContext(), R.drawable.ic_temperature_icon));
//                    textStatus.setText("test");
        textStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Создаем AlertDialog
                AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(view.getContext());
//                Log.d("debug","test>> "+mDialogBuilder.getContext());
                final EditText input = new EditText(view.getContext());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                mDialogBuilder.setView(input);
                //Настраиваем сообщение в диалоговом окне:
                mDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //Вводим текст и отображаем в строке ввода на основном экране:
                                        textStatus.setText(input.getText());
                                        MqttConnection.outputMessage(jsonWidgetMessage.getTopic().concat("/control"), textStatus.getText().toString());
                                    }
                                })
                        .setNegativeButton("Отмена",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                //Создаем AlertDialog:
                AlertDialog alertDialog = mDialogBuilder.create();

                //и отображаем его:
                alertDialog.show();
            }
        });
    }


}
