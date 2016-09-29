package com.rep5.sialah.common.models;

import java.io.Serializable;

/**
 * Created by chen0 on 26/5/2016.
 */
public class ClefMessage implements Serializable {



    private String id = "";
    private String deviceid = "";
    private String customerid = "";
    private String hostname = "";
    private String ipaddress = "";
    private String measurement = "";
    private String instance = "";
    private String tsid = "";
    private String kpi = "";
    private long timestamp;

    public ClefMessage(String id, long timestamp) {
        this.timestamp = timestamp;
        this.id = id;
    }

    public ClefMessage() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getTsid() {
        return tsid;
    }

    public void setTsid(String tsid) {
        this.tsid = tsid;
    }

    public String getKpi() {
        return kpi;
    }

    public void setKpi(String kpi) {
        this.kpi = kpi;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
