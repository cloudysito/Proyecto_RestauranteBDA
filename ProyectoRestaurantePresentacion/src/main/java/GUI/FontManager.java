/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

public class FontManager {
    
    private Font greatVibesRegular;
    private Font notoSerifCondensedRegular;
    private Font nunitoRegular;
    private Font nunitoBold;
    private Font nunitoSemiBold;
    
    public FontManager() {
        try {
            greatVibesRegular = new Font("Serif", Font.PLAIN, 12);
            notoSerifCondensedRegular = new Font("Serif", Font.PLAIN, 12);
            nunitoRegular = new Font("SansSerif", Font.PLAIN, 12);
            nunitoBold = new Font("SansSerif", Font.BOLD, 12);
            nunitoSemiBold = new Font("SansSerif", Font.BOLD, 12);

            registerFonts();
            
        } catch (Exception e) {
            e.printStackTrace();
            setFallbackFonts();
        }
    }
    
    private Font loadFont(String resourcePath) throws Exception {
        try (InputStream is = getClass().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new Exception("Font resource not found: " + resourcePath);
            }
            return Font.createFont(Font.TRUETYPE_FONT, is);
        }
    }
    
    private void registerFonts() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(greatVibesRegular);
        ge.registerFont(notoSerifCondensedRegular);
        ge.registerFont(nunitoRegular);
        ge.registerFont(nunitoBold);
        ge.registerFont(nunitoSemiBold);
    }
    
    private void setFallbackFonts() {
        greatVibesRegular = new Font("Serif", Font.PLAIN, 12);
        notoSerifCondensedRegular = new Font("Serif", Font.PLAIN, 12);
        nunitoRegular = new Font("SansSerif", Font.PLAIN, 12);
        nunitoBold = new Font("SansSerif", Font.BOLD, 12);
        nunitoSemiBold = new Font("SansSerif", Font.BOLD, 12);
    }

    public Font getGreatVibesRegular(float size) {
        return greatVibesRegular.deriveFont(size);
    }
    
    public Font getNotoSerifCondensedRegular(float size) {
        return notoSerifCondensedRegular.deriveFont(size);
    }
    
    public Font getNunitoRegular(float size) {
        return nunitoRegular.deriveFont(size);
    }
    
    public Font getNunitoBold(float size) {
        return nunitoBold.deriveFont(size);
    }
    
    public Font getNunitoSemiBold(float size) {
        return nunitoSemiBold.deriveFont(size);
    }
    
    public Font getFont(String fontFamily, String style, float size) {
        switch (fontFamily) {
            case "GreatVibes":
                return getGreatVibesRegular(size);
            case "NotoSerif Condensed":
                return getNotoSerifCondensedRegular(size);
            case "Nunito":
                switch (style.toLowerCase()) {
                    case "bold": return getNunitoBold(size);
                    case "semibold": return getNunitoSemiBold(size);
                    default: return getNunitoRegular(size);
                }
            default:
                return new Font("SansSerif", Font.PLAIN, (int)size);
        }
    }
}
