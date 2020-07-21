package com.wdnyjx.Utils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 执行工具类
 *
 * @Project:AllInOne
 * @Package:com.wdnyjx.Utils
 * @author:OverLord
 * @Since:2020/6/15 14:37
 * @Version:v0.0.1
 */
public class ExecuteUtil {
    /**
     * 切割List并消费
     * @param dataList
     * @param size
     * @param consumer
     * @param <T>
     */
    public static <T> void partitionRun(List<T> dataList, int size, Consumer<List<T>> consumer) {
        if (CollectionUtils.isEmpty(dataList)) {
            return;
        }
        Preconditions.checkArgument(size > 0, "size must not be a minus");
        //Lists.partition(dataList, size).forEach(consumer);
        Lists.partition(dataList, size).parallelStream().forEach(consumer);
    }

    public static <T, V> List<V> partitionCall2List(List<T> dataList, int size, Function<List<T>, List<V>> function) {
        if (CollectionUtils.isEmpty(dataList)) {
            return new ArrayList<>(0);
        }
        Preconditions.checkArgument(size > 0, "size must not be a minus");

        return Lists.partition(dataList, size)
                .stream()
                .map(function)
                .filter(Objects::nonNull)
                .reduce(new ArrayList<>(),
                        (resultList1, resultList2) -> {
                            resultList1.addAll(resultList2);
                            return resultList1;
                        });
    }

    public static <T, V> Map<T, V> partitionCall2Map(List<T> dataList, int size, Function<List<T>, Map<T, V>> function) {
        if (CollectionUtils.isEmpty(dataList)) {
            return new HashMap<>(0);
        }
        Preconditions.checkArgument(size > 0, "size must not be a minus");
        return Lists.partition(dataList, size)
                .stream()
                .map(function)
                .filter(Objects::nonNull)
                .reduce(new HashMap<>(),
                        (resultMap1, resultMap2) -> {
                            resultMap1.putAll(resultMap2);
                            return resultMap1;
                        });
    }
}
