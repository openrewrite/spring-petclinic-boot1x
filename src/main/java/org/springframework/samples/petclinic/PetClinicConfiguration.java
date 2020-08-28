package org.springframework.samples.petclinic;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app")
public class PetClinicConfiguration {
    private Aws aws;
    private Mail mail;
    private String name;
    private String port;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail value) {
        mail = value;
    }

    public static class Mail {
        private String techSupportContact;

        public String getTechSupportContact() {
            return techSupportContact;
        }

        public void setTechSupportContact(String value) {
            techSupportContact = value;
        }

        private String apiToken;

        public String getApiToken() {
            return apiToken;
        }

        public void setApiToken(String value) {
            apiToken = value;
        }

        private String oauthSecret;

        public String getOauthSecret() {
            return oauthSecret;
        }

        public void setOauthSecret(String value) {
            oauthSecret = value;
        }
    }

    public Aws getAws() {
        return aws;
    }

    public void setAws(Aws value) {
        aws = value;
    }

    public static class Aws {
        private String accessKey;

        public String getAccessKey() {
            return accessKey;
        }

        public void setAccessKey(String value) {
            accessKey = value;
        }

        private String secretKey;

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String value) {
            secretKey = value;
        }
    }
}
