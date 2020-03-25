package com.zhao.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "zhao")
@Validated
@Getter
@Setter
public class ZhaoProperties {

    @Email(message = "email格式错误")
    private String email;

    private Person child;
    private Person wife;
    private List<Person> family;
    private List<Person> family1;

    @Getter
    @Setter
    public static class Person {
        private String name;
        private int age;

        @Override
        public String toString() {
            return String.format("{name:%s, age:%s}", name, age);
        }
    }


    private final Pay pay = new Pay();

    @Getter
    @Setter
    public static class Pay {
        private final Ali ali = new Ali();
        private final Wx wx = new Wx();

        @Getter
        @Setter
        public static class Ali {
            private String notifyUrl;
            private String returnUrl;
        }

        @Getter
        @Setter
        public static class Wx {
            private String notifyUrl;
        }
    }
}
