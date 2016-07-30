package com.ct.fitorto.model;

import com.ct.fitorto.model.FitortoCategory;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ashwini on 5/25/2016.
 */
public class JsonResponseCategory {


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
        private List<FitortoCategory> data = new ArrayList<FitortoCategory>();

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
        public List<FitortoCategory> getData() {
            return data;
        }

        /**
         *
         * @param data
         * The data
         */
        public void setData(List<FitortoCategory> data) {
            this.data = data;
        }

}


