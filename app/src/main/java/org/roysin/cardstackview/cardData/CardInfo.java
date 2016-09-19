package org.roysin.cardstackview.cardData;

/**
 * @author  lijueding  on 2016/5/11.
 */
public class CardInfo {

    public static final float hdw = 0.6f;
    private static final String TAG = "CardInfo";
    public static final String TYPE_CREDIT = "贷记卡";
    public static final String TYPE_DEBIT = "借记卡";
    public static final String STATE_DEFAULT = "0";
    public static final String STATE_PENDING = "1";
    public static final String STATE_ACTIVATED = "2";

    public String cardNumber;
    public String name;
    public String cardType;
    public String pin;
    public String bankName;
    public String expireYear;
    public String expireMonth;
    public String expireDate;
    public String state;

    public CardInfo(){
        cardNumber = null;
        name = null;
        cardType = null;
        bankName = null;
        expireYear = null;
        expireDate = null;
        expireMonth = null;
        state = STATE_DEFAULT;
    }
    public CardInfo(String cardNumber){
        this();
        this.cardNumber = cardNumber;
    }
}
