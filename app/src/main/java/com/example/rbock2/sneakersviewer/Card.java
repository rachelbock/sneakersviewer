package com.example.rbock2.sneakersviewer;

/**
 * Card model
 *
 * @author rbock2
 */

public class Card {

    private String squarishURL;
    private String portraitURL;
    private String landscapeURL;
    private String title;
    private String subtitle;
    private String colorTheme;

    public String getSquarishURL() {
        return squarishURL;
    }

    public void setSquarishURL(String squarishURL) {
        this.squarishURL = squarishURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getColorTheme() {
        return colorTheme;
    }

    public void setColorTheme(String colorTheme) {
        this.colorTheme = colorTheme;
    }

    public String getPortraitURL() {
        return portraitURL;
    }

    public void setPortraitURL(String portraitURL) {
        this.portraitURL = portraitURL;
    }

    public String getLandscapeURL() {
        return landscapeURL;
    }

    public void setLandscapeURL(String landscapeURL) {
        this.landscapeURL = landscapeURL;
    }
}
