package com.ct.fitorto.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Ashwini on 5/25/2016.
 */
public class JsonResponseUser implements Parcelable {




        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("msg")
        @Expose
        private String msg;
        @SerializedName("count")
        @Expose
        private int count;
        @SerializedName("data")
        @Expose
        private List<FitortoUser> data = new ArrayList<FitortoUser>();
        @SerializedName("countOfCity")
        @Expose
        private int countOfCity;
        @SerializedName("Cities")
        @Expose
        private List<City> cities = new ArrayList<City>();

        /**
         *
         * @return
         * The status
         */
        public String getStatus() {
                return status;
        }

        /**
         *
         * @param status
         * The status
         */
        public void setStatus(String status) {
                this.status = status;
        }

        /**
         *
         * @return
         * The msg
         */
        public String getMsg() {
                return msg;
        }

        /**
         *
         * @param msg
         * The msg
         */
        public void setMsg(String msg) {
                this.msg = msg;
        }

        /**
         *
         * @return
         * The count
         */
        public int getCount() {
                return count;
        }

        /**
         *
         * @param count
         * The count
         */
        public void setCount(int count) {
                this.count = count;
        }

        /**
         *
         * @return
         * The data
         */
        public List<FitortoUser> getData() {
                return data;
        }

        /**
         *
         * @param data
         * The data
         */
        public void setData(List<FitortoUser> data) {
                this.data = data;
        }

        /**
         *
         * @return
         * The countOfCity
         */
        public int getCountOfCity() {
                return countOfCity;
        }

        /**
         *
         * @param countOfCity
         * The countOfCity
         */
        public void setCountOfCity(int countOfCity) {
                this.countOfCity = countOfCity;
        }

        /**
         *
         * @return
         * The cities
         */
        public List<City> getCities() {
                return cities;
        }

        /**
         *
         * @param cities
         * The Cities
         */
        public void setCities(List<City> cities) {
                this.cities = cities;
        }


        protected JsonResponseUser(Parcel in) {
                status = in.readString();
                msg = in.readString();
                count = in.readInt();
                if (in.readByte() == 0x01) {
                        data = new ArrayList<FitortoUser>();
                        in.readList(data, FitortoUser.class.getClassLoader());
                } else {
                        data = null;
                }
                countOfCity = in.readInt();
                if (in.readByte() == 0x01) {
                        cities = new ArrayList<City>();
                        in.readList(cities, City.class.getClassLoader());
                } else {
                        cities = null;
                }
        }

        @Override
        public int describeContents() {
                return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(status);
                dest.writeString(msg);
                dest.writeInt(count);
                if (data == null) {
                        dest.writeByte((byte) (0x00));
                } else {
                        dest.writeByte((byte) (0x01));
                        dest.writeList(data);
                }
                dest.writeInt(countOfCity);
                if (cities == null) {
                        dest.writeByte((byte) (0x00));
                } else {
                        dest.writeByte((byte) (0x01));
                        dest.writeList(cities);
                }
        }

        @SuppressWarnings("unused")
        public static final Parcelable.Creator<JsonResponseUser> CREATOR = new Parcelable.Creator<JsonResponseUser>() {
                @Override
                public JsonResponseUser createFromParcel(Parcel in) {
                        return new JsonResponseUser(in);
                }

                @Override
                public JsonResponseUser[] newArray(int size) {
                        return new JsonResponseUser[size];
                }
        };
}