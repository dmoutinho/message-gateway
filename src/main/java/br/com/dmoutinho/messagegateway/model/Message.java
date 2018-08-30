package br.com.dmoutinho.messagegateway.model;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;

import br.com.dmoutinho.messagegateway.utils.Formatter;
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
    
    @Transient
    @XmlElement(name = "creation-date", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar creationDate;
    
    @Column(name = "creationDateJpa", nullable = false)
    public Calendar getCreationDateJpa() {
    	System.err.println("creationDate "+creationDate);
        return new GregorianCalendar(this.creationDate.getYear(),this.creationDate.getMonth(),this.creationDate.getDay());
    }
    
    public void setCreationDateJpa(Calendar creationDateJpa) {
    	this.creationDate = new Formatter().dateToXMLGregorianCalendar(creationDateJpa.getTime());
    }
    
    @Column(name = "name", nullable = false)
    @XmlElement(required = true)
    protected String name;

    @Column(name = "type", nullable = false)
    @XmlElement(required = true)
    protected String type;

    @XmlElement(name = "content-type", required = true)
    protected String contentType;

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(value = AdapterCDATA.class)
    protected String payload;

    @Override
	public String toString() {
		return "Message [id=" + id + ", documentId=" + documentId + ", creationDate=" + creationDate + ", name=" + name
				+ ", type=" + type + ", contentType=" + contentType + ", payload=" + payload + "]";
	}
    
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String value) {
        this.documentId = value;
    }

    public XMLGregorianCalendar getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(XMLGregorianCalendar value) {
        this.creationDate = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String value) {
        this.type = value;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String value) {
        this.contentType = value;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String value) {
        this.payload = value;
    }

}