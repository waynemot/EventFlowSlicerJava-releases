//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.02.16 at 03:10:42 PM CST 
//


package edu.umd.cs.guitar.model.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CTWExtent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CTWExtent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="x" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="y" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="width" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="height" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CTWExtent", propOrder = {

})
public class CTWExtent {

    protected int x;
    protected int y;
    protected int width;
    protected int height;

    /**
     * Gets the value of the x property.
     * 
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the value of the x property.
     * 
     */
    public void setX(int value) {
        this.x = value;
    }

    /**
     * Gets the value of the y property.
     * 
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the value of the y property.
     * 
     */
    public void setY(int value) {
        this.y = value;
    }

    /**
     * Gets the value of the width property.
     * 
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the value of the width property.
     * 
     */
    public void setWidth(int value) {
        this.width = value;
    }

    /**
     * Gets the value of the height property.
     * 
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the value of the height property.
     * 
     */
    public void setHeight(int value) {
        this.height = value;
    }

}
