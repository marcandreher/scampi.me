package scampi.me.Classes;

public class Icon {

	private int id;
	private String fontIcon;
	private String name;
	private String text;
	private String link;
	private String color;

	public void setID(int id) {
		this.id = id;
	}

	public int getID() {
		return id;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getColor() {
		return color;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getLink() {
		return link;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setFontIcon(String fontIcon) {
		this.fontIcon = fontIcon;
	}

	public String getFontIcon() {
		return fontIcon;
	}

}
