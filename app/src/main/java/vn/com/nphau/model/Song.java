package vn.com.nphau.model;

import java.io.Serializable;

/**
 * Class Song
 * Created by nphau on 8/28/2015.
 */
public class Song implements Serializable {

    private String id;
    private String name;
    private String info;
    private String author;
    private String vol;


    private int isFavorite;
    private int isDelete;

    public Song() {
    }

    public Song(String id, String name, String info, String author, String vol) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.author = author;
        this.vol = vol;
    }

    public Song(String id, String name, String info, String author, String vol, int isFavorite) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.author = author;
        this.vol = vol;
        this.isFavorite = isFavorite;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getVol() {
        return vol;
    }

    public void setVol(String vol) {
        this.vol = vol;
    }

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }
}
