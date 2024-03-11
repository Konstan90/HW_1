package ru.stepup.homework;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws NothingToUndo {
        Account a = new Account("Petya");
        a.setSavings(CurList.RUB,50);
        System.out.println(a);
        a.setSavings(CurList.RUB,8);
        System.out.println(a);
        a.undo();
        System.out.println(a);
        a.setSavings(CurList.USD,120);
        a.setSavings(CurList.EUR,10);
        System.out.println(a);
        Loadable qs1 = a.Save();
        a.setSavings(CurList.USD,8500);
        a.setSavings(CurList.EUR,150);
        System.out.println(a);
        qs1.load();
        System.out.println(a);
        }
    }