package com.wdnyjx;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.wdnyjx.Entity.gjhn.MachineryInfo;
import com.wdnyjx.Service.IService;
import com.wdnyjx.Utils.ConstantUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * File ...
 *
 * @Project:AllInOne
 * @Package:com.wdnyjx
 * @author:OverLord
 * @Since:2020/6/16 11:10
 * @Version:v0.0.1
 */
@SpringBootTest
@Slf4j
public class CacheTest {
    @Autowired
    IService serviceImp;
    LoadingCache<String,List<MachineryInfo>> cache = CacheBuilder.newBuilder()
            .initialCapacity(2)
            .maximumSize(20)
            .expireAfterWrite(800, TimeUnit.MICROSECONDS)
            .build(new CacheLoader<String, List<MachineryInfo>>() {
                @Override
                public List<MachineryInfo> load(String s) throws Exception {
                    List<MachineryInfo> machineryInfos = serviceImp.listAll(ConstantUtil.parentIds);
                    return machineryInfos;
                }
            });

    public List<MachineryInfo> getJXList() {
        try {
            return cache.get("jx");
        } catch (ExecutionException e) {
            log.warn(e.getMessage());
        }
        return null;
    }

    @Test
    public void test01() {
        for (int i = 0; i < 10; i++) {
            List<MachineryInfo> jxList = jxList = getJXList();
            log.debug(jxList.size() + "条记录");
        }
    }

    @Test
    public void test02(){
        String[] results = {"共1条数据，1条数据失败", "共40条数据，40条保存成功，01条数据失败", "共43条数据，30条保存成功，0条数据失败,13条重复"};
        for (String result : results) {
            String[] strings = result.split("，");
            int length = strings.length;
            for (int i = 0; i < length; i++) {
                int index = strings[i].indexOf("条数据失败");
                if (index != -1){
                    String substring = strings[i].substring(0, index);
                    log.debug("失败"+Integer.parseInt(substring)+"条");
                }
            }
        }
    }
}
