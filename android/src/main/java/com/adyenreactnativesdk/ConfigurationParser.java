/*
 * Copyright (c) 2021 Adyen N.V.
 *
 * This file is open source and available under the MIT license. See the LICENSE file for more info.
 */

package com.adyenreactnativesdk;

import android.util.Log;

import androidx.annotation.NonNull;

import com.adyen.checkout.components.model.payments.Amount;
import com.adyen.checkout.core.api.Environment;
import com.adyen.checkout.core.exception.CheckoutException;
import com.facebook.react.bridge.ReadableMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import javax.annotation.Nullable;

final public class ConfigurationParser {

    final String TAG = "ConfigurationParser";

    final String AMOUNT_KEY = "amount";
    final String CLIENTKEY_KEY = "clientKey";
    final String COUNTRYCODE_KEY = "countryCode";
    final String ENVIRONMENT_KEY = "environment";
    final String SHOPPERLOCALE_KEY = "shopperLocale";
    final String SHOPPERREFERENCE_KEY = "shopperReference";
    final String SHOWSTOREPAYMENTFIELD_KEY = "showStorePaymentField";
    final String HOLDERNAMEREQUIRED_KEY = "holderNameRequired";
    final String SKIPLISTWHENSINGLEPAYMENTMETHOD_KEY = "skipListWhenSinglePaymentMethod";
    final String HIDECVCSTOREDCARD_KEY = "hideCvcStoredCard";
    final String SHOWPRESELECTEDSTOREDPAYMENTMETHOD_KEY = "showPreselectedStoredPaymentMethod";

    private final ReadableMap config;

    public ConfigurationParser(ReadableMap config) {
        this.config = config;
    }

    @Nullable
    public Amount getAmount() {
        ReadableMap map = config.getMap(AMOUNT_KEY);
        if (map == null) {
            Log.w(TAG, "No `amount` on configuration");
            return null;
        }

        JSONObject jsonObject = null;
        try {
            jsonObject = ReactNativeJson.convertMapToJson(map);
        } catch (CheckoutException | JSONException e) {
            Log.w(TAG, "Amount" + map.toString() + " not valid", e);
            return null;
        }
        return Amount.SERIALIZER.deserialize(jsonObject);
    }


    @NonNull
    public String getClientKey() throws NoSuchFieldException {
        String value = config.getString(CLIENTKEY_KEY);
        if (value == null) {
            throw new NoSuchFieldException("No " + CLIENTKEY_KEY);
        }

        return value;
    }

    @NonNull
    public String getCountryCode() throws NoSuchFieldException {
        String value = config.getString(COUNTRYCODE_KEY);
        if (value == null) {
            throw new NoSuchFieldException("No " + COUNTRYCODE_KEY);
        }

        return value;
    }

    @NonNull
    public String getShopperReference() throws NoSuchFieldException {
        String value = config.getString(SHOPPERREFERENCE_KEY);
        if (value == null) {
            throw new NoSuchFieldException("No " + SHOPPERREFERENCE_KEY);
        }

        return value;
    }

    @NonNull
    public Locale getLocale() throws NoSuchFieldException {
        String value = config.getString(SHOPPERLOCALE_KEY);
        if (value == null) {
            throw new NoSuchFieldException("No " + SHOPPERLOCALE_KEY);
        }

        return Locale.forLanguageTag(value);
    }

    @NonNull
    public Boolean getShowStorePaymentField() throws NoSuchFieldException {
        if(config.hasKey(SHOWSTOREPAYMENTFIELD_KEY)) {
            Boolean value = config.getBoolean(SHOWSTOREPAYMENTFIELD_KEY);
            if (value == null) {
                throw new NoSuchFieldException("No " + SHOWSTOREPAYMENTFIELD_KEY);
            }
            return value;
        }
        return true;
    }

    @NonNull
    public Boolean getHolderNameRequired() throws NoSuchFieldException {
        if(config.hasKey(HOLDERNAMEREQUIRED_KEY)) {
            Boolean value = config.getBoolean(HOLDERNAMEREQUIRED_KEY);
            if (value == null) {
                throw new NoSuchFieldException("No " + HOLDERNAMEREQUIRED_KEY);
            }
            return value;
        }
        return false;
    }

    @NonNull
    public Boolean getSkipListWhenSinglePaymentMethod() throws NoSuchFieldException {
        if(config.hasKey(SKIPLISTWHENSINGLEPAYMENTMETHOD_KEY)) {
            Boolean value = config.getBoolean(SKIPLISTWHENSINGLEPAYMENTMETHOD_KEY);
            if (value == null) {
                throw new NoSuchFieldException("No " + SKIPLISTWHENSINGLEPAYMENTMETHOD_KEY);
            }
            return value;
        }
        return false;
    }

    @NonNull
    public Boolean getHideCvcStoredCard() throws NoSuchFieldException {
        if(config.hasKey(HIDECVCSTOREDCARD_KEY)) {
            Boolean value = config.getBoolean(HIDECVCSTOREDCARD_KEY);
            if (value == null) {
                throw new NoSuchFieldException("No " + HIDECVCSTOREDCARD_KEY);
            }
            return value;
        }
        return true;
    }

    @NonNull
    public Boolean getShowPreselectedStoredPaymentMethod() throws NoSuchFieldException {
        if(config.hasKey(SHOWPRESELECTEDSTOREDPAYMENTMETHOD_KEY)){
            Boolean value = config.getBoolean(SHOWPRESELECTEDSTOREDPAYMENTMETHOD_KEY);
            if (value == null) {
                throw new NoSuchFieldException("No " + SHOWPRESELECTEDSTOREDPAYMENTMETHOD_KEY);
            }
            return value;
        }
        return true;
    }

    @NonNull
    public Environment getEnvironment() throws NoSuchFieldException {
        String environment = config.getString(ENVIRONMENT_KEY);
        if (environment == null) {
            throw new NoSuchFieldException("No " + ENVIRONMENT_KEY);
        }

        switch (environment.toLowerCase()) {
            case "live-au":
                return Environment.AUSTRALIA;
            case "live":
            case "live-eu":
                return Environment.EUROPE;
            case "live-us":
                return Environment.UNITED_STATES;
            default:
                return Environment.TEST;
        }
    }
}
