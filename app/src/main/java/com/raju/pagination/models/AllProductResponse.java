package com.raju.pagination.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AllProductResponse implements Parcelable {

    @SerializedName("success")
    public String Success;

    @SerializedName("page")
    public String Page;

    @SerializedName("total_results")
    public String Total_results;

    @SerializedName("total_pages")
    public String Total_pages;

    @SerializedName("products")
    public ArrayList<ProductDetailBean> products;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Success);
        dest.writeString(this.Page);
        dest.writeString(this.Total_results);
        dest.writeString(this.Total_pages);
        dest.writeList(this.products);
    }

    public AllProductResponse() {
    }

    protected AllProductResponse(Parcel in) {
        this.Success = in.readString();
        this.Page = in.readString();
        this.Total_results = in.readString();
        this.Total_pages = in.readString();
        this.products = new ArrayList<ProductDetailBean>();
        in.readList(this.products, ProductDetailBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<AllProductResponse> CREATOR = new Parcelable.Creator<AllProductResponse>() {
        @Override
        public AllProductResponse createFromParcel(Parcel source) {
            return new AllProductResponse(source);
        }

        @Override
        public AllProductResponse[] newArray(int size) {
            return new AllProductResponse[size];
        }
    };
}
