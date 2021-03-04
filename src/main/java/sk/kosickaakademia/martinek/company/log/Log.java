package sk.kosickaakademia.martinek.company.log;

public class Log {
    public void error(String msg){
        System.out.println("[ERRRRORRR]: " + msg);
    }

    public void print(String msg){
        System.out.println("[OK]: " + msg);
    }

    public void info(String msg){
        System.out.println(msg);
    }

}
