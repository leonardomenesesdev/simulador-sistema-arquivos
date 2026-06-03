import com.simuladorfs.FileSystemSimulator;

public class Main {
    public static void main(String[] args) {
        FileSystemSimulator simulator = new FileSystemSimulator();
        simulator.load();

        System.out.println("Simulador de Sistema de Arquivos iniciado.");
        System.out.println("Arquivo de dados: filesystem.dat");
        System.out.println("Diretorio raiz:");
        simulator.listDirectory("/");
    }
}
