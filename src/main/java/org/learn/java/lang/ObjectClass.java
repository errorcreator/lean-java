package org.learn.java.lang;

public class ObjectClass {

    public String toString(){
        return "own toString method";
    }

    public int hashCode(){
        return 12345;
    }



    public static void main(String[] args) {
        ObjectClass student = new ObjectClass();
        System.out.println(student);
        System.out.println(student.hashCode());

        String str = new String("Hello");
        System.out.println(str.toString());
        System.out.println(str.hashCode());
    }
}
