/*******************************************************************************
 *    Copyright (c) 2018 Jonathan A. Saddler
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *    
 *    Contributors:
 *     Jonathan A. Saddler - initial API and implementation
 *******************************************************************************/


package edu.umd.cs.guitar.model.data;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Preferences complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="PartialConfiguration"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ResDir" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="App" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CFile" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="AAFile" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="VAFile" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CustClass" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="RipconFile" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="GFile" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="EFile" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="TCDir" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="RepconFile" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Algorithm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="RMIChoice" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="TCSel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartialConfiguration", propOrder = {
    "resDir",
    "app",
    "cFile",
    "aaFile",
    "vaFile",
    "custClass",
    "ripconFile",
    "gFile",
    "eFile",
    "tcDir",
    "repconFile",
    "algorithm",
    "rmiChoice",
    "tcSel"
})

@XmlRootElement(name = "PartialConfiguration")
public class PartialConfiguration {

    @XmlElement(name = "App")
    protected String app;
    @XmlElement(name = "ResDir")
    protected String resDir;
    @XmlElement (name = "CFile")
    protected String cFile;
    @XmlElement(name = "AAFile")
    protected String aaFile;
    @XmlElement(name = "VAFile")
    protected String vaFile;
    @XmlElement(name = "CustClass")
    protected String custClass;
    @XmlElement(name = "RipconFile")
    protected String ripconFile;
    @XmlElement(name = "GFile")
    protected String gFile;
    @XmlElement(name = "EFile")
    protected String eFile;
    @XmlElement(name = "TCDir")
    protected String tcDir;
    @XmlElement(name = "RepconFile")
    protected String repconFile;
    @XmlElement(name = "Algorithm")
    protected String algorithm;
    @XmlElement(name = "RMIChoice")
    protected String rmiChoice;
    @XmlElement(name = "TCSel")
    protected String tcSel;

	public void initializeDefaults()
	{
		app = null;
		resDir = null;
		cFile = null;
		aaFile = null;
		vaFile = null;
		custClass = null;
		ripconFile = null;
		gFile = null;
		eFile = null;
		repconFile = null;
		tcDir = null;
		algorithm = "RSECWO";
		rmiChoice = "RMIOn:1099";
		tcSel = null;
	}

	public void copyFrom(PartialConfiguration old)
	{
		app = old.app;
		resDir = old.resDir;
		cFile = old.cFile;
		aaFile = old.aaFile;
		vaFile = old.vaFile;
		custClass = old.custClass;
		ripconFile = old.ripconFile;
		gFile = old.gFile;
		eFile = old.eFile;
		repconFile = old.repconFile;
		tcDir = old.tcDir;
		algorithm = old.algorithm;
		rmiChoice = old.rmiChoice;
		tcSel = old.tcSel;
	}

    /**
     * Gets the value of the app property
     */
    public String getApp() { return this.app; }
    /**
     * Sets the value of the app property
     */
    public void setApp(String theString) { this.app = theString; }
    /**
     * Gets the value of the resDir property
     * @return
     */
    public String getResDir() { return resDir; }
    /**
     * Sets the value of the resDir property
     */
    public void setResDir(String theString) { resDir = theString; }
	/**
	 * Gets the value of the CFile property
	 */
    public String getCFile() { return cFile; }
    /**
     * Sets the value of the CFile property
     */
    public void setCFile(String theString) { cFile = theString; }
	/**
	 * Get the value of the AAFile property
	 */
    public String getAAFile() { return aaFile; }
    /**
     * Set the value of the AAFile property
     */
    public void setAAFile(String theString) { aaFile = theString; }
    /**
     * Gets the value of the VAFile property
     */
    public String getVAFile() { return vaFile; }
    /**
     * Sets the value of the VAFile property
     */
    public void setVAFile(String theString) { vaFile = theString; }
    /**
     * Gets the value of the CustClass property.
     */
    public String getCustClass() { return custClass; }
    /**
     * Sets the value of the CustClass property.
     */
    public void setCustClass(String theString) { custClass = theString; }
    /**
     * Gets the value of the RipconFile property.
     */
    public String getRipconFile() { return ripconFile; }
    /**
     * Sets the value of the RipconFile property.
     */
    public void setRipconFile(String theString) { ripconFile = theString; }
    /**
     * Gets the value of the GFile property.
     */
    public String getGFile() { return gFile; }
    /**
     * Sets the value of the GFile property.
     * @param gFile
     */
	public void setGFile(String theString) { gFile = theString; }
	/**
     * Gets the value of the EFile property.
     */
    public String getEFile() { return eFile; }
    /**
     * Sets the value of the EFile property.
     */
	public void setEFile(String theString) { eFile = theString; }
	 /**
     * Gets the value of the RepconFile property.
     */
    public String getRepconFile() { return repconFile; }
    /**
     * Sets the value of the RepconFile property.
     */
    public void setRepconFile(String theString) { repconFile = theString; }
    /**
	 * Gets the value of the TCDir property
	 */
	public String getTCDir() { return tcDir; }
	/**
	 * Sets the value of the TCDir property
	 */
	public void setTCDir(String theString) { tcDir = theString; }
	/**
	 * Gets the value of the Algorithm property
	 */
	public String getAlgorithm() { return algorithm; }
	/**
	 * Sets the value of the Algorithm property
	 */
	public void setAlgorithm(String theString) { algorithm = theString; }
	/**
	 * Gets the value of the RMIChoice property
	 */
	public String getRMIChoice() { return rmiChoice; }
	/**
	 * Sets the value of the RMIChoice property
	 */
	public void setRMIChoice(String theString) { rmiChoice = theString; }
	/**
	 * Gets the value of the TCSel property
	 */
	public String getTCSel() { return tcSel; }
	/**
	 * Sets the value of the TCSel property
	 */
	public void setTCSel(String theString)  { tcSel = theString; }

	public String toString()
	{
		String[][] attributes = new String[][]{
				{"resDir", getResDir()},
				{"app", getApp()},
				{"cFile", getCFile()},
				{"aaFile", getAAFile()},
				{"vaFile", getVAFile()},
				{"custClass", getCustClass()},
				{"ripconFile", getRipconFile()},
				{"gFile", getGFile()},
				{"eFile", getEFile()},
				{"tcDir", getTCDir()},
				{"repconFile", getRepconFile()},
				{"algorithm", getAlgorithm()},
				{"rmiChoice", getRMIChoice()},
				{"tcSel", getTCSel()}
		};
		String toReturn = "EFS Configuration Settings:";
		for(String[] s : attributes) {
			toReturn += "\n" + s[0] + ": " + (s[1] == null ? "" : s[1]);
		}
		return toReturn;
	}
}
