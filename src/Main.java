import com.simuladorfs.FileSystemSimulator;
import com.simuladorfs.Shell;

public class Main {
    public static void main(String[] args) {
        FileSystemSimulator simulator = new FileSystemSimulator();
        Shell shell = new Shell(simulator);
        shell.run();
    }
}
