//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.02.18 at 10:44:22 AM CST 
//


package edu.umd.cs.guitar.model.data;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for Repeat complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Repeat">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Widget" type="{}Widget" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Repeat", propOrder = {
    "widget"
})
public class Repeat {

	
    @XmlElement(name = "Widget", required = true)
    protected List<Widget> widget;
    
    @XmlAttribute(name="minBound")
    protected String minBound;
    
    @XmlAttribute(name="maxBound")
    protected String maxBound;
    
    public static final String UNBOUNDED_SETTING = "unbounded";
    public static final String INVALID_SETTING = "invalid";
    /**
     * Gets the value of the widget property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the widget property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWidget().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Widget }
     * 
     * 
     */
    public List<Widget> getWidget() {
        if (widget == null) {
            widget = new ArrayList<Widget>();
        }
        return this.widget;
    }
    
    /**
     * Return a string representation of this Repeat.
     */
    public String toString()
    {
    	if(widget == null)
    		return "";
    	
    	String toReturn = "";
    	for(Widget w : widget) 
    		toReturn += w + "\n";
    	
    	return toReturn;
    }
    
    /**
     * Sets the minBound to newBound.
     * This method will only change the minBound to the desired value, 
     * if it is in the required range (>= 0)  
     * or equivalent to the string "unbounded" ignoring case (changed to "").
     * If outside the range, the bound is set to INVALID_SETTING. 
     */
    public void setMinBound(String newBound)
    {
    	minBound = newBound;
    	checkMinBound();
    }
    
    public String checkMinBound()
    {
    	if(minBound == null || minBound.isEmpty())
    		minBound = "";
    	else if(minBound.equalsIgnoreCase("unbounded")) 
			minBound = "";
		else 
			try {
				int bNum = Integer.parseInt(minBound);
				if(bNum < 0) 
					throw new NumberFormatException();
			}
			catch (NumberFormatException e) {
				minBound = INVALID_SETTING;
			}
    	return minBound;
    }
    
    /**
     * Sets the maxBound to newBound.
     * This method will only change the maxBound to the desired value, 
     * if it is in the required range (> 0)  
     * or equivalent to the string "unbounded" ignoring case (changed to "").
     * If outside the range, the bound is set to INVALID_SETTING. 
     */
    public void setMaxBound(String newBound)
    {
    	maxBound = newBound;
    	checkMaxBound();
    }
    public String checkMaxBound()
    {
    	if(maxBound == null || maxBound.isEmpty())
    		maxBound = "";
    	else if(maxBound.equalsIgnoreCase("unbounded")) 
			maxBound = "";
		else 
			try {
				int bNum = Integer.parseInt(maxBound);
				if(bNum < 0) 
					throw new NumberFormatException();
			}
			catch (NumberFormatException e) {
				maxBound = INVALID_SETTING;
			}
    	return maxBound;
    }
    
    public String getMinBound()
    {
    	if(minBound.isEmpty())
    		return UNBOUNDED_SETTING;
    	return minBound;
    }
    public String getMaxBound()
    {
    	if(maxBound.isEmpty())
    		return UNBOUNDED_SETTING;
    	return maxBound;
    }
    
    /**
     * Acts specifically on whether the minBound is unbounded or not.
     * If minBound is unbounded, return the integer passed in, if it is set to an integer,
     * return the integer value. 
     */
    public int testAndReturnMinBound(int altParam)
    {
    	minBound = checkMinBound();
    	if(minBound.isEmpty())
    		return 0; // default for min is 0 if only max is specified. 
    	else if(minBound.equals(INVALID_SETTING))
    		return altParam;
    	else
    		return Integer.parseInt(minBound);
    }
    /**
     * Acts specifically on whether the minBound is unbounded or not.
     * If minBound is unbounded, return the integer passed in, if it is set to an integer,
     * return the integer value. 
     */
    public int testAndReturnMaxBound(int altParam)
    {
    	maxBound = checkMaxBound();
    	if(maxBound.isEmpty()) 
    		return Integer.MAX_VALUE; // default for max is unbounded if only max is specified 
    	else if(maxBound.equals(INVALID_SETTING))
    		return altParam;
    	else
    		return Integer.parseInt(maxBound);
    }
}
