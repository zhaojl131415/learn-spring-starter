package com.zhao.elasticsearch.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goods extends IdBase {

    private String sn;

    private String name;

    private double price;

    private int num;

    private int alert_num;

    private String image;

    private String images;

    private double weight;

    private String create_time;

    private String update_time;

    private String spu_id;

    private int category_id;

    private String category_name;

    private String brand_name;

    private String spec;

    private int sale_num;

    private int comment_num;

    private int status;

    private Long version;

    public Goods(String id, String sn, String name, double price, int num, int alert_num, String image, String images, double weight, String create_time, String update_time, String spu_id, int category_id, String category_name, String brand_name, String spec, int sale_num, int comment_num, int status, Long version) {
        super(id);
        this.sn = sn;
        this.name = name;
        this.price = price;
        this.num = num;
        this.alert_num = alert_num;
        this.image = image;
        this.images = images;
        this.weight = weight;
        this.create_time = create_time;
        this.update_time = update_time;
        this.spu_id = spu_id;
        this.category_id = category_id;
        this.category_name = category_name;
        this.brand_name = brand_name;
        this.spec = spec;
        this.sale_num = sale_num;
        this.comment_num = comment_num;
        this.status = status;
        this.version = version;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id='" + super.getId() + '\'' +
                ", sn='" + sn + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", num=" + num +
                ", alert_num=" + alert_num +
                ", image='" + image + '\'' +
                ", images='" + images + '\'' +
                ", weight=" + weight +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                ", spu_id='" + spu_id + '\'' +
                ", category_id=" + category_id +
                ", category_name='" + category_name + '\'' +
                ", brand_name='" + brand_name + '\'' +
                ", spec='" + spec + '\'' +
                ", sale_num=" + sale_num +
                ", comment_num=" + comment_num +
                ", status=" + status +
                ", version=" + version +
                '}';
    }
}