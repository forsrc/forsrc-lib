package com.forsrc.tools;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.common.SolrException;

import java.util.Properties;

public class SolrUtils {

    public static HttpSolrClient connect(Properties properties) throws SolrException {

        HttpSolrClient httpSolrClient = null;

        httpSolrClient = new HttpSolrClient.Builder(properties.getProperty(SolrProperties.baseSolrUrl.getKey())).build();
        httpSolrClient.setParser(new XMLResponseParser());
        int timeout = 5 * 1000;

        httpSolrClient.setConnectionTimeout(5 * 1000);
        httpSolrClient.setMaxTotalConnections(10);
        httpSolrClient.setAllowCompression(true);
        httpSolrClient.setDefaultMaxConnectionsPerHost(100);
        httpSolrClient.setFollowRedirects(false);
        return httpSolrClient;
    }

    public enum SolrProperties {

        baseSolrUrl("baseSolrUrl"),
        timeout("timeout");
        private String key;

        SolrProperties(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}
