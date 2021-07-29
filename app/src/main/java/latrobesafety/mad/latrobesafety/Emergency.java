package latrobesafety.mad.latrobesafety;

import java.util.Date;

public class Emergency extends Request {

    private String lat;
    private String lon;
  // private String location;



    public Emergency( String name,String message,String lat,String lon, Date date)
    {
        super(name,1,message,date);
        //this.location = location;
        this.lat = lat;
        this.lon = lon;
    }

    public Emergency(){};

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "Emergency{" +
                "lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                '}';
    }
// public String getLocation() {
       // return location;
   // }

   // public void setLocation(String location) {
      //  this.location = location;
  //  }

}
