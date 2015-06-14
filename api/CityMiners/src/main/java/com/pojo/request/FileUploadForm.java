package com.pojo.request;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.ws.rs.FormParam;

/**
 * Created by wizzard on 14.06.15.
 */
public class FileUploadForm {
    public FileUploadForm() {
    }

    private byte[] image;
    private String lat;
    private String lng;
    private String desc;
    private String uid;
    private String type;
    private String layer;

    public byte[] getImage() {
        return image;
    }

    @FormParam("image")
    @PartType("image/jpeg")
    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getLat() {
        return lat;
    }

    @FormParam("lat")
    @PartType("text/plain")
    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    @FormParam("lng")
    @PartType("text/plain")
    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getDesc() {
        return desc;
    }

    @FormParam("desc")
    @PartType("text/plain")
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUid() {
        return uid;
    }

    @FormParam("uid")
    @PartType("text/plain")
    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    @FormParam("type")
    @PartType("text/plain")
    public void setType(String type) {
        this.type = type;
    }

    public String getLayer() {
        return layer;
    }

    @FormParam("layer")
    @PartType("text/plain")
    public void setLayer(String layer) {
        this.layer = layer;
    }
}
