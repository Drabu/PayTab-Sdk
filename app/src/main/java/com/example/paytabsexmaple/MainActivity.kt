package com.example.paytabsexmaple

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.paytab_dk.paytabs_sdk.payment.ui.activities.PayTabActivity
import com.paytab_dk.paytabs_sdk.utils.PaymentParams


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        startActivityMine();

    }

    fun startActivityMine() {

        val `in` = Intent(applicationContext, PayTabActivity::class.java)
        `in`.putExtra(
            PaymentParams.MERCHANT_EMAIL,
            "junaidbhat110@gmail.com"
        ) //this a demo account for testing the sdk
        `in`.putExtra(
            PaymentParams.SECRET_KEY,
            "WeI1C7IWdSKQ5hwrva7AGZmHXACiTv4QKMSnUBj7t4Xzmu0SE8sl7P35pFnjbr9Wd1j8CDf6HdF3HGVmOoHWdL1DrpXDR6z58SdL"
        )//Add your Secret Key Here
        `in`.putExtra(PaymentParams.LANGUAGE, PaymentParams.ENGLISH)
        `in`.putExtra(PaymentParams.TRANSACTION_TITLE, "Test Paytabs android library")
        `in`.putExtra(PaymentParams.AMOUNT, 5.0)

        `in`.putExtra(PaymentParams.CURRENCY_CODE, "BHD")
        `in`.putExtra(PaymentParams.CUSTOMER_PHONE_NUMBER, "009733")
        `in`.putExtra(PaymentParams.CUSTOMER_EMAIL, "customer-email@example.com")
        `in`.putExtra(PaymentParams.ORDER_ID, "123456")
        `in`.putExtra(PaymentParams.PRODUCT_NAME, "Product 1, Product 2")

//Billing Address
        `in`.putExtra(PaymentParams.ADDRESS_BILLING, "Flat 1,Building 123, Road 2345")
        `in`.putExtra(PaymentParams.CITY_BILLING, "Manama")
        `in`.putExtra(PaymentParams.STATE_BILLING, "Manama")
        `in`.putExtra(PaymentParams.COUNTRY_BILLING, "BHR")
        `in`.putExtra(
            PaymentParams.POSTAL_CODE_BILLING,
            "00973"
        ) //Put Country Phone code if Postal code not available '00973'

//Shipping Address
        `in`.putExtra(PaymentParams.ADDRESS_SHIPPING, "Flat 1,Building 123, Road 2345")
        `in`.putExtra(PaymentParams.CITY_SHIPPING, "Manama")
        `in`.putExtra(PaymentParams.STATE_SHIPPING, "Manama")
        `in`.putExtra(PaymentParams.COUNTRY_SHIPPING, "BHR")
        `in`.putExtra(
            PaymentParams.POSTAL_CODE_SHIPPING,
            "00973"
        ) //Put Country Phone code if Postal code not available '00973'

//Payment Page Style
        `in`.putExtra(PaymentParams.PAY_BUTTON_COLOR, "#2474bc")

//Tokenization
        `in`.putExtra(PaymentParams.IS_TOKENIZATION, false)
//PreAuth
        `in`.putExtra(PaymentParams.IS_PREAUTH, false)

        startActivityForResult(`in`, PaymentParams.PAYMENT_REQUEST_CODE)
    }
}
