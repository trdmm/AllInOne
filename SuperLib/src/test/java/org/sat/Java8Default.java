package org.sat;

/**
 * File ...
 *
 * @Project:AllInOne
 * @Package:org.sat
 * @author:OverLord
 * @Since:2020/6/18 10:52
 * @Version:v0.0.1
 */
@FunctionalInterface
interface Java8Default {
    double calculate(int a);
    default double sqrt(int a){
        return Math.sqrt(a);
    }
}
@FunctionalInterface
interface Converter<F,T>{
    T convert(F from);
    default double calculate(int a){
        return ++a;
    };
}

class Something {
    String startsWith(String s) {
        return String.valueOf(s.charAt(0));
    }
}

class Person {
    String firstName;
    String lastName;

    Person() {}

    Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}

// Person 工厂
interface PersonFactory<P extends Person> {
    P create(String firstName, String lastName);
}

