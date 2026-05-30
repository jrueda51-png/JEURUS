package app;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import vista.LoginFrame;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    System.out.println("No se pudo cargar el estilo visual del sistema.");
                }

                try {
                    LoginFrame login = new LoginFrame();
                    login.setVisible(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "Error al iniciar JEURUS:\n" + e.getMessage(),
                            "JEURUS", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
