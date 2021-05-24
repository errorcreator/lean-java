package org.learn.java.lombok;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = {"name"})
public class Student {
    private String name;
    private transient String code;
    private Date dateOfBirth;
    private int rollNo;

    public Student(String name) {
        this.name = name;
    }
}
