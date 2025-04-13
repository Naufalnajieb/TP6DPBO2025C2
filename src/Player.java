/* Saya Naufal Fakhri Al-Najieb dengan NIM 2309648 mengerjakan Tugas Praktikum 6
dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya
maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin. */

import java.awt.Image;

public class Player {

    private int posX;
    private int posY;
    private int width;
    private int height;
    private Image image;
    private int VelocityY;

    public Player(int posX, int posY, int width, int height, Image image) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.image = image;
        this.VelocityY = -0;
    }

    // Setter methods
    public void setPosX(int posX) {
        this.posX = posX;
    }
    public void setPosY(int posY) {
        this.posY = posY;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public void setImage(Image image) {
        this.image = image;
    }
    public void setVelocityY(int velocityY) {
        this.VelocityY = velocityY;
    }

    // Getter methods
    public int getPosX() {
        return this.posX;
    }
    public int getPosY() {
        return this.posY;
    }
    public int getWidth() {
        return this.width;
    }
    public int getHeight() {
        return this.height;
    }
    public Image getImage() {
        return this.image;
    }
    public int getVelocityY() {
        return this.VelocityY;
    }
}
