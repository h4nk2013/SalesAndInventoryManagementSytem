package Lifestyle.gui;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;
import java.awt.*;

public class MyTheme extends DefaultMetalTheme {
	private final ColorUIResource primary1 = new ColorUIResource(0xAA, 0x10, 0x10);
	private final ColorUIResource primary2 = new ColorUIResource(0xAA, 0xAA, 0x50);
	private final ColorUIResource primary3 = new ColorUIResource(0xAA, 0x10, 0x10);
	
	private final ColorUIResource secondary1 = new ColorUIResource(0xAA, 0x10, 0x10);
	private final ColorUIResource secondary2 = new ColorUIResource(0xAA, 0xAA, 0xAA); //Disable
	private final ColorUIResource secondary3 = new ColorUIResource(0x80, 0x80, 0x10);
	
	private final ColorUIResource fontColor = new ColorUIResource(0x00, 0x00, 0x00);
	private final ColorUIResource textHighLightColor = new ColorUIResource(0xAA, 0x66, 0xAA);
	
	private final FontUIResource windowTitleFont = new FontUIResource(new Font("Verdana", 1, 12));
	private final FontUIResource controlFont = new FontUIResource(new Font("Verdana", 0, 12));
	
	protected ColorUIResource getPrimary1() {return primary1; }
	protected ColorUIResource getPrimary2() {return primary2; }
	protected ColorUIResource getPrimary3() {return primary3; }
	
	protected ColorUIResource getSecondary1() {return secondary1;}
	protected ColorUIResource getSecondary2() {return secondary2;}
	protected ColorUIResource getSecondary3() {return secondary3;}
	
	public FontUIResource getWindowTitleFont() {return windowTitleFont;}
	public FontUIResource getControlTextFont() {return controlFont; }
	public FontUIResource getMenuTextFont() {return controlFont;}
	public FontUIResource getUserTextFont() {return controlFont;}
	public ColorUIResource getUserTextColor() {return fontColor;}
	public ColorUIResource getTextHighlightColor() {return textHighLightColor;}
}