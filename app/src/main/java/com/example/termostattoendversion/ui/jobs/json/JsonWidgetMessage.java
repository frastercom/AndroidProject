package com.example.termostattoendversion.ui.jobs.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonWidgetMessage {

    private String widget;
    private String icon;
    private String iconOff;
    private String page;
    private String order;
    private String descr;
    private String topic;
    private String after;
    private String[] options;
    private Object status;
    private String color;
    private String type;
    private String size;
    private String dateFormat;
    private String maxCount;

    public JsonWidgetMessage(String message) {
        try {
            JSONObject o = new JSONObject(message);
            if (!o.isNull("widget")) {
                widget = o.getString("widget");
            }
            if (!o.isNull("icon")) {
                icon = o.getString("icon");
            }
            if (!o.isNull("iconOff")) {
                iconOff = o.getString("iconOff");
            }
            if (!o.isNull("page")) {
                page = o.getString("page");
            }
            if (!o.isNull("order")) {
                order = o.getString("order");
            }
            if (!o.isNull("descr")) {
                descr = o.getString("descr");
            }
            if (!o.isNull("topic")) {
                topic = o.getString("topic");
            }
            if (!o.isNull("after")) {
                after = o.getString("after");
            }
            if (!o.isNull("options")) {
                JSONArray a = o.getJSONArray("options");
                options = new String[a.length()];
                for (int i = 0; i<a.length(); i++)
                    options[i] = a.getString(i);
            }
            if (!o.isNull("status")) {
                status = o.getString("status");
            }
            if (!o.isNull("color")) {
                color = o.getString("color");
            }
            if (!o.isNull("type")) {
                type = o.getString("type");
            }
            if (!o.isNull("size")) {
                size = o.getString("size");
            }
            if (!o.isNull("dateFormat")) {
                dateFormat = o.getString("dateFormat");
            }
            if (!o.isNull("maxCount")) {
                maxCount = o.getString("maxCount");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getWidget() {
        return widget;
    }

    public void setWidget(String widget) {
        this.widget = widget;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconOff() {
        return iconOff;
    }

    public void setIconOff(String iconOff) {
        this.iconOff = iconOff;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(String maxCount) {
        this.maxCount = maxCount;
    }
}
