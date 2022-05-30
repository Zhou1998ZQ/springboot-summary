package com.zzq.authenticator;

import com.zzq.authenticator.config.GoogleAuthenticator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthTest {
//    private static String secret="ORDNFVBNA53H5ICA";
//    private static String secret="HQ53C3M4MWQJUVXD";
    private static String secret="EC2VIL5SKQSOQXCR";


    @Test
    public void generateSecretQrCodeURLTest(){
        secret = GoogleAuthenticator.generateSecretKey();
        String qrBarcodeURL = GoogleAuthenticator.getQRBarcodeURL("13212213239", "www.zzq.com", secret);
        System.out.println("qrBarcodeURL:" + qrBarcodeURL + ",key:" + secret);
    }

    @Test
    public void generateSecretQrCodeTest() {
        // different user with different secret
        secret = GoogleAuthenticator.generateSecretKey();
        String qrcode = GoogleAuthenticator.getQRBarcode("qq1962811421@gmail.com", secret);
        System.out.println("qrcode:" + qrcode + ",key:" + secret);
    }


    @Test
    public void verifyTest() {
        // if code start with 0 then delete 0
        long code = 532250;
        long t = System.currentTimeMillis();
        GoogleAuthenticator ga = new GoogleAuthenticator();
        ga.setWindowSize(1);
        boolean r = ga.check_code(secret, code, t);
        System.out.println("检查code是否正确？" + r);
    }


}
