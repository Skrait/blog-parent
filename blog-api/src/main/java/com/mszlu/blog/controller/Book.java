package com.mszlu.blog.controller;

public class Book{
     // 私有构造方法
    private Book(){}
    // 静态公开方法，向图书馆借书
    static public Book libraryBorrow(){ // 创建静态方法，返回本类实例对象
        return new Book();
    }
    public static void main(String[] args){
        // 创建一个书的对象，不是new实例化的，而是通过方法从图书馆借来的
        Book book = Book.libraryBorrow();    
    }
} 