package org.sat;

import cn.hutool.core.io.LineHandler;
import cn.hutool.core.lang.Filter;
import cn.hutool.core.lang.copier.Copier;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Stopwatch;
import com.sun.org.apache.bcel.internal.generic.NEW;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;

import java.security.Key;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * File ...
 *
 * @Project:AllInOne
 * @Package:org.sat
 * @author:OverLord
 * @Since:2020/6/18 10:54
 * @Version:v0.0.1
 */
@Slf4j
public class Java8Test {
    public class Java8Impl implements Java8Default {
        @Override
        public double calculate(int a) {
            return Math.pow(a, 2);
        }
    }

    @Test
    public void defaultMethod() {
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
        log.debug(names.toString());
        Collections.sort(names, (String a, String b) -> {
            return b.compareTo(a);
        });
        log.debug(names.toString());
        names = Arrays.asList("peter", "anna", "mike", "xenia");
        Collections.sort(names, (String a, String b) -> b.compareTo(a));
        log.debug(names.toString());
        names = Arrays.asList("peter", "anna", "mike", "xenia");
        Collections.sort(names, (a, b) -> b.compareTo(a));
        log.debug(names.toString());
        names = Arrays.asList("peter", "anna", "mike", "xenia");
        names.sort((a, b) -> b.compareTo(a));
        log.debug(names.toString());
    }

    @Test
    public void Functional() {
        Converter<String, Integer> convert = (from) -> Integer.parseInt(from);
        int integer = convert.convert("963");
        log.debug("感染了{}人!", integer);
        int i = 83;
        System.out.printf("感染了 %d 人!", integer);
    }

    @Test
    public void Constructor() {
        //Converter<String,Integer> convert = (from) -> Integer.parseInt(from);
        Converter<String, Integer> convert = Integer::valueOf;
        Integer integer = convert.convert("963");

        Something something = new Something();
        Converter<String, String> startsWith = something::startsWith;
        String abc = startsWith.convert("abc");
        log.debug(abc);

        PersonFactory<Person> personCopier = Person::new;
        Person person = personCopier.create("abc", "xyz");
        log.debug(person.toString());
    }

    @Test
    public void innerFuncInterface() {
        Predicate<String> predicate = (s) -> s.length() > 0;
        boolean b = predicate.test("asda");
        boolean b1 = predicate.negate().test("fuck");
        log.debug(String.valueOf(b) + "---" + String.valueOf(b1));
    }

    /**
     * stream操作
     */
    @Test
    public void streamTest() {
        List<String> stringCollection = new ArrayList<>();
        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");
        //filter
        stringCollection.stream()
                .filter(s -> s.startsWith("a"))
                //.forEach(s->log.debug(s));
                .forEach(log::debug);
        //sort
        stringCollection.stream()
                .sorted()
                .forEach(log::debug);
        //map
        stringCollection.stream()
                .sorted((a, b) -> a.compareTo(b))
                .map(String::toUpperCase)
                .forEach(log::debug);
        //match
        boolean anyMatch = stringCollection.stream()
                .anyMatch(a -> a.startsWith("a"));
        boolean allMatch = stringCollection.stream()
                .allMatch(a -> a.startsWith("a"));
        boolean noneMatch = stringCollection.stream()
                .noneMatch(a -> a.startsWith("m"));
        log.debug(String.valueOf(anyMatch));
        log.debug(String.valueOf(allMatch));
        log.debug(String.valueOf(noneMatch));

        long c = stringCollection.stream()
                .filter(a -> a.startsWith("c"))
                .count();
        log.debug(c + "---");

        Optional<String> reduce = stringCollection.stream().sorted().reduce((s1, s2) -> s1 + "==" + s2);
        reduce.ifPresent(log::debug);
    }

    @Test
    public void parallelStreamTest() {
        int max = 1000000;
        ArrayList<String> uuids = new ArrayList<>(max);
        for (int i = 0; i < max; i++) {
            UUID uuid = UUID.randomUUID();
            uuids.add(uuid.toString());
        }
        Stopwatch stopwatch = Stopwatch.createStarted();
        long count = uuids.stream().sorted().count();
        long mills = stopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
        log.debug(count + "条耗时{}毫秒", mills);
        stopwatch = stopwatch.reset().start();
        uuids.parallelStream().sorted().forEach(log::debug);
        mills = stopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
        log.debug(count + "条耗时{}毫秒", mills);
    }

    @Test
    public void mapTest() {
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.putIfAbsent(i, "val " + i);
        }
        map.forEach((key, value) -> log.debug("Key:{} = {}", key, value));

        // computeIfPresent(), 当 key 存在时，才会做相关处理
        // 如下：对 key 为 3 的值，内部会先判断值是否存在，存在，则做 value + key 的拼接操作
        String s = map.computeIfPresent(3, (num, val) -> val + num);
        map.get(3);             // val33

        // 先判断 key 为 9 的元素是否存在，存在，则做删除操作
        map.computeIfPresent(9, (num, val) -> null);
        map.containsKey(9);     // false

        // computeIfAbsent(), 当 key 不存在时，才会做相关处理
        // 如下：先判断 key 为 23 的元素是否存在，不存在，则添加
        map.computeIfAbsent(23, num -> "val" + num);
        map.containsKey(23);    // true

        // 先判断 key 为 3 的元素是否存在，存在，则不做任何处理
        map.computeIfAbsent(3, num -> "bam");
        map.get(3);             // val33

        String not_found = map.getOrDefault(42, "not found");
        log.debug(not_found);

        // merge 方法，会先判断进行合并的 key 是否存在，不存在，则会添加元素
        map.merge(9, "val9", (value, newValue) -> value.concat(newValue));
        map.get(9);             // val9

        // 若 key 的元素存在，则对 value 执行拼接操作
        map.merge(9, "concat", (value, newValue) -> value.concat(newValue));
        map.get(9);             // val9concat
    }

    @Test
    public void timeTest(){
        Clock clock = Clock.systemDefaultZone();
        ZoneId zoneId = ZoneId.systemDefault();
        long millis = clock.millis();

        Instant instant = clock.instant();
        Date legacyDate = Date.from(instant);   // 老版本 java.util.Date

        LocalDateTime localDateTime = LocalDateTime.of(2020, Month.JANUARY, 20, 11, 12, 59);
        log.debug(localDateTime.toString());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd, yyyy - HH:mm");
        String format = formatter.format(localDateTime);
        log.debug(format);
    }
}
