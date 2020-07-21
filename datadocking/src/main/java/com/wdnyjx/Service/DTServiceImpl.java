package com.wdnyjx.Service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 大田二维码平台
 * 需要绑定的车
 */
@Slf4j
@Service
public class DTServiceImpl {
    /**
     * 大田二维码请求
     * @param i 页数
     * @return html页面
     * @throws IOException
     */
    public Document getDoc(int i) throws IOException {
        Document document = Jsoup
                .connect("http://www.nj2wm.com/iot/index?p="+i)
                .header("Cookie", "NJSESSID=ck24es52fkjucnq1orfqcksf44")
                .get();
        return document;
    }
}
