package information_management_practice;
/**
 *
 * @author RavenCosning
 */
public class MainApp {

    
    public static void main(String[] args) {
        
        com.formdev.flatlaf.FlatDarkLaf.setup();
        
        java.awt.EventQueue.invokeLater(() -> {
            new GUI().setVisible(true);
        });
    }
}