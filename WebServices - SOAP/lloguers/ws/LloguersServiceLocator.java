/**
 * LloguersServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package lloguers.ws;

public class LloguersServiceLocator extends org.apache.axis.client.Service implements lloguers.ws.LloguersService {

    // Use to get a proxy class for lloguers
    private final java.lang.String lloguers_address = "http://localhost:8000/axis/services/lloguers";

    public java.lang.String getlloguersAddress() {
        return lloguers_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String lloguersWSDDServiceName = "lloguers";

    public java.lang.String getlloguersWSDDServiceName() {
        return lloguersWSDDServiceName;
    }

    public void setlloguersWSDDServiceName(java.lang.String name) {
        lloguersWSDDServiceName = name;
    }

    public lloguers.ws.Lloguers getlloguers() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(lloguers_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getlloguers(endpoint);
    }

    public lloguers.ws.Lloguers getlloguers(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            lloguers.ws.LloguersSoapBindingStub _stub = new lloguers.ws.LloguersSoapBindingStub(portAddress, this);
            _stub.setPortName(getlloguersWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (lloguers.ws.Lloguers.class.isAssignableFrom(serviceEndpointInterface)) {
                lloguers.ws.LloguersSoapBindingStub _stub = new lloguers.ws.LloguersSoapBindingStub(new java.net.URL(lloguers_address), this);
                _stub.setPortName(getlloguersWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("lloguers".equals(inputPortName)) {
            return getlloguers();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:lloguers", "LloguersService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("lloguers"));
        }
        return ports.iterator();
    }

}
