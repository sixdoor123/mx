package com.baiyi.jj.app.manager.paypal;

//import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/10/8 0008.
 */
public class PayPalUserInfo {

//    @SerializedName("family_name")
    private String FamilyName;
//    @SerializedName("language")
    private String Language;
//    @SerializedName("phone_number")
    private String PhoneNumber;
//    @SerializedName("locale")
    private String Locale;
//    @SerializedName("name")
    private String Name;
//    @SerializedName("email")
    private String Email;
//    @SerializedName("account_type")
    private String AccountType;
//    @SerializedName("birthday")
    private String Birthday;
//    @SerializedName("given_name")
    private String GivenName;
//    @SerializedName("user_id")
    private String UserId;

    public String getFamilyName() {
        return FamilyName;
    }

    public void setFamilyName(String familyName) {
        FamilyName = familyName;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getLocale() {
        return Locale;
    }

    public void setLocale(String locale) {
        Locale = locale;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAccountType() {
        return AccountType;
    }

    public void setAccountType(String accountType) {
        AccountType = accountType;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getGivenName() {
        return GivenName;
    }

    public void setGivenName(String givenName) {
        GivenName = givenName;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    //    {
//        "address":{
//        "postal_code":"95131",
//                "locality":"San Jose",
//                "region":"CA",
//                "country":"US",
//                "street_address":"3 Main St"
//    },
//        "family_name":"Smith",
//            "language":"en_US",
//            "phone_number":"4082560980",
//            "locale":"en_US",
//            "name":"Roger Smith",
//            "email":"rsmith@somewhere.com",
//            "account_type":"PERSONAL",
//            "birthday":"1982-08-02",
//            "given_name":"Roger",
//            "user_id":"https://www.paypal.com/webapps/auth/identity/user/jG8zVpn2toXCPmzNffW1WTRLA2KOhPXYybeTM9p3ct0"
//    }
}
