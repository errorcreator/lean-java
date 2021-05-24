package org.learn.java.lombok;

public class Example {

    public static void main(String[] args) {
        Student student1 = new Student();
        student1.setName("Khanh");
        student1.setCode("abc");
        student1.setRollNo(123);

        Student student2 = new Student();
        student2.setName("Khanh1");
        student2.setCode("bcd");
        student2.setRollNo(12344);

        System.out.println(student1.equals(student2));
    }
}
