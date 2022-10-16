package ui;

import model.Master;

import java.io.*;

public class IO implements Serializable {
    static String masterPath = "../project_f0h3b/data/master.txt";
    private static final long serialVersionUID = 1L;

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

}
