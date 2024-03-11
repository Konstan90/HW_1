package ru.stepup.homework;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.HashMap;

public class Tests {
    @Test
    public void getAccountName(){
        Account a = new Account("Vasya");
        Assertions.assertEquals(a.getAccount(),"Vasya");
    }

    @Test
    public void checkNotEmptyAccount(){
        Account a = new Account("Vasya");
        Assertions.assertThrows(IllegalArgumentException.class, () -> a.setAccount(""));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Account(""));
    }

    @Test
    public void checkSetSaving(){
        Account a = new Account("Petya");
        a.setSavings(CurList.RUB,50);
        a.setSavings(CurList.EUR,8);
        a.setSavings(CurList.USD,120);
        HashMap savings = a.getSavings();
        Assertions.assertEquals(savings.get(CurList.RUB),50);
        Assertions.assertEquals(savings.get(CurList.EUR),8);
        Assertions.assertEquals(savings.get(CurList.USD),120);
    }

    @Test
    public void checkSavingOnlyPositive() {
        Account a = new Account("Vasya");
        Assertions.assertThrows(IllegalArgumentException.class, () -> a.setSavings(CurList.RUB,-50));
    }
    @Test
    public void checkUndo() throws NothingToUndo {
        Account a = new Account("Petya");
        a.setSavings(CurList.RUB,50);
        a.setSavings(CurList.RUB,8);
        a.undo();
        Assertions.assertEquals(a.getSavings().get(CurList.RUB),50);
        a.setAccount("Vasya");
        a.undo();
        Assertions.assertEquals(a.getAccount(),"Petya");
    }

    @Test
    public void checkSaveAndLoad(){
        Account a = new Account("Petya");
        a.setSavings(CurList.RUB,50);
        a.setSavings(CurList.EUR,8);
        Loadable qs1 = a.Save();
        a.setSavings(CurList.RUB,8500);
        a.setSavings(CurList.EUR,150);
        a.setAccount("Petya");
        qs1.load();
        Assertions.assertEquals(a.getAccount(),"Petya");
        Assertions.assertEquals(
                a.getSavings().get(CurList.RUB),50);
        Assertions.assertEquals(
                a.getSavings().get(CurList.EUR),8);
    }
}
