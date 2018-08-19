package br.com.dmoutinho.messagegateway.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;

import br.com.dmoutinho.messagegateway.xml.AdapterCDATA;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace="http://message-router.com.br/message", name = "message", propOrder = {
    "documentId",
    "creationDate",
    "name",
    "type",
    "contentType",
    "payload"
})
@XmlRootElement(namespace="http://message-router.com.br/message", name = "message")
public class Message {

    @Id
    @GeneratedValue
    @XmlTransient
    private Long id;

    @XmlElement(name = "document-id", required = true)
    protected String documentId;
    
    @XmlElement(name = "creation-date", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar creationDate;
    
    @XmlElement(required = true)
    protected String name;
    
    @XmlElement(required = true)
    protected String type;
    
    @XmlElement(name = "content-type", required = true)
    protected String contentType;
    
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(value = AdapterCDATA.class)
    protected String payload;

    public String getDocumentId() {
        return documentId;
    }

    @Override
	public String toString() {
		return "Message [id=" + id + ", documentId=" + documentId + ", creationDate=" + creationDate + ", name=" + name
				+ ", type=" + type + ", contentType=" + contentType + ", payload=" + payload + "]";
	}

	/**
     * Sets the value of the documentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentId(String value) {
        this.documentId = value;
    }

    /**
     * Gets the value of the creationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the value of the creationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreationDate(XMLGregorianCalendar value) {
        this.creationDate = value;
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
     * Gets the value of the contentType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Sets the value of the contentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContentType(String value) {
        this.contentType = value;
    }

    /**
     * Gets the value of the payload property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayload() {
        return payload;
    }

    /**
     * Sets the value of the payload property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayload(String value) {
        this.payload = value;
    }

}
