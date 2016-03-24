package com.example.prasanna.myapplication;

/**
 * Created by Prasanna on 3/21/16.
 */
public class Restaurant {
    //Unique Id
    private long mId;
    //Image icon
    private String mImageURL;
    //Name
    private String mRestId;
    private String mTitle;
    //Rating
    private float mRating;
    private String mRatingImageURL;
    //Address
    private String mAddress;
    // Reviews Count
    private int mReviewCount;
    //Phone Number
    private String mDisplayPhone;
    // Snippet
    private String mSnippetText;

    private boolean mIsFavorite;

    public Restaurant(String restId) {
        // Generate unique identifier
        mRestId = restId;
        mId=-1;
    }
    public Restaurant(){

    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }



    public String getImageURL() {
        return mImageURL;
    }

    public void setImageURL(String imageURL) {
        mImageURL = imageURL;
    }

    public void setIsFavorite(boolean isFavorite) {
        mIsFavorite = isFavorite;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public float getRating() {
        return mRating;
    }

    public void setRating(float rating) {
        mRating = rating;
    }

    public String getRatingImageURL() {
        return mRatingImageURL;
    }

    public void setRatingImageURL(String ratingImageURL) {
        mRatingImageURL = ratingImageURL;
    }

    public int getReviewCount() {
        return mReviewCount;
    }

    public void setReviewCount(int reviewCount) {
        mReviewCount = reviewCount;
    }

    public String getSnippetText() {
        return mSnippetText;
    }

    public void setSnippetText(String snippetText) {
        mSnippetText = snippetText;
    }

    public String getRestId() {
        return mRestId;
    }

    public void setRestId(String restId) {
        mRestId = restId;
    }

    public boolean isFavorite() {
        return mIsFavorite;
    }

    public void setFavorite(boolean isFavorite) {
        mIsFavorite = isFavorite;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }
    public String getDisplayPhone() {
        return mDisplayPhone;
    }

    public void setDisplayPhone(String displayPhone) {
        mDisplayPhone = displayPhone;
    }
}
