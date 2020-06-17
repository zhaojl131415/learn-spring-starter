package com.zhao.es.crawler.util;

import com.zhao.es.crawler.entity.Commodity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JsoupUtil {

    public static List<Commodity> parse(String keyword) throws  Exception {

        List<Commodity> list = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            list.addAll(parsePage(keyword, i));
        }
        return list;
    }

    private static List<Commodity> parsePage(String keyword, int page) throws  Exception {
        List<Commodity> list = new ArrayList<>();
        String url = "https://search.jd.com/Search?keyword=" + keyword + "&page=" + page;
        Document document = Jsoup.parse(new URL(url), 3000);

        Element jGoodsList = document.getElementById("J_goodsList");
        Elements elements = jGoodsList.getElementsByTag("li");

        for (Element element : elements) {
            String image = element.getElementsByTag("img").eq(0).attr("src"); // source-data-lazy-img
            String price = element.getElementsByClass("p-price").eq(0).text();
            String href = element.getElementsByClass("p-name").select("a").eq(0).attr("href");
            String name = element.getElementsByClass("p-name").select("em").eq(0).text();
            String shop = element.getElementsByClass("p-shop").eq(0).text();

            list.add(new Commodity(name, shop, price, image, href));
        }
        return list;
    }

    public static void main(String[] args) throws Exception {
        parse("java").forEach(System.out::println);
    }
}
