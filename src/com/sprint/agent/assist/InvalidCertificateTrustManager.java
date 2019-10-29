package com.sprint.agent.assist;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

public class InvalidCertificateTrustManager implements X509TrustManager{
    public X509Certificate[] getAcceptedIssuers1() {
        return null;
    }

  
    public void checkServerTrusted1(X509Certificate[] paramArrayOfX509Certificate, String paramString) throws CertificateException {

    }

   
    public void checkClientTrusted1(X509Certificate[] paramArrayOfX509Certificate, String paramString) throws CertificateException {
    }

	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		// TODO Auto-generated method stub
		return null;
	}
}
