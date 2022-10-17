package ui;

import model.Master;

import java.io.*;

// IO handles writing the file (master.txt) on disk and reading it from the disk
public class IO implements Serializable {
    static String masterPath = "../project_f0h3b/data/master.txt";
    private static final long serialVersionUID = 1L;


    // MODIFIES: master, ois, fis
    // EFFECT: loads and deserialize the master object from the file system
    public static Master loadMaster() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(masterPath);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Master master = (Master) ois.readObject();
        ois.close();
        fis.close();
        return master;
    }

    // MODIFIES: master.txt, ois, fis
    // EFFECT: serializes the master object and saves it on master.txt in the file system
    public static void saveMaster(Master master) throws IOException {
        FileOutputStream fos = new FileOutputStream(masterPath);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(master);
        oos.close();
        fos.close();
    }

}
