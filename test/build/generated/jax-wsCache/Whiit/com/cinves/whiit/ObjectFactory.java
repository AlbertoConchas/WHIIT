
package com.cinves.whiit;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.cinves.whiit package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ConsultResponse_QNAME = new QName("http://whiit.cinves.com/", "consultResponse");
    private final static QName _Inform_QNAME = new QName("http://whiit.cinves.com/", "inform");
    private final static QName _Hello_QNAME = new QName("http://whiit.cinves.com/", "hello");
    private final static QName _HelloResponse_QNAME = new QName("http://whiit.cinves.com/", "helloResponse");
    private final static QName _InformResponse_QNAME = new QName("http://whiit.cinves.com/", "informResponse");
    private final static QName _List_QNAME = new QName("http://whiit.cinves.com/", "list");
    private final static QName _ListResponse_QNAME = new QName("http://whiit.cinves.com/", "listResponse");
    private final static QName _Consult_QNAME = new QName("http://whiit.cinves.com/", "consult");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.cinves.whiit
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Hello }
     * 
     */
    public Hello createHello() {
        return new Hello();
    }

    /**
     * Create an instance of {@link Inform }
     * 
     */
    public Inform createInform() {
        return new Inform();
    }

    /**
     * Create an instance of {@link ConsultResponse }
     * 
     */
    public ConsultResponse createConsultResponse() {
        return new ConsultResponse();
    }

    /**
     * Create an instance of {@link HelloResponse }
     * 
     */
    public HelloResponse createHelloResponse() {
        return new HelloResponse();
    }

    /**
     * Create an instance of {@link ListResponse }
     * 
     */
    public ListResponse createListResponse() {
        return new ListResponse();
    }

    /**
     * Create an instance of {@link List }
     * 
     */
    public List createList() {
        return new List();
    }

    /**
     * Create an instance of {@link InformResponse }
     * 
     */
    public InformResponse createInformResponse() {
        return new InformResponse();
    }

    /**
     * Create an instance of {@link Consult }
     * 
     */
    public Consult createConsult() {
        return new Consult();
    }

    /**
     * Create an instance of {@link LastLocation }
     * 
     */
    public LastLocation createLastLocation() {
        return new LastLocation();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://whiit.cinves.com/", name = "consultResponse")
    public JAXBElement<ConsultResponse> createConsultResponse(ConsultResponse value) {
        return new JAXBElement<ConsultResponse>(_ConsultResponse_QNAME, ConsultResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Inform }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://whiit.cinves.com/", name = "inform")
    public JAXBElement<Inform> createInform(Inform value) {
        return new JAXBElement<Inform>(_Inform_QNAME, Inform.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Hello }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://whiit.cinves.com/", name = "hello")
    public JAXBElement<Hello> createHello(Hello value) {
        return new JAXBElement<Hello>(_Hello_QNAME, Hello.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HelloResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://whiit.cinves.com/", name = "helloResponse")
    public JAXBElement<HelloResponse> createHelloResponse(HelloResponse value) {
        return new JAXBElement<HelloResponse>(_HelloResponse_QNAME, HelloResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InformResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://whiit.cinves.com/", name = "informResponse")
    public JAXBElement<InformResponse> createInformResponse(InformResponse value) {
        return new JAXBElement<InformResponse>(_InformResponse_QNAME, InformResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link List }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://whiit.cinves.com/", name = "list")
    public JAXBElement<List> createList(List value) {
        return new JAXBElement<List>(_List_QNAME, List.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://whiit.cinves.com/", name = "listResponse")
    public JAXBElement<ListResponse> createListResponse(ListResponse value) {
        return new JAXBElement<ListResponse>(_ListResponse_QNAME, ListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Consult }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://whiit.cinves.com/", name = "consult")
    public JAXBElement<Consult> createConsult(Consult value) {
        return new JAXBElement<Consult>(_Consult_QNAME, Consult.class, null, value);
    }

}
