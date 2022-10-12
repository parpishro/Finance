package ui;

import model.Bookkeeping;
import model.Master;

import java.io.*;

public class IO implements Serializable {
    static String masterPath = "../project_f0h3b/data/master.txt";
    static String bookkeepingPath = "../project_f0h3b/data/bookkeeping.txt";

    public static Master loadMaster() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(masterPath);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Master master = (Master) ois.readObject();
        ois.close();
        fis.close();
        System.out.println("Master data was loaded successfully from master.txt");
        return master;
    }

    public static void saveMaster(Master master) throws IOException {
        FileOutputStream fos = new FileOutputStream(masterPath);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(master);
        oos.close();
        fos.close();
        System.out.println("Master data was saved successfully into master.txt");
    }

    public static Bookkeeping loadBookkeeping() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(bookkeepingPath);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Bookkeeping bookkeeping = (Bookkeeping) ois.readObject();
        ois.close();
        fis.close();
        System.out.println("Bookkeeping data was loaded successfully from bookkeeping.txt");
        return bookkeeping;
    }

    public static void saveBookkeeping(Bookkeeping bookkeeping) throws IOException {
        FileOutputStream fos = new FileOutputStream(bookkeepingPath);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(bookkeeping);
        oos.close();
        fos.close();
        System.out.println("Bookkeeping data was saved successfully into bookkeeping.txt");
    }
}
