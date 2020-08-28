package org.springframework.samples.petclinic;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app")
public class PetClinicConfiguration {
    private org.springframework.samples.petclinic.PetClinicConfiguration.Aws aws;
    private org.springframework.samples.petclinic.PetClinicConfiguration.Mail mail;
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

    public org.springframework.samples.petclinic.PetClinicConfiguration.Mail getMail() {
        return mail;
    }

    public void setMail(org.springframework.samples.petclinic.PetClinicConfiguration.Mail value) {
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

    public org.springframework.samples.petclinic.PetClinicConfiguration.Aws getAws() {
        return aws;
    }

    public void setAws(org.springframework.samples.petclinic.PetClinicConfiguration.Aws value) {
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
