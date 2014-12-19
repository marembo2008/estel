package com.afrocoin.estel;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 * @author marembo
 */
public enum EstelResultCode {

    TRANSACTION_SUCCESSFUL(0, "Transaction Successful");
//10
//Account Not Found
//11
//Activation Code Not Found
//12
//Subscriber Activation Not Allowed
//13
//Subscriber Already Activated
//14
//Subscriber Blocked
//15
//Subscriber Out of Hierarchy
//16
//Subscriber Already Exists
//17
//Inactive Subscriber
//18
//Subscriber Not Found
//19
//Subscriber Cancelled
//20
//Inactive Recipient
//21
//Recipient Blocked
//22
//Activation Not Allowed / Subscriber Blocked
//23
//Recipient Cancelled
//24
//Invalid Account
//40
//Error in DB
//41
//Invalid Account Type
//42
//Invalid Amount
//43
//Invalid Cache
//44
//Invalid Currency Code
//45
//Invalid Date
//46
//Invalid Message Type
//47
//DB Insert Failed
//48
//Error in Client
//60
//Invalid PIN
//61
//PIN / Password Expired
//62
//PIN Already Exists
//63
//PIN Not Found
//64
//PIN Type Not Found
//70
//Plan Not Found
//71
//Prefix Not Found
//72
//Inactive Product
//73
//Product Not Found
//74
//Vendor Not Found
//75
//Inactive Vendor
//90
//Trans Limit Not Found
//91
//Trans Limit Type Not Found
//92
//Transaction Not Found
//93
//Transaction Limit Reached
//94
//Max Limit Per Trans Reached
//95
//Max Internal Transfer Limit Reached
//96
//Max Internal Transfer Limit Different Currency Reached
//97
//Grace Period Over
//98
//Overdraft Option
//99
//Max Number Of Trans Reached
//100
//Insufficient Funds
//101
//Insufficient Wallet
//102
//Inactive Wallet
//103
//Wallet Threshold Reached
//104
//Wallet Not Found
//105
//Wallet Transfer Not Allowed
//106
//Transaction Reversal Failed
//200
//Error in Adapter
//201
//Adapter Not Available
//300
//Inactive Receiver
//301
//Receiver Not Found
//302
//Holding Account Not Found
//303
//Remittance Not Allowed
//900
//License Not Found
//901
//Connection Not Available
//902
//Technical Failure
//903
//Timed Out
//904
//Host Not Found
//905
//Invalid Request
//906
//Unknown Error
    private static final Map<Integer, EstelResultCode> ESTELRESULTCODE_MAP = Maps.newHashMap();

    static {
        for (final EstelResultCode resultCode : EstelResultCode.values()) {
            ESTELRESULTCODE_MAP.put(resultCode.code, resultCode);
        }
    }
    private final int code;
    private final String description;

    private EstelResultCode(final int code, final String description) {
        this.code = code;
        this.description = description;
    }

    @Nonnull
    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return description;
    }

    @Nonnull
    public static EstelResultCode fromCode(final int code) {
        return checkNotNull(ESTELRESULTCODE_MAP.get(code), "There was no resultcode with the specified code");
    }
}
