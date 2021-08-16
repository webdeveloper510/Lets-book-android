package com.letsbook.letsbook.Model;


public class Response_Shops_List {
    private int code;
    private Model_ShopsList data;

    public Response_Shops_List(int code, Model_ShopsList data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Model_ShopsList getData() {
        return data;
    }

    public void setData(Model_ShopsList data) {
        this.data = data;
    }
}
