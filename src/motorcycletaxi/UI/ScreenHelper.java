package motorcycletaxi.UI;

public class ScreenHelper {

    // Clears the console, like typing cls
    public static void clear() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }

    // Waits for Enter so the user can read a message before the screen clears
    public static void pause() {
        InputHelper.readText("\nPress Enter to continue...");
    }
}