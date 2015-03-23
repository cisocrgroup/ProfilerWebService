
/**
 * ProfilerWebServiceMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
        package cis.profiler.web;

        /**
        *  ProfilerWebServiceMessageReceiverInOut message receiver
        */

        public class ProfilerWebServiceMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutMessageReceiver{


        public void invokeBusinessLogic(org.apache.axis2.context.MessageContext msgContext, org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault{

        try {

        // get the implementation class for the Web Service
        Object obj = getTheImplementationObject(msgContext);

        ProfilerWebServiceSkeletonInterface skel = (ProfilerWebServiceSkeletonInterface)obj;
        //Out Envelop
        org.apache.axiom.soap.SOAPEnvelope envelope = null;
        //Find the axisOperation that has been set by the Dispatch phase.
        org.apache.axis2.description.AxisOperation op = msgContext.getOperationContext().getAxisOperation();
        if (op == null) {
        throw new org.apache.axis2.AxisFault("Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
        }

        java.lang.String methodName;
        if((op.getName() != null) && ((methodName = org.apache.axis2.util.JavaUtils.xmlNameToJavaIdentifier(op.getName().getLocalPart())) != null)){


        

            if("simpleEnrich".equals(methodName)){
                
                de.uni_muenchen.cis.www.profiler.SimpleEnrichResponse simpleEnrichResponse28 = null;
	                        de.uni_muenchen.cis.www.profiler.SimpleEnrichRequest wrappedParam =
                                                             (de.uni_muenchen.cis.www.profiler.SimpleEnrichRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    de.uni_muenchen.cis.www.profiler.SimpleEnrichRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               simpleEnrichResponse28 =
                                                   
                                                   
                                                         skel.simpleEnrich(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), simpleEnrichResponse28, false, new javax.xml.namespace.QName("http://www.cis.uni-muenchen.de/profiler/",
                                                    "simpleEnrich"));
                                    } else 

            if("startSession".equals(methodName)){
                
                de.uni_muenchen.cis.www.profiler.StartSessionResponse startSessionResponse30 = null;
	                        startSessionResponse30 =
                                                     
                                                 skel.startSession()
                                                ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), startSessionResponse30, false, new javax.xml.namespace.QName("http://www.cis.uni-muenchen.de/profiler/",
                                                    "startSession"));
                                    } else 

            if("getSimpleConfigurations".equals(methodName)){
                
                de.uni_muenchen.cis.www.profiler.GetSimpleConfigurationsResponse getSimpleConfigurationsResponse32 = null;
	                        getSimpleConfigurationsResponse32 =
                                                     
                                                 skel.getSimpleConfigurations()
                                                ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getSimpleConfigurationsResponse32, false, new javax.xml.namespace.QName("http://www.cis.uni-muenchen.de/profiler/",
                                                    "getSimpleConfigurations"));
                                    } else 

            if("createAccount".equals(methodName)){
                
                de.uni_muenchen.cis.www.profiler.CreateAccountResponse createAccountResponse34 = null;
	                        de.uni_muenchen.cis.www.profiler.CreateAccountRequest wrappedParam =
                                                             (de.uni_muenchen.cis.www.profiler.CreateAccountRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    de.uni_muenchen.cis.www.profiler.CreateAccountRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createAccountResponse34 =
                                                   
                                                   
                                                         skel.createAccount(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createAccountResponse34, false, new javax.xml.namespace.QName("http://www.cis.uni-muenchen.de/profiler/",
                                                    "createAccount"));
                                    } else 

            if("validateEmail".equals(methodName)){
                
                de.uni_muenchen.cis.www.profiler.ValidateEmailResponse validateEmailResponse36 = null;
	                        de.uni_muenchen.cis.www.profiler.ValidateEmailRequest wrappedParam =
                                                             (de.uni_muenchen.cis.www.profiler.ValidateEmailRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    de.uni_muenchen.cis.www.profiler.ValidateEmailRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               validateEmailResponse36 =
                                                   
                                                   
                                                         skel.validateEmail(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), validateEmailResponse36, false, new javax.xml.namespace.QName("http://www.cis.uni-muenchen.de/profiler/",
                                                    "validateEmail"));
                                    } else 

            if("getConfigurations".equals(methodName)){
                
                de.uni_muenchen.cis.www.profiler.GetConfigurationsResponse getConfigurationsResponse38 = null;
	                        getConfigurationsResponse38 =
                                                     
                                                 skel.getConfigurations()
                                                ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getConfigurationsResponse38, false, new javax.xml.namespace.QName("http://www.cis.uni-muenchen.de/profiler/",
                                                    "getConfigurations"));
                                    } else 

            if("resendID".equals(methodName)){
                
                de.uni_muenchen.cis.www.profiler.ResendIDResponse resendIDResponse40 = null;
	                        de.uni_muenchen.cis.www.profiler.ResendIDRequest wrappedParam =
                                                             (de.uni_muenchen.cis.www.profiler.ResendIDRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    de.uni_muenchen.cis.www.profiler.ResendIDRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               resendIDResponse40 =
                                                   
                                                   
                                                         skel.resendID(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), resendIDResponse40, false, new javax.xml.namespace.QName("http://www.cis.uni-muenchen.de/profiler/",
                                                    "resendID"));
                                    } else 

            if("abortProfiling".equals(methodName)){
                
                de.uni_muenchen.cis.www.profiler.AbortProfilingResponse abortProfilingResponse42 = null;
	                        de.uni_muenchen.cis.www.profiler.AbortProfilingRequest wrappedParam =
                                                             (de.uni_muenchen.cis.www.profiler.AbortProfilingRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    de.uni_muenchen.cis.www.profiler.AbortProfilingRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               abortProfilingResponse42 =
                                                   
                                                   
                                                         skel.abortProfiling(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), abortProfilingResponse42, false, new javax.xml.namespace.QName("http://www.cis.uni-muenchen.de/profiler/",
                                                    "abortProfiling"));
                                    } else 

            if("getProfile".equals(methodName)){
                
                de.uni_muenchen.cis.www.profiler.GetProfileResponse getProfileResponse44 = null;
	                        de.uni_muenchen.cis.www.profiler.GetProfileRequest wrappedParam =
                                                             (de.uni_muenchen.cis.www.profiler.GetProfileRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    de.uni_muenchen.cis.www.profiler.GetProfileRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getProfileResponse44 =
                                                   
                                                   
                                                         skel.getProfile(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getProfileResponse44, false, new javax.xml.namespace.QName("http://www.cis.uni-muenchen.de/profiler/",
                                                    "getProfile"));
                                    } else 

            if("getProfilingStatus".equals(methodName)){
                
                de.uni_muenchen.cis.www.profiler.GetProfilingStatusResponse getProfilingStatusResponse46 = null;
	                        de.uni_muenchen.cis.www.profiler.GetProfilingStatusRequest wrappedParam =
                                                             (de.uni_muenchen.cis.www.profiler.GetProfilingStatusRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    de.uni_muenchen.cis.www.profiler.GetProfilingStatusRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getProfilingStatusResponse46 =
                                                   
                                                   
                                                         skel.getProfilingStatus(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getProfilingStatusResponse46, false, new javax.xml.namespace.QName("http://www.cis.uni-muenchen.de/profiler/",
                                                    "getProfilingStatus"));
                                    } else 

            if("getTransactions".equals(methodName)){
                
                de.uni_muenchen.cis.www.profiler.GetTransactionsResponse getTransactionsResponse48 = null;
	                        de.uni_muenchen.cis.www.profiler.GetTransactionsRequest wrappedParam =
                                                             (de.uni_muenchen.cis.www.profiler.GetTransactionsRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    de.uni_muenchen.cis.www.profiler.GetTransactionsRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getTransactionsResponse48 =
                                                   
                                                   
                                                         skel.getTransactions(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getTransactionsResponse48, false, new javax.xml.namespace.QName("http://www.cis.uni-muenchen.de/profiler/",
                                                    "getTransactions"));
                                    } else 

            if("checkQuota".equals(methodName)){
                
                de.uni_muenchen.cis.www.profiler.CheckQuotaResponse checkQuotaResponse50 = null;
	                        de.uni_muenchen.cis.www.profiler.CheckQuotaRequest wrappedParam =
                                                             (de.uni_muenchen.cis.www.profiler.CheckQuotaRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    de.uni_muenchen.cis.www.profiler.CheckQuotaRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               checkQuotaResponse50 =
                                                   
                                                   
                                                         skel.checkQuota(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), checkQuotaResponse50, false, new javax.xml.namespace.QName("http://www.cis.uni-muenchen.de/profiler/",
                                                    "checkQuota"));
                                    
            } else {
              throw new java.lang.RuntimeException("method not found");
            }
        

        newMsgContext.setEnvelope(envelope);
        }
        }
        catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
        }
        
        //
            private  org.apache.axiom.om.OMElement  toOM(de.uni_muenchen.cis.www.profiler.SimpleEnrichRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(de.uni_muenchen.cis.www.profiler.SimpleEnrichRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(de.uni_muenchen.cis.www.profiler.SimpleEnrichResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(de.uni_muenchen.cis.www.profiler.SimpleEnrichResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(de.uni_muenchen.cis.www.profiler.StartSessionResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(de.uni_muenchen.cis.www.profiler.StartSessionResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(de.uni_muenchen.cis.www.profiler.GetSimpleConfigurationsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(de.uni_muenchen.cis.www.profiler.GetSimpleConfigurationsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(de.uni_muenchen.cis.www.profiler.CreateAccountRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(de.uni_muenchen.cis.www.profiler.CreateAccountRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(de.uni_muenchen.cis.www.profiler.CreateAccountResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(de.uni_muenchen.cis.www.profiler.CreateAccountResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(de.uni_muenchen.cis.www.profiler.ValidateEmailRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(de.uni_muenchen.cis.www.profiler.ValidateEmailRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(de.uni_muenchen.cis.www.profiler.ValidateEmailResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(de.uni_muenchen.cis.www.profiler.ValidateEmailResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(de.uni_muenchen.cis.www.profiler.GetConfigurationsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(de.uni_muenchen.cis.www.profiler.GetConfigurationsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(de.uni_muenchen.cis.www.profiler.ResendIDRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(de.uni_muenchen.cis.www.profiler.ResendIDRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(de.uni_muenchen.cis.www.profiler.ResendIDResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(de.uni_muenchen.cis.www.profiler.ResendIDResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(de.uni_muenchen.cis.www.profiler.AbortProfilingRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(de.uni_muenchen.cis.www.profiler.AbortProfilingRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(de.uni_muenchen.cis.www.profiler.AbortProfilingResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(de.uni_muenchen.cis.www.profiler.AbortProfilingResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(de.uni_muenchen.cis.www.profiler.GetProfileRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(de.uni_muenchen.cis.www.profiler.GetProfileRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(de.uni_muenchen.cis.www.profiler.GetProfileResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(de.uni_muenchen.cis.www.profiler.GetProfileResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(de.uni_muenchen.cis.www.profiler.GetProfilingStatusRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(de.uni_muenchen.cis.www.profiler.GetProfilingStatusRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(de.uni_muenchen.cis.www.profiler.GetProfilingStatusResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(de.uni_muenchen.cis.www.profiler.GetProfilingStatusResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(de.uni_muenchen.cis.www.profiler.GetTransactionsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(de.uni_muenchen.cis.www.profiler.GetTransactionsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(de.uni_muenchen.cis.www.profiler.GetTransactionsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(de.uni_muenchen.cis.www.profiler.GetTransactionsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(de.uni_muenchen.cis.www.profiler.CheckQuotaRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(de.uni_muenchen.cis.www.profiler.CheckQuotaRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(de.uni_muenchen.cis.www.profiler.CheckQuotaResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(de.uni_muenchen.cis.www.profiler.CheckQuotaResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, de.uni_muenchen.cis.www.profiler.SimpleEnrichResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(de.uni_muenchen.cis.www.profiler.SimpleEnrichResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private de.uni_muenchen.cis.www.profiler.SimpleEnrichResponse wrapsimpleEnrich(){
                                de.uni_muenchen.cis.www.profiler.SimpleEnrichResponse wrappedElement = new de.uni_muenchen.cis.www.profiler.SimpleEnrichResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, de.uni_muenchen.cis.www.profiler.StartSessionResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(de.uni_muenchen.cis.www.profiler.StartSessionResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private de.uni_muenchen.cis.www.profiler.StartSessionResponse wrapstartSession(){
                                de.uni_muenchen.cis.www.profiler.StartSessionResponse wrappedElement = new de.uni_muenchen.cis.www.profiler.StartSessionResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, de.uni_muenchen.cis.www.profiler.GetSimpleConfigurationsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(de.uni_muenchen.cis.www.profiler.GetSimpleConfigurationsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private de.uni_muenchen.cis.www.profiler.GetSimpleConfigurationsResponse wrapgetSimpleConfigurations(){
                                de.uni_muenchen.cis.www.profiler.GetSimpleConfigurationsResponse wrappedElement = new de.uni_muenchen.cis.www.profiler.GetSimpleConfigurationsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, de.uni_muenchen.cis.www.profiler.CreateAccountResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(de.uni_muenchen.cis.www.profiler.CreateAccountResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private de.uni_muenchen.cis.www.profiler.CreateAccountResponse wrapcreateAccount(){
                                de.uni_muenchen.cis.www.profiler.CreateAccountResponse wrappedElement = new de.uni_muenchen.cis.www.profiler.CreateAccountResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, de.uni_muenchen.cis.www.profiler.ValidateEmailResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(de.uni_muenchen.cis.www.profiler.ValidateEmailResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private de.uni_muenchen.cis.www.profiler.ValidateEmailResponse wrapvalidateEmail(){
                                de.uni_muenchen.cis.www.profiler.ValidateEmailResponse wrappedElement = new de.uni_muenchen.cis.www.profiler.ValidateEmailResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, de.uni_muenchen.cis.www.profiler.GetConfigurationsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(de.uni_muenchen.cis.www.profiler.GetConfigurationsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private de.uni_muenchen.cis.www.profiler.GetConfigurationsResponse wrapgetConfigurations(){
                                de.uni_muenchen.cis.www.profiler.GetConfigurationsResponse wrappedElement = new de.uni_muenchen.cis.www.profiler.GetConfigurationsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, de.uni_muenchen.cis.www.profiler.ResendIDResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(de.uni_muenchen.cis.www.profiler.ResendIDResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private de.uni_muenchen.cis.www.profiler.ResendIDResponse wrapresendID(){
                                de.uni_muenchen.cis.www.profiler.ResendIDResponse wrappedElement = new de.uni_muenchen.cis.www.profiler.ResendIDResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, de.uni_muenchen.cis.www.profiler.AbortProfilingResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(de.uni_muenchen.cis.www.profiler.AbortProfilingResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private de.uni_muenchen.cis.www.profiler.AbortProfilingResponse wrapabortProfiling(){
                                de.uni_muenchen.cis.www.profiler.AbortProfilingResponse wrappedElement = new de.uni_muenchen.cis.www.profiler.AbortProfilingResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, de.uni_muenchen.cis.www.profiler.GetProfileResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(de.uni_muenchen.cis.www.profiler.GetProfileResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private de.uni_muenchen.cis.www.profiler.GetProfileResponse wrapgetProfile(){
                                de.uni_muenchen.cis.www.profiler.GetProfileResponse wrappedElement = new de.uni_muenchen.cis.www.profiler.GetProfileResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, de.uni_muenchen.cis.www.profiler.GetProfilingStatusResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(de.uni_muenchen.cis.www.profiler.GetProfilingStatusResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private de.uni_muenchen.cis.www.profiler.GetProfilingStatusResponse wrapgetProfilingStatus(){
                                de.uni_muenchen.cis.www.profiler.GetProfilingStatusResponse wrappedElement = new de.uni_muenchen.cis.www.profiler.GetProfilingStatusResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, de.uni_muenchen.cis.www.profiler.GetTransactionsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(de.uni_muenchen.cis.www.profiler.GetTransactionsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private de.uni_muenchen.cis.www.profiler.GetTransactionsResponse wrapgetTransactions(){
                                de.uni_muenchen.cis.www.profiler.GetTransactionsResponse wrappedElement = new de.uni_muenchen.cis.www.profiler.GetTransactionsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, de.uni_muenchen.cis.www.profiler.CheckQuotaResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(de.uni_muenchen.cis.www.profiler.CheckQuotaResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private de.uni_muenchen.cis.www.profiler.CheckQuotaResponse wrapcheckQuota(){
                                de.uni_muenchen.cis.www.profiler.CheckQuotaResponse wrappedElement = new de.uni_muenchen.cis.www.profiler.CheckQuotaResponse();
                                return wrappedElement;
                         }
                    


        /**
        *  get the default envelope
        */
        private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory){
        return factory.getDefaultEnvelope();
        }


        private  java.lang.Object fromOM(
        org.apache.axiom.om.OMElement param,
        java.lang.Class type,
        java.util.Map extraNamespaces) throws org.apache.axis2.AxisFault{

        try {
        
                if (de.uni_muenchen.cis.www.profiler.SimpleEnrichRequest.class.equals(type)){
                
                           return de.uni_muenchen.cis.www.profiler.SimpleEnrichRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (de.uni_muenchen.cis.www.profiler.SimpleEnrichResponse.class.equals(type)){
                
                           return de.uni_muenchen.cis.www.profiler.SimpleEnrichResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (de.uni_muenchen.cis.www.profiler.StartSessionResponse.class.equals(type)){
                
                           return de.uni_muenchen.cis.www.profiler.StartSessionResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (de.uni_muenchen.cis.www.profiler.GetSimpleConfigurationsResponse.class.equals(type)){
                
                           return de.uni_muenchen.cis.www.profiler.GetSimpleConfigurationsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (de.uni_muenchen.cis.www.profiler.CreateAccountRequest.class.equals(type)){
                
                           return de.uni_muenchen.cis.www.profiler.CreateAccountRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (de.uni_muenchen.cis.www.profiler.CreateAccountResponse.class.equals(type)){
                
                           return de.uni_muenchen.cis.www.profiler.CreateAccountResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (de.uni_muenchen.cis.www.profiler.ValidateEmailRequest.class.equals(type)){
                
                           return de.uni_muenchen.cis.www.profiler.ValidateEmailRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (de.uni_muenchen.cis.www.profiler.ValidateEmailResponse.class.equals(type)){
                
                           return de.uni_muenchen.cis.www.profiler.ValidateEmailResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (de.uni_muenchen.cis.www.profiler.GetConfigurationsResponse.class.equals(type)){
                
                           return de.uni_muenchen.cis.www.profiler.GetConfigurationsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (de.uni_muenchen.cis.www.profiler.ResendIDRequest.class.equals(type)){
                
                           return de.uni_muenchen.cis.www.profiler.ResendIDRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (de.uni_muenchen.cis.www.profiler.ResendIDResponse.class.equals(type)){
                
                           return de.uni_muenchen.cis.www.profiler.ResendIDResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (de.uni_muenchen.cis.www.profiler.AbortProfilingRequest.class.equals(type)){
                
                           return de.uni_muenchen.cis.www.profiler.AbortProfilingRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (de.uni_muenchen.cis.www.profiler.AbortProfilingResponse.class.equals(type)){
                
                           return de.uni_muenchen.cis.www.profiler.AbortProfilingResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (de.uni_muenchen.cis.www.profiler.GetProfileRequest.class.equals(type)){
                
                           return de.uni_muenchen.cis.www.profiler.GetProfileRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (de.uni_muenchen.cis.www.profiler.GetProfileResponse.class.equals(type)){
                
                           return de.uni_muenchen.cis.www.profiler.GetProfileResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (de.uni_muenchen.cis.www.profiler.GetProfilingStatusRequest.class.equals(type)){
                
                           return de.uni_muenchen.cis.www.profiler.GetProfilingStatusRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (de.uni_muenchen.cis.www.profiler.GetProfilingStatusResponse.class.equals(type)){
                
                           return de.uni_muenchen.cis.www.profiler.GetProfilingStatusResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (de.uni_muenchen.cis.www.profiler.GetTransactionsRequest.class.equals(type)){
                
                           return de.uni_muenchen.cis.www.profiler.GetTransactionsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (de.uni_muenchen.cis.www.profiler.GetTransactionsResponse.class.equals(type)){
                
                           return de.uni_muenchen.cis.www.profiler.GetTransactionsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (de.uni_muenchen.cis.www.profiler.CheckQuotaRequest.class.equals(type)){
                
                           return de.uni_muenchen.cis.www.profiler.CheckQuotaRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (de.uni_muenchen.cis.www.profiler.CheckQuotaResponse.class.equals(type)){
                
                           return de.uni_muenchen.cis.www.profiler.CheckQuotaResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
        } catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
           return null;
        }



    

        /**
        *  A utility method that copies the namepaces from the SOAPEnvelope
        */
        private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env){
        java.util.Map returnMap = new java.util.HashMap();
        java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
        while (namespaceIterator.hasNext()) {
        org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
        returnMap.put(ns.getPrefix(),ns.getNamespaceURI());
        }
        return returnMap;
        }

        private org.apache.axis2.AxisFault createAxisFault(java.lang.Exception e) {
        org.apache.axis2.AxisFault f;
        Throwable cause = e.getCause();
        if (cause != null) {
            f = new org.apache.axis2.AxisFault(e.getMessage(), cause);
        } else {
            f = new org.apache.axis2.AxisFault(e.getMessage());
        }

        return f;
    }

        }//end of class
    