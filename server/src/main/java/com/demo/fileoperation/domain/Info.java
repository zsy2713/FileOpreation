package com.demo.fileoperation.domain;

public class Info {
    private Integer id;
    private String size;
    private String type;
    private String filename;
    private String date;
    private String mkdir;
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMkdir() {
        return mkdir;
    }

    public void setMkdir(String mkdir) {
        this.mkdir = mkdir;
    }

    @Override
    public String toString() {
        return "Info{" +
                "id=" + id +
                ", size='" + size + '\'' +
                ", type='" + type + '\'' +
                ", filename='" + filename + '\'' +
                ", date='" + date + '\'' +
                ", mkdir='" + mkdir + '\'' +
                '}';
    }
}
