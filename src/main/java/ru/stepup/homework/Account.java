package ru.stepup.homework;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

enum CurList {RUB,EUR,USD}
interface Command{
    public void perform();
}
interface Loadable{
    public void load();
}
class NothingToUndo extends Exception{}
public class Account {
    private String account;
    private HashMap<CurList, Integer> savings;
    private Deque<Command> commands = new ArrayDeque<>();

    public Account(String account) {
        setAccount(account);
        this.savings = new HashMap<>();
    }
    private Account(){}
    public void setAccount(String account) {
        if(account == null || account.isEmpty()) throw new IllegalArgumentException("Name must be not empty");
        String oldAccount = this.account;
        this.commands.push(()->{this.account = oldAccount;});
        this.account = account;
    }

    public void setSavings(CurList cur, Integer val) {
        if (val<0) throw new IllegalArgumentException();
        if (this.savings.containsKey(cur))
        {
            Integer oldValue = this.savings.get(cur);
            this.commands.push(()->{this.savings.put(cur, oldValue);});
        }
        else
        {
            this.commands.push(()->{this.savings.remove(cur);});
        }
        this.savings.put(cur,val);
    }

    public String getAccount() {
        return account;
    }

    public HashMap<CurList, Integer> getSavings() {
        return new HashMap<CurList, Integer>(this.savings);
    }
    public Loadable Save() {return new Snapshot();}
    private class Snapshot implements Loadable
    {
        private String account;
        private HashMap<CurList, Integer> savings;

        public Snapshot ()
        {
            this.account = Account.this.account;
            this.savings = new HashMap<>(Account.this.savings);

        }
        @Override
        public void load() {
            Account.this.account = this.account;
            Account.this.savings = this.savings;
        }
    }

    public  Account undo() throws NothingToUndo {
        if (commands.isEmpty()) throw new NothingToUndo();
        commands.pop().perform();
        return this;
    }

    @Override
    public String toString() {
        return "Account{" +
                "Имя='" + account + '\'' +
                ", Счета=" + savings +
                '}';
    }
}
