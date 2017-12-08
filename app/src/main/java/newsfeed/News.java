package newsfeed;

/**
 * Created by lara on 20/9/16.
 */
public class News {

    String userName;
    String beerName;
    String postDate;
    String postInfo;
    String postRating;
    String userLocation;

    public News(String userName, String beerName, String postDate, String postInfo, String postRating, String userLocation) {
        this.userName = userName;
        this.beerName = beerName;
        this.postDate = postDate;
        this.postInfo = postInfo;
        this.postRating = postRating;
        this.userLocation = userLocation;
    }

    public String getuserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBeerName() {
        return beerName;
    }

    public void setBeerName(String beerName) {
        this.beerName = beerName;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPostInfo() {
        return postInfo;
    }

    public void setPostInfo(String postInfo) {
        this.postInfo = postInfo;
    }

    public String getPostRating() {
        return postRating;
    }

    public void setPostRating(String postRating) {
        this.postRating = postRating;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }


    @Override
    public String toString() {
        return "News{" +
                "userName='" + userName + '\'' +
                ", beerName='" + beerName + '\'' +
                ", postDate='" + postDate + '\'' +
                ", postInfo='" + postInfo + '\'' +
                ", postRating='" + postRating + '\'' +
                ", userLocation='" + userLocation + '\'' +
                '}';
    }
}
