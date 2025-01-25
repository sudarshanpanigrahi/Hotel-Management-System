package com.hms;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.crypto.bcrypt.BCrypt.gensalt;

public class A {
    private int x;
    private int y;
    private int z;
    public A(int x, int y){
        this.x = x;
        this.y = y;
    }

    public A(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    } public String toString() {
        return x +" " + y + " " + z;
    }
    public static void main(String[] args) {
        A a1=new A(10,20);
        A a2=new A(20,30,40);
        System.out.println(a1);
        System.out.println(a2);
    }
}
