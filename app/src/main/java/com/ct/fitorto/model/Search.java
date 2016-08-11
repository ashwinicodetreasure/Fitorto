package com.ct.fitorto.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashwini on 5/27/2016.
 */
public class Search implements Parcelable {

    @SerializedName("gymID")
    @Expose
    private String gymID;
    @SerializedName("gymName")
    @Expose
    private String gymName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("contactNo")
    @Expose
    private String contactNo;
    @SerializedName("emailID")
    @Expose
    private String emailID;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("capacity")
    @Expose
    private String capacity;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("isFavorite")
    @Expose
    private String isFavorite;
    @SerializedName("reviews")
    @Expose
    private String reviews;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("amenties")
    @Expose
    private List<String> amenties = new ArrayList<String>();
    @SerializedName("equipments")
    @Expose
    private List<String> equipments = new ArrayList<String>();
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("schedule")
    @Expose
    private List<Schedule> schedule = new ArrayList<Schedule>();
    @SerializedName("packages")
    @Expose
    private ArrayList<Package> packages = new ArrayList<Package>();
    @SerializedName("images")
    @Expose
    private List<GymImages> images = new ArrayList<GymImages>();

    /**
     *
     * @return
     * The gymID
     */
    public String getGymID() {
        return gymID;
    }

    /**
     *
     * @param gymID
     * The gymID
     */
    public void setGymID(String gymID) {
        this.gymID = gymID;
    }

    /**
     *
     * @return
     * The gymName
     */
    public String getGymName() {
        return gymName;
    }

    /**
     *
     * @param gymName
     * The gymName
     */
    public void setGymName(String gymName) {
        this.gymName = gymName;
    }

    /**
     *
     * @return
     * The address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     * The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return
     * The area
     */
    public String getArea() {
        return area;
    }

    /**
     *
     * @param area
     * The area
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     *
     * @return
     * The city
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city
     * The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return
     * The state
     */
    public String getState() {
        return state;
    }

    /**
     *
     * @param state
     * The state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     *
     * @return
     * The country
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     * The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return
     * The logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     *
     * @param logo
     * The logo
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     *
     * @return
     * The contactNo
     */
    public String getContactNo() {
        return contactNo;
    }

    /**
     *
     * @param contactNo
     * The contactNo
     */
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    /**
     *
     * @return
     * The emailID
     */
    public String getEmailID() {
        return emailID;
    }

    /**
     *
     * @param emailID
     * The emailID
     */
    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    /**
     *
     * @return
     * The website
     */
    public String getWebsite() {
        return website;
    }

    /**
     *
     * @param website
     * The website
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     *
     * @return
     * The capacity
     */
    public String getCapacity() {
        return capacity;
    }

    /**
     *
     * @param capacity
     * The capacity
     */
    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The category
     */
    public String getCategory() {
        return category;
    }

    /**
     *
     * @param category
     * The category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     *
     * @return
     * The isFavorite
     */
    public String getIsFavorite() {
        return isFavorite;
    }

    /**
     *
     * @param isFavorite
     * The isFavorite
     */
    public void setIsFavorite(String isFavorite) {
        this.isFavorite = isFavorite;
    }

    /**
     *
     * @return
     * The reviews
     */
    public String getReviews() {
        return reviews;
    }

    /**
     *
     * @param reviews
     * The reviews
     */
    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    /**
     *
     * @return
     * The rating
     */
    public String getRating() {
        return rating;
    }

    /**
     *
     * @param rating
     * The rating
     */
    public void setRating(String rating) {
        this.rating = rating;
    }

    /**
     *
     * @return
     * The amenties
     */
    public List<String> getAmenties() {
        return amenties;
    }

    /**
     *
     * @param amenties
     * The amenties
     */
    public void setAmenties(List<String> amenties) {
        this.amenties = amenties;
    }

    /**
     *
     * @return
     * The equipments
     */
    public List<String> getEquipments() {
        return equipments;
    }

    /**
     *
     * @param equipments
     * The equipments
     */
    public void setEquipments(List<String> equipments) {
        this.equipments = equipments;
    }

    /**
     *
     * @return
     * The gender
     */
    public String getGender() {
        return gender;
    }

    /**
     *
     * @param gender
     * The gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     *
     * @return
     * The latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     *
     * @param latitude
     * The latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     *
     * @return
     * The longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     *
     * @param longitude
     * The longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     *
     * @return
     * The schedule
     */
    public List<Schedule> getSchedule() {
        return schedule;
    }

    /**
     *
     * @param schedule
     * The schedule
     */
    public void setSchedule(List<Schedule> schedule) {
        this.schedule = schedule;
    }

    /**
     *
     * @return
     * The packages
     */
    public ArrayList<Package> getPackages() {
        return packages;
    }

    /**
     *
     * @param packages
     * The packages
     */
    public void setPackages(ArrayList<Package> packages) {
        this.packages = packages;
    }

    /**
     *
     * @return
     * The images
     */
    public List<GymImages> getImages() {
        return images;
    }

    /**
     *
     * @param images
     * The images
     */
    public void setImages(List<GymImages> images) {
        this.images = images;
    }


    protected Search(Parcel in) {
        gymID = in.readString();
        gymName = in.readString();
        address = in.readString();
        area = in.readString();
        city = in.readString();
        state = in.readString();
        country = in.readString();
        logo = in.readString();
        contactNo = in.readString();
        emailID = in.readString();
        website = in.readString();
        capacity = in.readString();
        description = in.readString();
        category = in.readString();
        isFavorite = in.readString();
        reviews = in.readString();
        rating = in.readString();
        if (in.readByte() == 0x01) {
            amenties = new ArrayList<String>();
            in.readList(amenties, String.class.getClassLoader());
        } else {
            amenties = null;
        }
        if (in.readByte() == 0x01) {
            equipments = new ArrayList<String>();
            in.readList(equipments, String.class.getClassLoader());
        } else {
            equipments = null;
        }
        gender = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        if (in.readByte() == 0x01) {
            schedule = new ArrayList<Schedule>();
            in.readList(schedule, Schedule.class.getClassLoader());
        } else {
            schedule = null;
        }
        if (in.readByte() == 0x01) {
            packages = new ArrayList<Package>();
            in.readList(packages, Package.class.getClassLoader());
        } else {
            packages = null;
        }
        if (in.readByte() == 0x01) {
            images = new ArrayList<GymImages>();
            in.readList(images, GymImages.class.getClassLoader());
        } else {
            images = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(gymID);
        dest.writeString(gymName);
        dest.writeString(address);
        dest.writeString(area);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(country);
        dest.writeString(logo);
        dest.writeString(contactNo);
        dest.writeString(emailID);
        dest.writeString(website);
        dest.writeString(capacity);
        dest.writeString(description);
        dest.writeString(category);
        dest.writeString(isFavorite);
        dest.writeString(reviews);
        dest.writeString(rating);
        if (amenties == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(amenties);
        }
        if (equipments == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(equipments);
        }
        dest.writeString(gender);
        dest.writeString(latitude);
        dest.writeString(longitude);
        if (schedule == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(schedule);
        }
        if (packages == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(packages);
        }
        if (images == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(images);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Search> CREATOR = new Parcelable.Creator<Search>() {
        @Override
        public Search createFromParcel(Parcel in) {
            return new Search(in);
        }

        @Override
        public Search[] newArray(int size) {
            return new Search[size];
        }
    };
}