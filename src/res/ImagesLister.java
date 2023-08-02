package res;

import java.io.File;

/**
 * A class i made to assist in the function of writing code to load resources.
 */
public class ImagesLister {


    public static void main(String[] args) {
        File press = new File("src/res/pressed");
        File depress = new File("src/res/depressed");
        File gridDepress = new File("src/res/depressed/grid");

        File[] pressedImages = press.listFiles();
        File[] depressedImages = depress.listFiles();
        File[] gridDepressedImages = gridDepress.listFiles();

        for (File file : pressedImages) {
            String s = kebabCaseToCamelCase(file.getName());
            System.out.println("public Image " + s.substring(0, s.length() - 4) + "Pressed = new ImageIcon(\"" + file.getPath().replace("\\", "/") + "\").getImage();");
        }

        for (File file : depressedImages) {
            String s = kebabCaseToCamelCase(file.getName());
            System.out.println("public Image " + s.substring(0, s.length() - 4) + "Depressed = new ImageIcon(\"" + file.getPath().replace("\\", "/") + "\").getImage();");
        }

        for (File file : gridDepressedImages) {
            String s = kebabCaseToCamelCase(file.getName());
            System.out.println("public Image " + s.substring(0, s.length() - 4) + " = new ImageIcon(\"" + file.getPath().replace("\\", "/") + "\").getImage();");
        }

    }

    public static String kebabCaseToCamelCase(String s) {
        String[] words = s.split("-");
        if (words.length <= 1) return s;
        String temp = words[0];

        for (int i = 1; i < words.length; i++) {
            temp += Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1);
        }

        return temp;
    }
}
