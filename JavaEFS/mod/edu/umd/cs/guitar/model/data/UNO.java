//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.02.18 at 10:44:22 AM CST 
//


package edu.umd.cs.guitar.model.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Preferences complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Preferences">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Port" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Family" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Linux" type="{}Linux"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UNO", propOrder = {
    "port",
    "family", 
    "linux"
})

public class UNO {

    @XmlElement(name = "Port", required = true)
    protected int port;
    
    @XmlElement(name = "Family", required = true)
    protected String family;    
    
    @XmlElement(name = "Linux")
    protected Linux linux;  

    /**
     * Gets the value of the port property.
     *  
     */
    public int getPort() {
        return this.port; 
    }
    
    /**
     * Sets the value of the property.
     *  
     */
    public void setPort(int portNum) {
        this.port = portNum;  
    }
    
    /**
     * Gets the value of the family property.
     *  
     */
    public String getFamily() {
        return this.family; 
    }
    
    /**
     * Sets the value of the family property.
     *  
     */
    public void setFamily(String fam){
    	this.family = fam;
    }
    
    /**
     * Gets the value of the linux property.
     *  
     */
    public Linux getLinux() {
        return this.linux; 
    }
    
    /**
     * Sets the value of the linux property.
     *  
     */
    public void setLinux(Linux lin) {
       this.linux = lin;  
    }


}