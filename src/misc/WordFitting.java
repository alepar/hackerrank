package misc;

public class WordFitting {

    /*
    Font Size
    •	Font sizes are integers.
    •	Each has a minimum and maximum size:
    •	int MIN = 4;
    •	int MAX = 16;

    Helper methods
    •	int widthOf(int fontSize, char c)
    •	int heightOf(int fontSize)
    */

    public static int widthOf(char c, int font) {
        return font*60;
    }

    public static int heightOf(int font) {
        return font*100;
    }

    private static int getWordWidth(String s,int font) {
        int width = 0;

        for (int i = 0 ; i < s.length(); i++)
            width += widthOf(s.charAt(i),font);
        return width;
    }

    public static int getMaxFont(String s, int w, int h) {
        int font = 4; int maxFont = 0, curLen = 0, curHeight = 0;

        String[] words = s.split(" ");
        outerloop:
        for (; font <= 16; font++)	{
            curHeight = font;
            for (int i = 0 ; i < words.length; i++) {
                if (curLen + getWordWidth(words[i], font) > w) {
                    curHeight += font;
                    if (curHeight > h)
                        break outerloop;
                    curLen = getWordWidth(words[i], font);
                } else {
                    curLen += getWordWidth(words[i], font);
                }
                if (i < words.length - 1)
                    curLen += widthOf(' ', font);
            }
            maxFont = Math.max(maxFont, font);
            font++;
        }
        return maxFont;
    }

    public static void main(String[] args) {
        System.out.println(getMaxFont("abcd", 102400, 10));
        System.out.println(getMaxFont("abcdf abcdf abcdf abcdf abcdf abcdf abcdf abcdf", 10, 102400));
    }

}
