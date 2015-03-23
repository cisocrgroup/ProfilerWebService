
/**
 * ProfilerWebServiceSkeletonInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
    package cis.profiler.web;
    /**
     *  ProfilerWebServiceSkeletonInterface java skeleton interface for the axisService
     */
    public interface ProfilerWebServiceSkeletonInterface {
     
         
        /**
         * Auto generated method signature
         * 
                                    * @param simpleEnrichRequest
         */

        
                public de.uni_muenchen.cis.www.profiler.SimpleEnrichResponse simpleEnrich
                (
                  de.uni_muenchen.cis.www.profiler.SimpleEnrichRequest simpleEnrichRequest
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
         */

        
                public de.uni_muenchen.cis.www.profiler.StartSessionResponse startSession
                (
                  
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
         */

        
                public de.uni_muenchen.cis.www.profiler.GetSimpleConfigurationsResponse getSimpleConfigurations
                (
                  
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param createAccountRequest
         */

        
                public de.uni_muenchen.cis.www.profiler.CreateAccountResponse createAccount
                (
                  de.uni_muenchen.cis.www.profiler.CreateAccountRequest createAccountRequest
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param validateEmailRequest
         */

        
                public de.uni_muenchen.cis.www.profiler.ValidateEmailResponse validateEmail
                (
                  de.uni_muenchen.cis.www.profiler.ValidateEmailRequest validateEmailRequest
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
         */

        
                public de.uni_muenchen.cis.www.profiler.GetConfigurationsResponse getConfigurations
                (
                  
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param resendIDRequest
         */

        
                public de.uni_muenchen.cis.www.profiler.ResendIDResponse resendID
                (
                  de.uni_muenchen.cis.www.profiler.ResendIDRequest resendIDRequest
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param abortProfilingRequest
         */

        
                public de.uni_muenchen.cis.www.profiler.AbortProfilingResponse abortProfiling
                (
                  de.uni_muenchen.cis.www.profiler.AbortProfilingRequest abortProfilingRequest
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getProfileRequest
         */

        
                public de.uni_muenchen.cis.www.profiler.GetProfileResponse getProfile
                (
                  de.uni_muenchen.cis.www.profiler.GetProfileRequest getProfileRequest
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getProfilingStatusRequest
         */

        
                public de.uni_muenchen.cis.www.profiler.GetProfilingStatusResponse getProfilingStatus
                (
                  de.uni_muenchen.cis.www.profiler.GetProfilingStatusRequest getProfilingStatusRequest
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getTransactionsRequest
         */

        
                public de.uni_muenchen.cis.www.profiler.GetTransactionsResponse getTransactions
                (
                  de.uni_muenchen.cis.www.profiler.GetTransactionsRequest getTransactionsRequest
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param checkQuotaRequest
         */

        
                public de.uni_muenchen.cis.www.profiler.CheckQuotaResponse checkQuota
                (
                  de.uni_muenchen.cis.www.profiler.CheckQuotaRequest checkQuotaRequest
                 )
            ;
        
         }
    