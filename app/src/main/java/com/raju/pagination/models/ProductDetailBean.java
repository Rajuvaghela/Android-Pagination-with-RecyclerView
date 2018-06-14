package com.raju.pagination.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProductDetailBean implements Parcelable {

    @SerializedName("product_id")
    public String Product_id;


    @SerializedName("title")
    public String Title;


    @SerializedName("qty")
    public String Qty;


    @SerializedName("currency_symbol")
    public String Currency_symbol;


    @SerializedName("description")
    public String Description;


    @SerializedName("status")
    public String Status;


    @SerializedName("rating")
    public String Rating;


    @SerializedName("parent_category_id")
    public String Parent_category_id;


    @SerializedName("on_sale")
    public String On_sale;


    @SerializedName("sale_price")
    public String Sale_price;


    @SerializedName("regular_price")
    public String Regular_price;


    @SerializedName("tax_product_with_price_regula")
    public String Tax_product_with_price_regula;


    @SerializedName("tax_product_with_price_sale")
    public String Tax_product_with_price_sale;


    @SerializedName("image")
    public String Image;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Product_id);
        dest.writeString(this.Title);
        dest.writeString(this.Qty);
        dest.writeString(this.Currency_symbol);
        dest.writeString(this.Description);
        dest.writeString(this.Status);
        dest.writeString(this.Rating);
        dest.writeString(this.Parent_category_id);
        dest.writeString(this.On_sale);
        dest.writeString(this.Sale_price);
        dest.writeString(this.Regular_price);
        dest.writeString(this.Tax_product_with_price_regula);
        dest.writeString(this.Tax_product_with_price_sale);
        dest.writeString(this.Image);
    }

    public ProductDetailBean() {
    }

    protected ProductDetailBean(Parcel in) {
        this.Product_id = in.readString();
        this.Title = in.readString();
        this.Qty = in.readString();
        this.Currency_symbol = in.readString();
        this.Description = in.readString();
        this.Status = in.readString();
        this.Rating = in.readString();
        this.Parent_category_id = in.readString();
        this.On_sale = in.readString();
        this.Sale_price = in.readString();
        this.Regular_price = in.readString();
        this.Tax_product_with_price_regula = in.readString();
        this.Tax_product_with_price_sale = in.readString();
        this.Image = in.readString();
    }

    public static final Parcelable.Creator<ProductDetailBean> CREATOR = new Parcelable.Creator<ProductDetailBean>() {
        @Override
        public ProductDetailBean createFromParcel(Parcel source) {
            return new ProductDetailBean(source);
        }

        @Override
        public ProductDetailBean[] newArray(int size) {
            return new ProductDetailBean[size];
        }
    };
}
