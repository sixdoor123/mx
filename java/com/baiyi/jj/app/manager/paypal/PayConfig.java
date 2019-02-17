package com.baiyi.jj.app.manager.paypal;

//import com.paypal.android.sdk.payments.PayPalConfiguration;

import com.paypal.android.sdk.payments.PayPalConfiguration;

/**
 * Created by Administrator on 2016/10/20 0020.
 */
public class PayConfig {

    public static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_PRODUCTION;

    // 公司测试 AQ9ZMz6jEfOyMIbdHCSgbq_j03HZLgJnkg-E_DF3T5P_rLn4T84z0R6-evL_kw6yKIC4MSO8WAFq0po7
    // 公司正式 ARDceMoh-fTepidk-aeXnzZeXoMuoC_CjVwY4yYtSWlBb1enIF5do0Fd3jIlBHBpNsyehZHqEDBBVxhD
    // 个人的 ASVUbd4IZqwCsYofV3hZrNdHfssCXAdqEee7Dqn4DsdDNIKK9R02MrieQPFbi9sr8oZ964XbiWUFO7qw
    public static final String CONFIG_CLIENT_ID = "ARDceMoh-fTepidk-aeXnzZeXoMuoC_CjVwY4yYtSWlBb1enIF5do0Fd3jIlBHBpNsyehZHqEDBBVxhD";

    // 个人的 EPfqwx0fjfe6_G_Li9TuMyr6AHmnvkNSAK1OpZbcXSnAdZAlB8IZvufQRYY9pAtmzfKY4CYFWLC1M9VD
    // 沙河  EEHNtwwkurthmeioGxR3SMR_VDQgotEazJoIbg-M2oOdsBkP_tCBFU77SAZJNoazwp0CpF9uVm2K7RTf
    // 正式 EMk7yEb5zW728g6Q5HUPFxejL7E-eqH67zEXZ2OBirowBZmcW5q2Ga5WM7kgGYMXixvnqqtednVBTvzH
    public static final String CONFIG_Secret = "EMk7yEb5zW728g6Q5HUPFxejL7E-eqH67zEXZ2OBirowBZmcW5q2Ga5WM7kgGYMXixvnqqtednVBTvzH";


    public static String PayPalUrl = "https://api.paypal.com";
//	private static String PayPalUrl = "https://api.sandbox.paypal.com";
}
