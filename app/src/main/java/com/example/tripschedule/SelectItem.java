package com.example.tripschedule;

public class SelectItem {
    private String address;
    private String detail;
    private String mapx;
    private String mapy;
    private String roadAddress;
    private String tel;
    private String title;
    private String image;

    public SelectItem(String title,String tel,String address,String detail,String image,String mapx,String mapy){
        this.title=title;
        this.tel=tel;
        this.address=address;
        this.detail=detail;
        this.image=image;
        this.mapx=mapx;
        this.mapy=mapy;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getMapx() {
        return mapx;
    }

    public void setMapx(String mapx) {
        this.mapx = mapx;
    }

    public String getMapy() {
        return mapy;
    }

    public void setMapy(String mapy) {
        this.mapy = mapy;
    }

    public String getRoadAddress() {
        return roadAddress;
    }

    public void setRoadAddress(String roadAddress) {
        this.roadAddress = roadAddress;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
