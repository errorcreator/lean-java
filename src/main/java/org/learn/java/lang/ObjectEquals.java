package org.learn.java.lang;

public class ObjectEquals {

    String name;
    int rollNbr;

    public ObjectEquals(String name, int rollNbr) {
        this.name = name;
        this.rollNbr = rollNbr;
    }


//    public boolean equals(Object obj){
//
//    }

    public static void main(String[] args) {
        ObjectEquals objEquals1 = new ObjectEquals("Rama", 007);
        ObjectEquals objEquals2 = new ObjectEquals("bama", 004);
        ObjectEquals objEquals3 = new ObjectEquals("gama", 005);
        ObjectEquals objEquals4 = new ObjectEquals("Rama", 002);
        ObjectEquals objEquals5 = new ObjectEquals("Rama", 007);
        ObjectEquals objEquals6 = objEquals1;

        System.out.println(objEquals1);
        System.out.println(objEquals5);
        System.out.println(objEquals1 == objEquals2);

        System.out.println(objEquals1.equals(objEquals2));

        System.out.println(objEquals1);
        System.out.println(objEquals6);

        System.out.println(objEquals5.hashCode());
        System.out.println(objEquals6.hashCode());

        Integer i1 = new Integer(2);
        Integer i2 = new Integer(1);
        System.out.println(i1);
        System.out.println(i2);
    }


}
