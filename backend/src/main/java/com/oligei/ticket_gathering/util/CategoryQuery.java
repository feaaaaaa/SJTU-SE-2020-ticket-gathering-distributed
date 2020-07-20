/**
 * @ClassName CategoryQuery
 * @Description Helper Class for Query on Category
 * @Author ziliuziliu
 * @Date 2020/7/16
 */

package com.oligei.ticket_gathering.util;

public class CategoryQuery {
    private String type;
    private String name;

    public CategoryQuery() {}
    public CategoryQuery(String type, String name) {
        this.type=type;
        this.name=name;
    }

    public String getType() {return type;}
    public void setType(String type) {this.type=type;}
    public String getName() {return name;}
    public void setName(String name) {this.name=name;}
}