package cis.profiler.web;

import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProfilerWebServiceSkeleton implements ProfilerWebServiceSkeletonInterface {
        private final Properties props_;
        public ProfilerWebServiceSkeleton() {
                props_ = new Properties();
                loadProperties();
        }

        private void loadProperties() {
                try {
                        assert(props_ != null);
                        props_.load(
                                new InputStreamReader(
                                        this.getClass().getResourceAsStream("/conf/profiler.ini")));
                } catch (IOException e) {
                        log(Level.SEVERE, "could not open configuration file 'profiler.ini'");
                }
        }

        private void log(Level level, String msg) {
                Logger.getLogger(ProfilerWebServiceSkeleton.class.getName()).log(level, msg);
        }

        public de.uni_muenchen.cis.www.profiler.GetConfigurationsResponse getConfigurations() {
                de.uni_muenchen.cis.www.profiler.GetConfigurationsResponse resp =
                        new de.uni_muenchen.cis.www.profiler.GetConfigurationsResponse();
                de.uni_muenchen.cis.www.profiler.GetConfigurationsResponseType rst =
                        new de.uni_muenchen.cis.www.profiler.GetConfigurationsResponseType();

                rst.setConfigurations(new BackendGetDictionaries(props_).getDictionaries());
                resp.setGetConfigurationsResponse(rst);
                return resp;
                //TODO : fill this with the necessary business logic
                //throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#getConfigurations");
        }

        /**
         * Auto generated method signature
         *
         * @param simpleEnrichRequest3
         * @return simpleEnrichResponse4
         */

        public de.uni_muenchen.cis.www.profiler.SimpleEnrichResponse simpleEnrich
        (
                de.uni_muenchen.cis.www.profiler.SimpleEnrichRequest simpleEnrichRequest3
                )
                {
                        //TODO : fill this with the necessary business logic
                        throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#simpleEnrich");
                }


        /**
         * Auto generated method signature
         *
         * @return startSessionResponse6
         */

        public de.uni_muenchen.cis.www.profiler.StartSessionResponse startSession
        (

                )
                {
                        //TODO : fill this with the necessary business logic
                        throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#startSession");
                }


        /**
         * Auto generated method signature
         *
         * @return getSimpleConfigurationsResponse8
         */

        public de.uni_muenchen.cis.www.profiler.GetSimpleConfigurationsResponse getSimpleConfigurations
        (

                )
                {
                        //TODO : fill this with the necessary business logic
                        throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#getSimpleConfigurations");
                }


        /**
         * Auto generated method signature
         *
         * @param createAccountRequest9
         * @return createAccountResponse10
         */

        public de.uni_muenchen.cis.www.profiler.CreateAccountResponse createAccount
        (
                de.uni_muenchen.cis.www.profiler.CreateAccountRequest createAccountRequest9
                )
                {
                        //TODO : fill this with the necessary business logic
                        throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#createAccount");
                }


        /**
         * Auto generated method signature
         *
         * @param validateEmailRequest11
         * @return validateEmailResponse12
         */

        public de.uni_muenchen.cis.www.profiler.ValidateEmailResponse validateEmail
        (
                de.uni_muenchen.cis.www.profiler.ValidateEmailRequest validateEmailRequest11
                )
                {
                        //TODO : fill this with the necessary business logic
                        throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#validateEmail");
                }




        /**
         * Auto generated method signature
         *
         * @param resendIDRequest15
         * @return resendIDResponse16
         */

        public de.uni_muenchen.cis.www.profiler.ResendIDResponse resendID
        (
                de.uni_muenchen.cis.www.profiler.ResendIDRequest resendIDRequest15
                )
                {
                        //TODO : fill this with the necessary business logic
                        throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#resendID");
                }


        /**
         * Auto generated method signature
         *
         * @param abortProfilingRequest17
         * @return abortProfilingResponse18
         */

        public de.uni_muenchen.cis.www.profiler.AbortProfilingResponse abortProfiling
        (
                de.uni_muenchen.cis.www.profiler.AbortProfilingRequest abortProfilingRequest17
                )
                {
                        //TODO : fill this with the necessary business logic
                        throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#abortProfiling");
                }


        /**
         * Auto generated method signature
         *
         * @param getProfileRequest19
         * @return getProfileResponse20
         */

        public de.uni_muenchen.cis.www.profiler.GetProfileResponse getProfile
        (
                de.uni_muenchen.cis.www.profiler.GetProfileRequest getProfileRequest19
                )
                {
                        //TODO : fill this with the necessary business logic
                        throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#getProfile");
                }


        /**
         * Auto generated method signature
         *
         * @param getProfilingStatusRequest21
         * @return getProfilingStatusResponse22
         */

        public de.uni_muenchen.cis.www.profiler.GetProfilingStatusResponse getProfilingStatus
        (
                de.uni_muenchen.cis.www.profiler.GetProfilingStatusRequest getProfilingStatusRequest21
                )
                {
                        //TODO : fill this with the necessary business logic
                        throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#getProfilingStatus");
                }


        /**
         * Auto generated method signature
         *
         * @param getTransactionsRequest23
         * @return getTransactionsResponse24
         */

        public de.uni_muenchen.cis.www.profiler.GetTransactionsResponse getTransactions
        (
                de.uni_muenchen.cis.www.profiler.GetTransactionsRequest getTransactionsRequest23
                )
                {
                        //TODO : fill this with the necessary business logic
                        throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#getTransactions");
                }


        /**
         * Auto generated method signature
         *
         * @param checkQuotaRequest25
         * @return checkQuotaResponse26
         */

        public de.uni_muenchen.cis.www.profiler.CheckQuotaResponse checkQuota
        (
                de.uni_muenchen.cis.www.profiler.CheckQuotaRequest checkQuotaRequest25
                )
                {
                        //TODO : fill this with the necessary business logic
                        throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#checkQuota");
                }

}
