package Utils;

import java.util.Properties;

public class TestData {
//    region VARIABLES
    private final Integer quantity;
    private final String productId;
    private final String thirdCategoryUrl;
    private final String secondCategoryUrl;
    private final String firstCategoryUrl;
    private final String productUrl;
    private final String categoryUrl;
    private final String myAccountUrl;
    private final String phoneNumber;
    private final String numberCart;
    private final String expirationDate;
    private final String cvc;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String city;
    private final String region;
    private final String postCode;
    private final String countryCode;
    private final String password;
    private final String existentEmail;
    private final String existentEmailPassword;
    private final String correctNumberCart;
    private final String wrongExpirationDate;
    private final String incompleteExpirationDate;
    private final String incompleteCvc;
    private final String declinedNumberCart3DSecure;
    private final String incorrectNumberCart;
    private final String numberCart3DSecure;
    private final String incompleteNumberCart;
    private final String wrongPhoneNumber;
    private final String wrongEmail;
//endregion

    public TestData(Properties properties) {
        //region URL
        productUrl                  = properties.getProperty("productUrl");
        categoryUrl                 = properties.getProperty("categoryUrl");
        firstCategoryUrl            = properties.getProperty("firstCategoryUrl");
        secondCategoryUrl           = properties.getProperty("secondCategoryUrl");
        thirdCategoryUrl            = properties.getProperty("thirdCategoryUrl");
        myAccountUrl                = properties.getProperty("myAccountUrl");

        //region PRODUCT
        productId                   = properties.getProperty("productId");
        quantity                    = Integer.parseInt(properties.getProperty("quantity"));

        //region CART
        numberCart                  = properties.getProperty("numberCart");
        correctNumberCart           = properties.getProperty("correctNumberCart");
        declinedNumberCart3DSecure  = properties.getProperty("declinedNumberCart3DSecure");
        incorrectNumberCart         = properties.getProperty("incorrectNumberCart");
        numberCart3DSecure          = properties.getProperty("numberCart3DSecure");
        incompleteNumberCart        = properties.getProperty("incompleteNumberCart");

        expirationDate              = properties.getProperty("expirationDate");
        wrongExpirationDate         = properties.getProperty("wrongExpirationDate");
        incompleteExpirationDate    = properties.getProperty("incompleteExpirationDate");

        incompleteCvc               = properties.getProperty("incompleteCvc");
        cvc                         = properties.getProperty("cvc");

        //region CUSTOMER
        firstName                   = properties.getProperty("firstName");
        lastName                    = properties.getProperty("lastName");

        //region ADDRESS
        address                     = properties.getProperty("address");
        city                        = properties.getProperty("city");
        region                      = properties.getProperty("region");
        postCode                    = properties.getProperty("postCode");
        countryCode                 = properties.getProperty("countryCode");
        phoneNumber                 = properties.getProperty("phoneNumber");

        //region CONTACT
        password                    = properties.getProperty("password");
        existentEmail               = properties.getProperty("existentEmail");
        existentEmailPassword       = properties.getProperty("existentEmailPassword");
        wrongEmail                  = properties.getProperty("wrongEmail");
        wrongPhoneNumber            = properties.getProperty("wrongPhoneNumber");
    }
    public Integer getQuantity() {
        return quantity;
    }

    public String getProductId() {
        return productId;
    }

    public String getThirdCategoryUrl() {
        return thirdCategoryUrl;
    }

    public String getSecondCategoryUrl() {
        return secondCategoryUrl;
    }

    public String getFirstCategoryUrl() {
        return firstCategoryUrl;
    }

    public String getProductUrl() {
        return productUrl;
    }


    public String getCategoryUrl() {
        return categoryUrl;
    }

    public String getMyAccountUrl() {
        return myAccountUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getNumberCart() {
        return numberCart;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getCvc() {
        return cvc;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getRegion() {
        return region;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getPassword() {
        return password;
    }

    public String getExistentEmail() {
        return existentEmail;
    }

    public String getExistentEmailPassword() {
        return existentEmailPassword;
    }

    public String getCorrectNumberCart() {
        return correctNumberCart;
    }

    public String getWrongExpirationDate() {
        return wrongExpirationDate;
    }

    public String getIncompleteExpirationDate() {
        return incompleteExpirationDate;
    }

    public String getIncompleteCvc() {
        return incompleteCvc;
    }

    public String getDeclinedNumberCart3DSecure() {
        return declinedNumberCart3DSecure;
    }

    public String getIncorrectNumberCart() {
        return incorrectNumberCart;
    }

    public String getNumberCart3DSecure() {
        return numberCart3DSecure;
    }

    public String getIncompleteNumberCart() {
        return incompleteNumberCart;
    }

    public String getWrongPhoneNumber() {
        return wrongPhoneNumber;
    }

    public String getWrongEmail() {
        return wrongEmail;
    }
}

