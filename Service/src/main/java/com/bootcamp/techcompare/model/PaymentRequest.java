package com.bootcamp.techcompare.model;

//{
//  "userId": "user123",
//  "paymentDetails": {
//    "cardNumber": "4111111111111111",
//    "expiryDate": "12/24",
//    "cvv": "123"
//  },
//  "billingAddress": {
//    "street": "123 Example St",
//    "city": "Anytown",
//    "state": "CA",
//    "zipCode": "90210"
//  }
//}

public class PaymentRequest {

        private String username;

        class PaymentDetails {
            private String cardNumber;
            private String expiryDate;
            private String cvv;

            public PaymentDetails(String cardNumber, String expiryDate, String cvv) {
                this.cardNumber = cardNumber;
                this.expiryDate = expiryDate;
                this.cvv = cvv;
            }

            public String getCardNumber() {
                return cardNumber;
            }

            public void setCardNumber(String cardNumber) {
                this.cardNumber = cardNumber;
            }

            public String getExpiryDate() {
                return expiryDate;
            }

            public void setExpiryDate(String expiryDate) {
                this.expiryDate = expiryDate;
            }

            public String getCvv() {
                return cvv;
            }

            public void setCvv(String cvv) {
                this.cvv = cvv;
            }
        }

        private PaymentDetails paymentDetails;

        class Address {
            private String street;
            private String city;
            private String state;
            private String zipCode;

            public Address(String street, String city, String state, String zipCode) {
                this.street = street;
                this.city = city;
                this.state = state;
                this.zipCode = zipCode;
            }

            public String getStreet() {
                return street;
            }

            public void setStreet(String street) {
                this.street = street;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getZipCode() {
                return zipCode;
            }

            public void setZipCode(String zipCode) {
                this.zipCode = zipCode;
            }
        }

        private Address billingAddress;

        public PaymentRequest(String username, PaymentDetails paymentDetails, Address billingAddress) {
            this.username = username;
            this.paymentDetails = paymentDetails;
            this.billingAddress = billingAddress;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public PaymentDetails getPaymentDetails() {
            return paymentDetails;
        }

        public void setPaymentDetails(PaymentDetails paymentDetails) {
            this.paymentDetails = paymentDetails;
        }

        public Address getBillingAddress() {
            return billingAddress;
        }

        public void setBillingAddress(Address billingAddress) {
            this.billingAddress = billingAddress;
        }
}
