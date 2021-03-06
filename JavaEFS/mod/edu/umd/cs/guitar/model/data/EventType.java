/*	
 *  Copyright (c) 2009-@year@. The GUITAR group at the University of Maryland. Names of owners of this group may
 *  be obtained by sending an e-mail to atif@cs.umd.edu
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 *  documentation files (the "Software"), to deal in the Software without restriction, including without 
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 *	the Software, and to permit persons to whom the Software is furnished to do so, subject to the following 
 *	conditions:
 * 
 *	The above copyright notice and this permission notice shall be included in all copies or substantial 
 *	portions of the Software.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 *	LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO 
 *	EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER 
 *	IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR 
 *	THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package edu.umd.cs.guitar.model.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EventType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="EventType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EventId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="WidgetId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Initial" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="Action" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ParameterList" type="{}ParameterListType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Listeners" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Optional" type="{}AttributesType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EventType", propOrder = {
    "eventId",
    "widgetId",
    "type",
    "initial",
    "action",
    "parameterList",
    "name",
    "listeners",
    "optional"
})
public class EventType {

    @XmlElement(name = "EventId", required = true)
    protected String eventId;



	@XmlElement(name = "WidgetId", required = true)
    protected String widgetId;
    @XmlElement(name = "Type", required = true)
    protected String type;
    @XmlElement(name = "Initial")
    protected boolean initial;
    @XmlElement(name = "Action", required = true)
    protected String action;
    @XmlElement(name = "ParameterList")
    protected List<ParameterListType> parameterList;
    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "Listeners")
    protected List<String> listeners;
    @XmlElement(name = "Optional")
    protected AttributesType optional;

    /**
     * Gets the value of the eventId property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * Sets the value of the eventId property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setEventId(String value) {
        this.eventId = value;
    }

    /**
     * Gets the value of the widgetId property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getWidgetId() {
        return widgetId;
    }

    /**
     * Sets the value of the widgetId property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setWidgetId(String value) {
        this.widgetId = value;
    }

    /**
     * Gets the value of the type property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the initial property.
     *
     */
    public boolean isInitial() {
        return initial;
    }

    /**
     * Sets the value of the initial property.
     *
     */
    public void setInitial(boolean value) {
        this.initial = value;
    }

    /**
     * Gets the value of the action property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAction(String value) {
        this.action = value;
    }

    /**
     * Gets the value of the parameterList property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parameterList property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParameterList().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ParameterListType }
     *
     *
     */
    public List<ParameterListType> getParameterList() {
        if (parameterList == null) {
            parameterList = new ArrayList<ParameterListType>();
        }
        return this.parameterList;
    }

    /**
     * Gets the value of the name property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the listeners property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listeners property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListeners().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     *
     *
     */
    public List<String> getListeners() {
        if (listeners == null) {
            listeners = new ArrayList<String>();
        }
        return this.listeners;
    }

    /**
     * Gets the value of the optional property.
     *
     * @return
     *     possible object is
     *     {@link AttributesType }
     *
     */
    public AttributesType getOptional() {
        return optional;
    }

    /**
     * Sets the value of the optional property.
     *
     * @param value
     *     allowed object is
     *     {@link AttributesType }
     *
     */
    public void setOptional(AttributesType value) {
        this.optional = value;
    }

    /**
     * Sets the value of the parameterList property.
     *
     * @param parameterList
     *     allowed object is
     *     {@link ParameterListType }
     *
     */
    public void setParameterList(List<ParameterListType> parameterList) {
        this.parameterList = parameterList;
    }

    /**
     * Sets the value of the listeners property.
     *
     * @param listeners
     *     allowed object is
     *     {@link String }
     *
     */
    public void setListeners(List<String> listeners) {
        this.listeners = listeners;
    }

    /**
     * Return true if this event type has at least one non-empty parameter
     * @return
     */
    public boolean hasAnyParameterLists()
    {
    	boolean oneP = parameterList != null && !parameterList.isEmpty();
    	if(!oneP)
    		return oneP;
    	for(ParameterListType pl : parameterList) {
    		oneP = pl.hasOneParameter();
    		if(oneP)
    			return oneP;
    	}
    	return false;
    }


    public String toString()
    {
    	String toReturn = eventId;

		if(hasAnyParameterLists()) {
			toReturn += ", Params: ";
			for(ParameterListType plt : parameterList) {
    			toReturn += "\'" + plt.getParameter().get(0) + "\'";
    			for(int i = 1; i < plt.getParameter().size(); i++)
    				toReturn += ", \'" + plt.getParameter().get(i) + "\'";
    		}
		}

    	return toReturn;
    }
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((eventId == null) ? 0 : eventId.hashCode());
		result = prime * result + ((parameterList == null) ? 0 : parameterList.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventType other = (EventType) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (eventId == null) {
			if (other.eventId != null)
				return false;
		} else if (!eventId.equals(other.eventId))
			return false;
		if (parameterList == null) {
			if (other.parameterList != null)
				return false;
		} else if (!parameterList.equals(other.parameterList))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	/**
	 * Fully compare the two parameter types of the two events.
	 * @param e1
	 * @param e2
	 * @return
	 */
	public static boolean parameterMatch(EventType e1, EventType e2)
	{
		if(e1 == null || e2 == null)
			return false;
		if(e1.getParameterList().size() != e2.getParameterList().size())
			return false;
		Iterator<ParameterListType> iPl1 = e1.getParameterList().iterator();
		Iterator<ParameterListType> iPl2 = e2.getParameterList().iterator();
		while(iPl1.hasNext()) {
			ParameterListType pl1 = iPl1.next();
			ParameterListType pl2 = iPl2.next();
			if(pl1.getParameter().size() != pl2.getParameter().size())
				return false;
			Iterator<String> il1 = pl1.getParameter().iterator();
			Iterator<String> il2 = pl2.getParameter().iterator();
			while(il1.hasNext())
				if(!il1.next().equals(il2.next()))
					return false;
		}
		return true;
	}
}

